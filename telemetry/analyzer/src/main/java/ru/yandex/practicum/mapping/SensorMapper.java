package ru.yandex.practicum.mapping;

import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.model.Sensor;

public class SensorMapper {

    public static Sensor map(String hubId, DeviceAddedEventAvro deviceAddedEventAvro) {
        Sensor sensor = new Sensor();
        sensor.setId(deviceAddedEventAvro.getId());
        sensor.setHubId(hubId);
        return sensor;
    }
}
