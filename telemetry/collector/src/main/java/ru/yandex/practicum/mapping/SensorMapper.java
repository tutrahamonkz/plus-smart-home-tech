package ru.yandex.practicum.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.exception.BadRequestException;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.model.sensor.*;

@Mapper
public interface SensorMapper {

    SensorMapper INSTANCE = Mappers.getMapper(SensorMapper.class);

    @Mapping(target = "payload", source = ".", qualifiedByName = "mapPayload")
    SensorEventAvro mapSensorToAvro(SensorEvent event);

    @Named("mapPayload")
    default Object mapPayload(SensorEvent event) {
        return switch (event.getType()) {
            case LIGHT_SENSOR_EVENT -> mapSensorToAvro((LightSensorEvent) event);
            case MOTION_SENSOR_EVENT -> mapSensorToAvro((MotionSensorEvent) event);
            case CLIMATE_SENSOR_EVENT -> mapSensorToAvro((ClimateSensorEvent) event);
            case SWITCH_SENSOR_EVENT -> mapSensorToAvro((SwitchSensorEvent) event);
            case TEMPERATURE_SENSOR_EVENT -> mapSensorToAvro((TemperatureSensorEvent) event);
            default -> throw new BadRequestException("Задан неверный тип сенсора " + event.getType());
        };
    }

    ClimateSensorAvro mapSensorToAvro(ClimateSensorEvent event);
    LightSensorAvro mapSensorToAvro(LightSensorEvent event);
    MotionSensorAvro mapSensorToAvro(MotionSensorEvent event);
    SwitchSensorAvro mapSensorToAvro(SwitchSensorEvent event);
    TemperatureSensorAvro mapSensorToAvro(TemperatureSensorEvent event);
}