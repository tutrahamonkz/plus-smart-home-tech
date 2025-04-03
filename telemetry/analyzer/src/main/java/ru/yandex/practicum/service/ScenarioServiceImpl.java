package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.mapping.ScenarioMapper;
import ru.yandex.practicum.repository.ScenarioRepository;

@Service
@RequiredArgsConstructor
public class ScenarioServiceImpl {

    private final ScenarioRepository scenarioRepository;

    public void addScenario(String hubId, ScenarioAddedEventAvro scenario) {
        scenarioRepository.save(ScenarioMapper.map(hubId, scenario));
    }

    public void removeScenario(ScenarioRemovedEventAvro scenario) {
        scenarioRepository.deleteByName(scenario.getName());
    }
}
