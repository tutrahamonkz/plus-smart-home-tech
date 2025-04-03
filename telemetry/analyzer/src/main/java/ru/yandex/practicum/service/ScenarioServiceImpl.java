package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.mapping.ScenarioMapper;
import ru.yandex.practicum.repository.ActionRepository;
import ru.yandex.practicum.repository.ConditionRepository;
import ru.yandex.practicum.repository.ScenarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScenarioServiceImpl {

    private final ScenarioRepository scenarioRepository;
    private final ActionRepository actionRepository;
    private final ConditionRepository conditionRepository;

    public void addScenario(String hubId, ScenarioAddedEventAvro scenario) {
        scenarioRepository.save(ScenarioMapper.map(hubId, scenario));
        List<DeviceActionAvro> actions = scenario.getActions();
        List<ScenarioConditionAvro> conditions = scenario.getConditions();
        if (!actions.isEmpty()) {
            actionRepository.saveAll(actions.stream().map(ScenarioMapper::map).toList());
        }
        if (!conditions.isEmpty()) {
            conditionRepository.saveAll(conditions.stream().map(ScenarioMapper::map).toList());
        }
    }

    public void removeScenario(ScenarioRemovedEventAvro scenario) {
        scenarioRepository.deleteByName(scenario.getName());
    }
}
