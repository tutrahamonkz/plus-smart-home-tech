package ru.yandex.practicum.model.hub;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DeviceAction {
    @NotBlank
    String sensorId;
    @NotNull
    ActionType type;
    Integer value;
}