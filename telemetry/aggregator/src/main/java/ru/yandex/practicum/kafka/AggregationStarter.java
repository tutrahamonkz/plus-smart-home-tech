package ru.yandex.practicum.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Класс AggregationStarter, ответственный за запуск агрегации данных.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {

    private final KafkaProducer<Void, SensorsSnapshotAvro> producer;
    private final KafkaConsumer<Void, SensorEventAvro> consumer;
    private final RecordHandler recordHandler;

    private static final Duration CONSUME_ATTEMPT_TIMEOUT = Duration.ofMillis(1000);
    @Value("${sensor.topic}")
    private String sensorTopic;
    @Value("${snapshot.topic}")
    private String snapshotTopic;

    private static final Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    /**
     * Метод для начала процесса агрегации данных.
     * Подписывается на топики для получения событий от датчиков,
     * формирует снимок их состояния и записывает в кафку.
     */
    public void start() {
        List<String> topics = List.of(sensorTopic);
        try {
            consumer.subscribe(topics);

            // Цикл обработки событий
            while (true) {
                ConsumerRecords<Void, SensorEventAvro> records = consumer.poll(CONSUME_ATTEMPT_TIMEOUT);

                int count = 0;
                for (ConsumerRecord<Void, SensorEventAvro> record : records) {
                    Optional<SensorsSnapshotAvro> snapshot = recordHandler.updateState(record.value());
                    if (snapshot.isPresent()) {
                        ProducerRecord<Void, SensorsSnapshotAvro> producerRecord = new ProducerRecord<>(snapshotTopic,
                                snapshot.get());

                        log.info("Отправка {} в топик {}", snapshot.get(), snapshotTopic);

                        try {
                            producer.send(producerRecord);
                            producer.flush();
                        } catch (Exception e) {
                            log.error("Ошибка при отправке сообщения в Kafka: {}", e.getMessage(), e);
                        }
                    }
                    manageOffsets(record, count, consumer);
                    count++;
                }
                consumer.commitSync();
            }

        } catch (WakeupException ignored) {
            // игнорируем - закрываем консьюмер и продюсер в блоке finally
        } catch (Exception e) {
            log.error("Ошибка во время обработки событий от датчиков", e);
        } finally {

            try {
                producer.flush();
                consumer.commitSync(currentOffsets);
            } finally {
                log.info("Закрываем консьюмер");
                consumer.close();
                log.info("Закрываем продюсер");
                producer.close();
            }
        }
    }

    private static void manageOffsets(ConsumerRecord<Void, SensorEventAvro> record, int count,
                                      KafkaConsumer<Void, SensorEventAvro> consumer) {
        // обновляем текущий оффсет для топика-партиции
        currentOffsets.put(
                new TopicPartition(record.topic(), record.partition()),
                new OffsetAndMetadata(record.offset() + 1)
        );

        if(count % 10 == 0) {
            consumer.commitAsync(currentOffsets, (offsets, exception) -> {
                if(exception != null) {
                    log.warn("Ошибка во время фиксации оффсетов: {}", offsets, exception);
                }
            });
        }
    }
}