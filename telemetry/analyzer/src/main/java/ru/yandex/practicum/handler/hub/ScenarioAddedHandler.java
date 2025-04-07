package ru.yandex.practicum.handler.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.mapping.HubMapper;
import ru.yandex.practicum.model.*;
import ru.yandex.practicum.repository.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScenarioAddedHandler implements HubEventHandler {

    private final ScenarioRepository scenarioRepository;
    private final ConditionRepository conditionRepository;
    private final ActionRepository actionRepository;
    private final SensorRepository sensorRepository;
    private final ScenarioConditionRepository scenarioConditionRepository;
    private final ScenarioActionRepository scenarioActionRepository;

    @Override
    public String getEventType() {
        return ScenarioAddedEventAvro.class.getCanonicalName();
    }

    @Override
    @Transactional
    public void handle(HubEventAvro hubEvent) {
        ScenarioAddedEventAvro scenarioAvro = (ScenarioAddedEventAvro) hubEvent.getPayload();

        Scenario scenario = scenarioRepository.findByHubIdAndName(hubEvent.getHubId(), scenarioAvro.getName())
                .orElseGet(() -> HubMapper.mapToScenario(hubEvent));

        scenarioRepository.save(scenario);

        // Сохраняем ScenarioActions
        List<ScenarioAction> scenarioActions = new ArrayList<>();
        for (DeviceActionAvro actionAvro : scenarioAvro.getActions()) {
            Sensor sensor = getSensorById(actionAvro.getSensorId());

            Action action = HubMapper.mapToAction(actionAvro);
            actionRepository.save(action);

            ScenarioAction scenarioAction = new ScenarioAction();
            scenarioAction.setScenario(scenario);
            scenarioAction.setAction(action);
            scenarioAction.setSensor(sensor);
            scenarioActions.add(scenarioAction);
        }
        scenarioActionRepository.saveAll(scenarioActions);

        // Сохраняем ScenarioConditions
        List<ScenarioCondition> scenarioConditions = new ArrayList<>();
        for (ScenarioConditionAvro conditionAvro : scenarioAvro.getConditions()) {
            Sensor sensor = getSensorById(conditionAvro.getSensorId());

            Condition condition = HubMapper.mapToCondition(conditionAvro);
            conditionRepository.save(condition);

            ScenarioCondition scenarioCondition = new ScenarioCondition();
            scenarioCondition.setScenario(scenario);
            scenarioCondition.setCondition(condition);
            scenarioCondition.setSensor(sensor);
            scenarioConditions.add(scenarioCondition);
        }
        scenarioConditionRepository.saveAll(scenarioConditions);
    }

    private Sensor getSensorById(String id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Сенсор с ID " + id + " отсутствует"));
    }
}
