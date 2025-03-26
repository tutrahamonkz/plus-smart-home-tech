package ru.yandex.practicum.kafka;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class RecordHandler {
    private static final Map<String, SensorsSnapshotAvro> snapshots = new HashMap<>();

    Optional<SensorsSnapshotAvro> updateState(SensorEventAvro event) {

        SensorsSnapshotAvro snapshot;
        if(snapshots.containsKey(event.getHubId())) {
            snapshot = snapshots.get(event.getHubId());
        } else {
            snapshot = SensorsSnapshotAvro.newBuilder()
                    .setHubId(event.getHubId())
                    .setTimestamp(event.getTimestamp())
                    .setSensorsState(new HashMap<>())
                    .build();
            snapshots.put(event.getHubId(), snapshot);
        }
        Map<String, SensorStateAvro> states = snapshot.getSensorsState();
        if(!states.isEmpty() && states.containsKey(event.getHubId())) {
            SensorStateAvro oldState = states.get(event.getHubId());
            if(oldState.getTimestamp().isAfter(event.getTimestamp()) ||
            oldState.getData().equals(event.getPayload())) {
                return Optional.empty();
            }
        }
        SensorStateAvro state = SensorStateAvro.newBuilder()
                .setData(event.getPayload())
                .setTimestamp(event.getTimestamp())
                .build();
        snapshot.getSensorsState().put(event.getHubId(), state);
        snapshot.setTimestamp(event.getTimestamp());
        return Optional.of(snapshot);
    }
}