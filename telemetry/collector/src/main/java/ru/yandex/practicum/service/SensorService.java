package ru.yandex.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.KafkaAvroProducer;
import ru.yandex.practicum.mapping.SensorMapper;
import ru.yandex.practicum.model.sensor.SensorEvent;

@Slf4j
@Service
@AllArgsConstructor
public class SensorService {
    private final KafkaAvroProducer producer;
    private static final String TOPIC_SENSOR = "telemetry.sensors.v1";

    public void send(SensorEvent event) {
        log.info("Отправляем данные от сенсора {}", event.toString());

        producer.send(TOPIC_SENSOR, SensorMapper.INSTANCE.mapSensorToAvro(event));
    }
}