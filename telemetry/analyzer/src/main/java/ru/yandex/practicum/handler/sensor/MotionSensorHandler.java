package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;

@Component
public class MotionSensorHandler implements SensorHandler {

    @Override
    public String getEventType() {
        return MotionSensorAvro.class.getCanonicalName();
    }

    @Override
    public Integer getSensorValue(ConditionTypeAvro typeAvro, SensorStateAvro stateAvro) {
        MotionSensorAvro sensorAvro = (MotionSensorAvro) stateAvro.getData();
        return switch (typeAvro) {
            case MOTION -> sensorAvro.getMotion() ? 1 : 0;
            default -> null;
        };
    }
}
