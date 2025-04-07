package ru.yandex.practicum.mapping;

import com.google.protobuf.Timestamp;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Instant;

public class SensorMapperHandle {

    public static SensorEventAvro mapClimate(SensorEventProto event) {
        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(map(event.getTimestamp()))
                .setPayload(map(event.getClimateSensorEvent()))
                .build();
    }

    public static SensorEventAvro mapLight(SensorEventProto event) {
        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(map(event.getTimestamp()))
                .setPayload(map(event.getLightSensorEvent()))
                .build();
    }

    public static SensorEventAvro mapMotion(SensorEventProto event) {
        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(map(event.getTimestamp()))
                .setPayload(map(event.getMotionSensorEvent()))
                .build();
    }

    public static SensorEventAvro mapSwitch(SensorEventProto event) {
        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(map(event.getTimestamp()))
                .setPayload(map(event.getSwitchSensorEvent()))
                .build();
    }

    public static SensorEventAvro mapTemperature(SensorEventProto event) {
        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(map(event.getTimestamp()))
                .setPayload(map(event.getTemperatureSensorEvent()))
                .build();
    }

    public static ClimateSensorAvro map(ClimateSensorProto event) {
        return ClimateSensorAvro.newBuilder()
                .setCo2Level(event.getCo2Level())
                .setHumidity(event.getHumidity())
                .setTemperatureC(event.getTemperatureC())
                .build();
    }

    public static LightSensorAvro map(LightSensorProto event) {
        return LightSensorAvro.newBuilder()
                .setLinkQuality(event.getLinkQuality())
                .setLuminosity(event.getLuminosity())
                .build();
    }

    public static MotionSensorAvro map(MotionSensorProto event) {
        return MotionSensorAvro.newBuilder()
                .setLinkQuality(event.getLinkQuality())
                .setMotion(event.getMotion())
                .setVoltage(event.getVoltage())
                .build();
    }

    public static SwitchSensorAvro map(SwitchSensorProto event) {
        return SwitchSensorAvro.newBuilder()
                .setState(event.getState())
                .build();
    }

    public static TemperatureSensorAvro map(TemperatureSensorProto event) {
        return TemperatureSensorAvro.newBuilder()
                .setTemperatureC(event.getTemperatureC())
                .setTemperatureF(event.getTemperatureF())
                .build();
    }

    private static Instant map(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }
}
