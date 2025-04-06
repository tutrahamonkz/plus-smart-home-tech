package ru.yandex.practicum.handler.sensor;

import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;

public class ClimateSensorHandler implements SensorHandler {

    @Override
    public String getEventType() {
        return ClimateSensorAvro.class.getSimpleName();
    }

    @Override
    public Integer getSensorValue(ConditionTypeAvro typeAvro, SensorStateAvro stateAvro) {
        ClimateSensorAvro climateSensorAvro = (ClimateSensorAvro) stateAvro.getData();
        return switch (typeAvro) {
            case TEMPERATURE -> climateSensorAvro.getTemperatureC();
            case HUMIDITY -> climateSensorAvro.getHumidity();
            case CO2LEVEL -> climateSensorAvro.getCo2Level();
            default -> null;
        };
    }
}
