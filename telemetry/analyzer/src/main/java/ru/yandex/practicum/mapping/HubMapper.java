package ru.yandex.practicum.mapping;

import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.model.Action;
import ru.yandex.practicum.model.Condition;
import ru.yandex.practicum.model.Scenario;

public class HubMapper {

    public static Scenario mapScenario(String hubId, ScenarioAddedEventAvro scenarioAddedEventAvro) {
        Scenario scenario = new Scenario();
        scenario.setHubId(hubId);
        scenario.setName(scenarioAddedEventAvro.getName());
        return scenario;
    }

    public static Action mapAction(DeviceActionAvro deviceActionAvro) {
        Action action = new Action();
        action.setType(deviceActionAvro.getType().name());
        action.setValue(deviceActionAvro.getValue());
        return action;
    }

    public static Condition mapCondition(ScenarioConditionAvro scenarioConditionAvro) {
        Condition condition = new Condition();
        condition.setType(scenarioConditionAvro.getType().name());
        condition.setOperation(scenarioConditionAvro.getOperation().name());
        if (scenarioConditionAvro.getValue() != null) {
            if (scenarioConditionAvro.getValue() instanceof Integer) {
                condition.setValue((Integer) scenarioConditionAvro.getValue());
            } else {
                condition.setValue(0);
            }
        }
        return condition;
    }
}
