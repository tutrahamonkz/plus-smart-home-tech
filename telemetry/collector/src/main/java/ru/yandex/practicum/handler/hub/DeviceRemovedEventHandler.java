package ru.yandex.practicum.handler.hub;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

public class DeviceRemovedEventHandler implements HubEventHandler {

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return null;
    }

    @Override
    public void handle(HubEventProto event) {

    }
}
