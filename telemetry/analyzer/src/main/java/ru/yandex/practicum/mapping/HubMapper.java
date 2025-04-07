package ru.yandex.practicum.mapping;

import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.model.Action;
import ru.yandex.practicum.model.Condition;
import ru.yandex.practicum.model.Scenario;
import ru.yandex.practicum.model.Sensor;

public class HubMapper {

    public static Scenario mapToScenario(HubEventAvro hubEventAvro) {
        ScenarioAddedEventAvro addedEventAvro = (ScenarioAddedEventAvro) hubEventAvro.getPayload();
        Scenario scenario = new Scenario();
        scenario.setHubId(hubEventAvro.getHubId());
        scenario.setName(addedEventAvro.getName());
        return scenario;
    }

    public static Action mapToAction(DeviceActionAvro deviceActionAvro) {
        Action action = new Action();
        action.setType(deviceActionAvro.getType().name());
        action.setValue(deviceActionAvro.getValue());
        return action;
    }

    public static Condition mapToCondition(ScenarioConditionAvro scenarioConditionAvro) {
        Condition condition = new Condition();
        condition.setType(scenarioConditionAvro.getType().name());
        condition.setOperation(scenarioConditionAvro.getOperation().name());
        condition.setValue(getConditionValue(scenarioConditionAvro.getValue()));
        return condition;
    }

    public static Sensor mapToSensor(String hubId, DeviceAddedEventAvro deviceAddedEventAvro) {
        Sensor sensor = new Sensor();
        sensor.setId(deviceAddedEventAvro.getId());
        sensor.setHubId(hubId);
        return sensor;
    }

    private static Integer getConditionValue(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Boolean) {
            return (Boolean) value ? 1 : 0;
        } else {
            return null;
        }
    }
}
