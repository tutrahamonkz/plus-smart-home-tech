package ru.yandex.practicum.handler.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.repository.SensorRepository;

@Component
@RequiredArgsConstructor
public class DeviceRemovedHandler implements HubEventHandler {

    private final SensorRepository sensorRepository;

    @Override
    public String getEventType() {
        return DeviceRemovedEventAvro.class.getCanonicalName();
    }

    @Override
    public void handle(HubEventAvro hubEvent) {
        String sensorId = ((DeviceRemovedEventAvro) hubEvent.getPayload()).getId();
        sensorRepository.deleteById(sensorId);
    }
}
