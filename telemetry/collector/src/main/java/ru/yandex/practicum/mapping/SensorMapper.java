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

    ClimateSensorAvro mapClimateSensorToAvro(ClimateSensorProto event);
    LightSensorAvro mapLightSensorToAvro(LightSensorProto event);
    MotionSensorAvro mapMotionSensorToAvro(MotionSensorProto event);
    SwitchSensorAvro mapSwitchSensorToAvro(SwitchSensorProto event);
    TemperatureSensorAvro mapTemperatureSensorToAvro(TemperatureSensorProto event);

    @Named("mapPayload")
    default Object mapPayload(SensorEventProto event) {
        return switch (event.getPayloadCase()) {
            case LIGHT_SENSOR_EVENT -> mapLightSensorToAvro(event.getLightSensorEvent());
            case MOTION_SENSOR_EVENT -> mapMotionSensorToAvro(event.getMotionSensorEvent());
            case CLIMATE_SENSOR_EVENT -> mapClimateSensorToAvro(event.getClimateSensorEvent());
            case SWITCH_SENSOR_EVENT -> mapSwitchSensorToAvro(event.getSwitchSensorEvent());
            case TEMPERATURE_SENSOR_EVENT -> mapTemperatureSensorToAvro(event.getTemperatureSensorEvent());
            default -> throw new BadRequestException("Задан неверный тип сенсора " + event.getPayloadCase());
        };
    }

    default Instant map(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }
}