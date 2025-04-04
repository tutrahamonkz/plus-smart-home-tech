package ru.yandex.practicum.model;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Embeddable
@EqualsAndHashCode
public class ScenarioConditionKey implements Serializable {
    private Long scenarioId;
    private String sensorId;
    private Long conditionId;
}
