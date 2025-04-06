package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;

@Component
public class SwitchSensorHandler implements SensorHandler {

    @Override
    public String getEventType() {
        return SwitchSensorAvro.class.getCanonicalName();
    }

    @Override
    public Integer getSensorValue(ConditionTypeAvro typeAvro, SensorStateAvro stateAvro) {
        SwitchSensorAvro sensorAvro = (SwitchSensorAvro) stateAvro.getData();
        return switch (typeAvro) {
            case SWITCH -> sensorAvro.getState() ? 1 : 0;
            default -> null;
        };
    }
}
