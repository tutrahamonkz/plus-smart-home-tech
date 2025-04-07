package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;

@Component
public class LightSensorHandler implements SensorHandler {

    @Override
    public String getEventType() {
        return LightSensorAvro.class.getCanonicalName();
    }

    @Override
    public Integer getSensorValue(ConditionTypeAvro typeAvro, SensorStateAvro stateAvro) {
        LightSensorAvro sensorAvro = (LightSensorAvro) stateAvro.getData();
        return switch (typeAvro) {
            case LUMINOSITY -> sensorAvro.getLuminosity();
            default -> null;
        };
    }
}
