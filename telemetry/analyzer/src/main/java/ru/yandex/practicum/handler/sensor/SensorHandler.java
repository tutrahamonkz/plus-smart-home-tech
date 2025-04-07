package ru.yandex.practicum.handler.sensor;

import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;

public interface SensorHandler {

    String getEventType();

    Integer getSensorValue(ConditionTypeAvro typeAvro, SensorStateAvro stateAvro);
}
