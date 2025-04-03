package ru.yandex.practicum.mapping;

import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.model.Action;
import ru.yandex.practicum.model.Condition;
import ru.yandex.practicum.model.Scenario;

public class ScenarioMapper {

    public static Scenario map(String hubId, ScenarioAddedEventAvro scenarioAddedEventAvro) {
        Scenario scenario = new Scenario();
        scenario.setHubId(hubId);
        scenario.setName(scenarioAddedEventAvro.getName());
        return scenario;
    }

    public static Action map(DeviceActionAvro actionAvro) {
        Action action = new Action();
        action.setType(actionAvro.getType().name());
        action.setValue(actionAvro.getValue());
        return action;
    }

    public static Condition map(ScenarioConditionAvro conditionAvro) {
        Condition condition = new Condition();
        condition.setType(conditionAvro.getType().name());
        condition.setOperation(conditionAvro.getOperation().name());
        return condition;
    }
}
