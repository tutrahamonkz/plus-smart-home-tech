package ru.yandex.practicum.handler.sensor;

import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;

public class TemperatureSensorHandler implements SensorHandler {

    @Override
    public String getEventType() {
        return TemperatureSensorAvro.class.getCanonicalName();
    }

    @Override
    public Integer getSensorValue(ConditionTypeAvro typeAvro, SensorStateAvro stateAvro) {
        TemperatureSensorAvro sensorAvro = (TemperatureSensorAvro) stateAvro.getData();
        return switch (typeAvro) {
            case TEMPERATURE -> sensorAvro.getTemperatureC();
            default -> null;
        };
    }
}
