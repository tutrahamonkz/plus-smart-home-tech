package ru.yandex.practicum.service;

import com.google.protobuf.Timestamp;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;
import ru.yandex.practicum.handler.sensor.SensorHandler;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.model.*;
import ru.yandex.practicum.repository.ScenarioRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SnapshotServiceImpl {

    @GrpcClient("hub-router")
    private HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterClient;

    private final ScenarioRepository scenarioRepository;

    private final Map<String, SensorHandler> handlers;

    public SnapshotServiceImpl(ScenarioRepository scenarioRepository, Set<SensorHandler> sensorHandlers) {
        this.scenarioRepository = scenarioRepository;
        this.handlers = sensorHandlers.stream()
                .collect(Collectors.toMap(
                        SensorHandler::getEventType,
                        Function.identity()
                ));
    }

    @Transactional
    public void updateState(SensorsSnapshotAvro snapshotAvro) {
        List<Scenario> scenarios = scenarioRepository.findAllByHubId(snapshotAvro.getHubId());

        scenarios.forEach(scenario -> {
            boolean conditionSatisfied = scenario.getScenarioConditions().stream()
                    .allMatch(scenarioCondition -> checkCondition(snapshotAvro, scenarioCondition));

            if (conditionSatisfied) {
                scenario.getScenarioActions().forEach(scenarioAction -> {
                    sendDeviceAction(scenario, scenarioAction, snapshotAvro.getTimestamp());
                });
            }
        });
    }

    private boolean checkCondition(SensorsSnapshotAvro snapshot, ScenarioCondition scenarioCondition) {
        Sensor sensor = scenarioCondition.getSensor();
        Condition condition = scenarioCondition.getCondition();

        SensorStateAvro sensorStateAvro = snapshot.getSensorsState().get(sensor.getId());

        if (sensorStateAvro == null) {
            return false;
        }

        SensorHandler handler = handlers.get(sensorStateAvro.getData().getClass().getCanonicalName());
        if (handler == null) {
            throw new IllegalArgumentException("Unknown sensor type " +
                    sensorStateAvro.getData().getClass().getCanonicalName());
        }

        Integer sensorValue = handler.getSensorValue(ConditionTypeAvro.valueOf(condition.getType()), sensorStateAvro);

        if (sensorValue == null) {
            return false;
        }

        int conditionValue = condition.getValue();
        ConditionOperationAvro operation = ConditionOperationAvro.valueOf(condition.getOperation());
        return switch (operation) {
            case EQUALS -> sensorValue == conditionValue;
            case LOWER_THAN -> sensorValue < conditionValue;
            case GREATER_THAN -> sensorValue > conditionValue;
            default -> throw new IllegalArgumentException("Unknown operation type " + operation);
        };
    }

    private void sendDeviceAction(Scenario scenario, ScenarioAction scenarioAction, Instant timestamp) {
        Sensor sensor = scenarioAction.getSensor();
        Action action = scenarioAction.getAction();

        DeviceActionRequest request = DeviceActionRequest.newBuilder()
                .setHubId(scenario.getHubId())
                .setScenarioName(scenario.getName())
                .setAction(DeviceActionProto.newBuilder()
                        .setSensorId(sensor.getId())
                        .setType(ActionTypeProto.valueOf(action.getType()))
                        .setValue(action.getValue())
                        .build())
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(timestamp.getEpochSecond())
                        .build())
                .build();

        log.info("Sending device action {}", request);
        hubRouterClient.handleDeviceAction(request);
    }
}
