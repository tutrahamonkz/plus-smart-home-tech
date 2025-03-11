package ru.yandex.practicum.model.hub;

import lombok.Getter;

@Getter
public class DeviceRemovedEvent extends HubEvent {
    String id;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVED;
    }
}