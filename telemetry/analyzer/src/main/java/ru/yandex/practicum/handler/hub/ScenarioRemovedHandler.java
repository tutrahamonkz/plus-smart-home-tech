package ru.yandex.practicum.handler.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.repository.ScenarioRepository;

@Component
@RequiredArgsConstructor
public class ScenarioRemovedHandler implements HubEventHandler {

    private final ScenarioRepository scenarioRepository;

    @Override
    public String getEventType() {
        return ScenarioRemovedHandler.class.getCanonicalName();
    }

    @Override
    public void handle(HubEventAvro hubEvent) {
        String scenario = ((ScenarioRemovedEventAvro) hubEvent.getPayload()).getName();
        scenarioRepository.deleteByName(scenario);
    }
}
