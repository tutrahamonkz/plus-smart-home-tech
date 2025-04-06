package ru.yandex.practicum.handler.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.mapping.HubMapper;
import ru.yandex.practicum.model.Sensor;
import ru.yandex.practicum.repository.SensorRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceAddedHandler implements HubEventHandler {

    private final SensorRepository sensorRepository;

    @Override
    public String getEventType() {
        return DeviceAddedEventAvro.class.getCanonicalName();
    }

    @Override
    public void handle(HubEventAvro hubEvent) {
        Sensor newSensor = HubMapper.mapToSensor(hubEvent.getHubId(), (DeviceAddedEventAvro) hubEvent.getPayload());
        if (!sensorRepository.existsByIdInAndHubId(List.of(newSensor.getId()), newSensor.getHubId())) {
            Sensor sensor = sensorRepository.save(newSensor);
            log.info("Added device {}", sensor);
        }
    }
}
