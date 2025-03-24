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
public interface HubMapper {
    HubMapper INSTANCE = Mappers.getMapper(HubMapper.class);

    @Mapping(target = "payload", source = ".", qualifiedByName = "mapPayload")
    HubEventAvro mapHubToAvro(HubEventProto event);

    DeviceAddedEventAvro mapHubToAvro(DeviceAddedEventProto event);
    DeviceRemovedEventAvro mapHubToAvro(DeviceRemovedEventProto event);
    ScenarioAddedEventAvro mapHubToAvro(ScenarioAddedEventProto event);
    ScenarioRemovedEventAvro mapHubToAvro(ScenarioRemovedEventProto event);

    @Named("mapPayload")
    default Object mapPayload(HubEventProto event) {
        return switch (event.getPayloadCase()) {
            case SCENARIO_ADDED -> mapHubToAvro(event.getScenarioAdded());
            case SCENARIO_REMOVED -> mapHubToAvro(event.getScenarioRemoved());
            case DEVICE_ADDED -> mapHubToAvro(event.getDeviceAdded());
            case DEVICE_REMOVED -> mapHubToAvro(event.getDeviceRemoved());
            default -> throw new BadRequestException("Задан неверный тип сенсора " + event.getPayloadCase());
        };
    }

    default Instant map(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }

    default DeviceTypeAvro map(DeviceTypeProto type) {
        if (type == DeviceTypeProto.UNRECOGNIZED) {
            return null; // Или выберите другое значение по умолчанию
        }
        return DeviceTypeAvro.valueOf(type.name());
    }
}