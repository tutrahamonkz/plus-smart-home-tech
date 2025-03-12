package ru.yandex.practicum.model.hub;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ScenarioRemovedEvent extends HubEvent {
    String name;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_REMOVED;
    }
}