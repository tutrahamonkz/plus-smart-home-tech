package ru.yandex.practicum.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.service.SnapshotServiceImpl;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class SnapshotProcessor {

    private final KafkaConsumer<Void, SensorsSnapshotAvro> consumer;
    private final SnapshotServiceImpl snapshotService;

    private static final Duration CONSUME_ATTEMPT_TIMEOUT = Duration.ofMillis(1000);

    @Value("${analyzer.topic.snapshots.v1}")
    private String hubTopic;

    private static final Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    public SnapshotProcessor(KafkaConsumer<Void, SensorsSnapshotAvro> consumer, SnapshotServiceImpl snapshotService) {
        this.consumer = consumer;
        this.snapshotService = snapshotService;

        Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));
    }

    public void start() {

        try {
            Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));
            List<String> topics = List.of(hubTopic);
            consumer.subscribe(topics);

            while (true) {
                ConsumerRecords<Void, SensorsSnapshotAvro> records = consumer.poll(CONSUME_ATTEMPT_TIMEOUT);

                int count = 0;
                for (ConsumerRecord<Void, SensorsSnapshotAvro> record : records) {
                    log.info("Received record {}", record.value());
                    snapshotService.updateState(record.value());
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
