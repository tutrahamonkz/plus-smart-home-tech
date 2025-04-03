package ru.yandex.practicum.kafka;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Optional;

@Component
public class SnapshotHandler {

    public Optional<DeviceActionRequest> updateState(SensorsSnapshotAvro snapshot) {
        return Optional.empty();
    }
}
