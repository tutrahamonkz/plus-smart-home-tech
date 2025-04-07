package ru.yandex.practicum.mapping;

import com.google.protobuf.Timestamp;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Instant;

public class HubMapperHandle {

    public static DeviceActionAvro map(DeviceActionProto event) {
        return DeviceActionAvro.newBuilder()
                .setSensorId(event.getSensorId())
                .setType(ActionTypeAvro.valueOf(event.getType().name()))
                .setValue(event.getValue())
                .build();

    }

    public static ScenarioConditionAvro map(ScenarioConditionProto event) {
        return ScenarioConditionAvro.newBuilder()
                .setSensorId(event.getSensorId())
                .setOperation(ConditionOperationAvro.valueOf(event.getOperation().name()))
                .setType(ConditionTypeAvro.valueOf(event.getType().name()))
                .setValue(event.hasBoolValue() ? event.getBoolValue() : event.hasIntValue() ? event.getIntValue() : null)
                .build();
    }

    public static ScenarioAddedEventAvro map(ScenarioAddedEventProto event) {
        return ScenarioAddedEventAvro.newBuilder()
                .setName(event.getName())
                .setConditions(event.getConditionList().stream()
                        .map(HubMapperHandle::map)
                        .toList())
                .setActions(event.getActionList().stream()
                        .map(HubMapperHandle::map)
                        .toList())
                .build();
    }

    public static ScenarioRemovedEventAvro map(ScenarioRemovedEventProto event) {
        return ScenarioRemovedEventAvro.newBuilder()
                .setName(event.getName())
                .build();
    }

    public static DeviceAddedEventAvro map(DeviceAddedEventProto event) {
        return DeviceAddedEventAvro.newBuilder()
                .setId(event.getId())
                .setType(DeviceTypeAvro.valueOf(event.getType().name()))
                .build();
    }

    public static DeviceRemovedEventAvro map(DeviceRemovedEventProto event) {
        return DeviceRemovedEventAvro.newBuilder()
                .setId(event.getId())
                .build();
    }

    public static HubEventAvro mapScenarioAdded(HubEventProto event) {
        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(map(event.getTimestamp()))
                .setPayload(map(event.getScenarioAdded()))
                .build();
    }

    public static HubEventAvro mapScenarioRemoved(HubEventProto event) {
        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(map(event.getTimestamp()))
                .setPayload(map(event.getScenarioRemoved()))
                .build();
    }

    public static HubEventAvro mapDeviceAdded(HubEventProto event) {
        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(map(event.getTimestamp()))
                .setPayload(map(event.getDeviceAdded()))
                .build();
    }

    public static HubEventAvro mapDeviceRemoved(HubEventProto event) {
        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(map(event.getTimestamp()))
                .setPayload(map(event.getDeviceRemoved()))
                .build();
    }

    private static Instant map(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }
}
