package ru.yandex.practicum.handler.sensor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.KafkaAvroProducer;
import ru.yandex.practicum.mapping.SensorMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class TemperatureSensorEventHandler implements SensorEventHandler {

    @Value("${sensor.topic}")
    private String topic;
    private final KafkaAvroProducer producer;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.TEMPERATURE_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto event) {
        log.info("Received temperature sensor event: {}", event);
        producer.send(topic, SensorMapper.INSTANCE.mapSensorToAvro(event));
    }
}