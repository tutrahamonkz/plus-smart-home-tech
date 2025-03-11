package ru.yandex.practicum.model.hub;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ScenarioCondition {

    @NotBlank
    String sensorId;

    @NotNull
    ConditionType conditionType;

    @NotNull
    ConditionOperation conditionOperation;

    @NotNull
    Integer value;
}
