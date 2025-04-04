package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SnapshotServiceImpl {

    public Optional<DeviceActionRequest> updateState(SensorsSnapshotAvro sensorsSnapshotAvro) {
        return Optional.empty();
    }
}
