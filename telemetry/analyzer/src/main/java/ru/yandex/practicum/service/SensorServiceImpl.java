package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.mapping.SensorMapper;
import ru.yandex.practicum.model.Sensor;
import ru.yandex.practicum.repository.SensorRepository;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl {

    private final SensorRepository sensorRepository;

    public void addSensor(String hubId, DeviceAddedEventAvro event) {
        Sensor sensor = SensorMapper.map(hubId, event);
        sensorRepository.save(sensor);
    }

    public void removeSensor(DeviceRemovedEventAvro event) {
        sensorRepository.deleteById(event.getId());
    }
}
