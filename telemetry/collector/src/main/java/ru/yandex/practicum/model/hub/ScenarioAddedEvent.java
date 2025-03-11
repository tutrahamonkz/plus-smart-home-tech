package ru.yandex.practicum.model.hub;

import lombok.Getter;

import java.util.List;

@Getter
public class ScenarioAddedEvent extends HubEvent {
    String name;
    List<ScenarioCondition> conditions;
    List<DeviceAction> actions;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}