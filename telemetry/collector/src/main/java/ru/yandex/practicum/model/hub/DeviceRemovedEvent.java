package ru.yandex.practicum.model.hub;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DeviceRemovedEvent extends HubEvent {
    String id;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVED;
    }
}