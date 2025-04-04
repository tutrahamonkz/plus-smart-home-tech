package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.mapping.HubMapper;
import ru.yandex.practicum.mapping.SnapshotMapper;
import ru.yandex.practicum.model.*;
import ru.yandex.practicum.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HubServiceImpl {

    private final ScenarioRepository scenarioRepository;
    private final ActionRepository actionRepository;
    private final ConditionRepository conditionRepository;
    private final ScenarioActionRepository scenarioActionRepository;
    private final ScenarioConditionRepository scenarioConditionRepository;
    private final SensorRepository sensorRepository;

    public void updateState(HubEventAvro hubEvent) {
        HubEventType type = HubEventType.valueOf(hubEvent.getPayload().getClass().getSimpleName());

        switch (type) {
            case DeviceAddedEventAvro:
                addSensor(hubEvent.getHubId(), (DeviceAddedEventAvro) hubEvent.getPayload());
                break;
            case DeviceRemovedEventAvro:
                removeSensor((DeviceRemovedEventAvro) hubEvent.getPayload());
                break;
            case ScenarioAddedEventAvro:
                addScenario(hubEvent.getHubId(), (ScenarioAddedEventAvro) hubEvent.getPayload());
                break;
            case ScenarioRemovedEventAvro:
                removeScenario((ScenarioRemovedEventAvro) hubEvent.getPayload());
                break;
        }
    }

    @Transactional
    public void addScenario(String hubId, ScenarioAddedEventAvro scenarioAvro) {
        Scenario scenario = HubMapper.mapScenario(hubId, scenarioAvro);
        scenarioRepository.save(scenario);

        List<Condition> conditions = new ArrayList<>();
        List<ScenarioCondition> scenarioConditions = new ArrayList<>();

        scenarioAvro.getConditions().forEach(conditionAvro -> {
            Condition condition = HubMapper.mapCondition(conditionAvro);
            conditions.add(condition);
            ScenarioCondition scenarioCondition = new ScenarioCondition();
            scenarioCondition.setId(new ScenarioConditionKey());
            scenarioCondition.setScenario(scenario);
            scenarioCondition.setCondition(condition);
            scenarioCondition.setSensor(sensorRepository.findById(conditionAvro.getSensorId()).orElseThrow());
            scenarioConditions.add(scenarioCondition);
        });

        conditionRepository.saveAll(conditions);
        scenarioConditionRepository.saveAll(scenarioConditions);

        List<Action> actions = new ArrayList<>();
        List<ScenarioAction> scenarioActions = new ArrayList<>();

        scenarioAvro.getActions().forEach(actionAvro -> {
            Action action = HubMapper.mapAction(actionAvro);
            actions.add(action);
            ScenarioAction scenarioAction = new ScenarioAction();
            scenarioAction.setId(new ScenarioActionKey());
            scenarioAction.setScenario(scenario);
            scenarioAction.setAction(action);
            scenarioAction.setSensor(sensorRepository.findById(actionAvro.getSensorId()).orElseThrow());
            scenarioActions.add(scenarioAction);
        });

        actionRepository.saveAll(actions);
        scenarioActionRepository.saveAll(scenarioActions);
    }

    @Transactional
    public void removeScenario(ScenarioRemovedEventAvro scenario) {
        scenarioRepository.deleteByName(scenario.getName());
    }

    @Transactional
    public void addSensor(String hubId, DeviceAddedEventAvro event) {
        Sensor sensor = SnapshotMapper.map(hubId, event);
        sensorRepository.save(sensor);
    }

    @Transactional
    public void removeSensor(DeviceRemovedEventAvro event) {
        sensorRepository.deleteById(event.getId());
    }
}