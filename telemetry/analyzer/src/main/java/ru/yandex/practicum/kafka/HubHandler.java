package ru.yandex.practicum.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.model.HubEventType;
import ru.yandex.practicum.service.ScenarioServiceImpl;
import ru.yandex.practicum.service.SensorServiceImpl;

@Component
@RequiredArgsConstructor
public class HubHandler {

    private final SensorServiceImpl sensorService;
    private final ScenarioServiceImpl scenarioService;

    public void updateState(HubEventAvro event) {
        HubEventType type = HubEventType.valueOf(event.getPayload().getClass().getSimpleName());

        switch (type) {
            case DeviceAddedEventAvro:
                sensorService.addSensor(event.getHubId(), (DeviceAddedEventAvro) event.getPayload());
                break;
            case DeviceRemovedEventAvro:
                sensorService.removeSensor((DeviceRemovedEventAvro) event.getPayload());
                break;
            case ScenarioAddedEventAvro:
                scenarioService.addScenario(event.getHubId(), (ScenarioAddedEventAvro) event.getPayload());
                break;
            case ScenarioRemovedEventAvro:
                scenarioService.removeScenario((ScenarioRemovedEventAvro) event.getPayload());
                break;
        }
    }
}
