package ru.yandex.practicum.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.kafka.SnapshotHandler;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SnapshotProcessor {

    private final KafkaConsumer<Void, SensorsSnapshotAvro> consumer;
    private final SnapshotHandler snapshotHandler;

    private static final Duration CONSUME_ATTEMPT_TIMEOUT = Duration.ofMillis(1000);

    @Value("${analyzer.topic.snapshots.v1}")
    private String hubTopic;

    private static final Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    public void start() {
        List<String> topics = List.of(hubTopic);
        try {
            consumer.subscribe(topics);

            while (true) {
                ConsumerRecords<Void, SensorsSnapshotAvro> records = consumer.poll(CONSUME_ATTEMPT_TIMEOUT);

                int count = 0;
                for (ConsumerRecord<Void, SensorsSnapshotAvro> record : records) {
                    log.info("Received record {}", record.value());
                    Optional<DeviceActionRequest> sensorsSnapshotAvro = snapshotHandler.updateState(record.value());
                    if (sensorsSnapshotAvro.isPresent()) {
                        // здесь нужна отправка действий из сценария на hub с помощью grpc клиента.
                    }
                    manageOffsets(record, count, consumer);
                    count++;
                }
            }
        } catch (WakeupException ignored) {

        } catch (Exception e) {
            log.error("Ошибка во время обработки событий от снэпшота]", e);
        } finally {
            try {
                consumer.commitSync(currentOffsets);
            } finally {
                log.info("Закрываем консьюмер");
                consumer.close();
            }
        }
    }

    private static void manageOffsets(ConsumerRecord<Void, SensorsSnapshotAvro> record, int count,
                                      KafkaConsumer<Void, SensorsSnapshotAvro> consumer) {
        // обновляем текущий оффсет для топика-партиции
        currentOffsets.put(
                new TopicPartition(record.topic(), record.partition()),
                new OffsetAndMetadata(record.offset() + 1)
        );

        if (count % 10 == 0) {
            consumer.commitAsync(currentOffsets, (offsets, exception) -> {
                if (exception != null) {
                    log.warn("Ошибка во время фиксации оффсетов: {}", offsets, exception);
                }
            });
        }
    }
}
