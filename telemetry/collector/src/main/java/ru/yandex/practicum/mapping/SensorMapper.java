package ru.yandex.practicum.mapping;

import com.google.protobuf.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.exception.BadRequestException;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Instant;

@Mapper
public interface SensorMapper {

    SensorMapper INSTANCE = Mappers.getMapper(SensorMapper.class);

    @Mapping(target = "payload", source = ".", qualifiedByName = "mapPayload")
    SensorEventAvro mapSensorToAvro(SensorEventProto event);

    @Named("mapPayload")
    default Object mapPayload(SensorEventProto event) {
        return switch (event.getPayloadCase()) {
            case LIGHT_SENSOR_EVENT -> mapSensorToAvro((LightSensorProto) event);
            case MOTION_SENSOR_EVENT -> mapSensorToAvro((MotionSensorProto) event);
            case CLIMATE_SENSOR_EVENT -> mapSensorToAvro((ClimateSensorProto) event);
            case SWITCH_SENSOR_EVENT -> mapSensorToAvro((SwitchSensorProto) event);
            case TEMPERATURE_SENSOR_EVENT -> mapSensorToAvro((TemperatureSensorProto) event);
            default -> throw new BadRequestException("Задан неверный тип сенсора " + event.getPayloadCase());
        };
    }

    ClimateSensorAvro mapSensorToAvro(ClimateSensorProto event);
    LightSensorAvro mapSensorToAvro(LightSensorProto event);
    MotionSensorAvro mapSensorToAvro(MotionSensorProto event);
    SwitchSensorAvro mapSensorToAvro(SwitchSensorProto event);
    TemperatureSensorAvro mapSensorToAvro(TemperatureSensorProto event);

    default Instant map(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }
}