package ru.yandex.practicum.model.hub;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ScenarioCondition {

    @NotBlank
    String sensorId;

    @NotNull
    ConditionType type;

    @NotNull
    ConditionOperation operation;

    @NotNull
    Integer value;
}