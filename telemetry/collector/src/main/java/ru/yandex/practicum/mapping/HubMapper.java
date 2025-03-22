package ru.yandex.practicum.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.exception.BadRequestException;
import ru.yandex.practicum.kafka.telemetry.event.*;

@Mapper
public interface HubMapper {
    HubMapper INSTANCE = Mappers.getMapper(HubMapper.class);

    @Mapping(target = "payload", source = ".", qualifiedByName = "mapPayload")
    HubEventAvro mapHubToAvro(HubEvent event);

    @Named("mapPayload")
    default Object mapPayload(HubEvent event) {
        return switch (event.getType()) {
            case SCENARIO_ADDED -> mapHubToAvro((ScenarioAddedEvent) event);
            case SCENARIO_REMOVED -> mapHubToAvro((ScenarioRemovedEvent) event);
            case DEVICE_ADDED -> mapHubToAvro((DeviceAddedEvent) event);
            case DEVICE_REMOVED -> mapHubToAvro((DeviceRemovedEvent) event);
            default -> throw new BadRequestException("Задан неверный тип сенсора " + event.getType());
        };
    }

    @Mapping(target = "type", source = "deviceType")
    DeviceAddedEventAvro mapHubToAvro(DeviceAddedEvent event);
    DeviceRemovedEventAvro mapHubToAvro(DeviceRemovedEvent event);
    ScenarioAddedEventAvro mapHubToAvro(ScenarioAddedEvent event);
    ScenarioRemovedEventAvro mapHubToAvro(ScenarioRemovedEvent event);
}