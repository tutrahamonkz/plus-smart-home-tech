package ru.yandex.practicum.model.hub;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DeviceAddedEvent extends HubEvent {
    String id;
    DeviceType deviceType;

    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED;
    }
}