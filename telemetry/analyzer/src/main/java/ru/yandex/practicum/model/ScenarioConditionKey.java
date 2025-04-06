package ru.yandex.practicum.model;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class ScenarioConditionKey implements Serializable {
    private Long scenarioId;
    private Long conditionId;
    private String sensorId;
}
