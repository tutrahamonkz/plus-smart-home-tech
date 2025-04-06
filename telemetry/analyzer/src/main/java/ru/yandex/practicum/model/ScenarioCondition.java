package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "scenario_conditions")
public class ScenarioCondition {

    @EmbeddedId
    private ScenarioConditionKey id = new ScenarioConditionKey();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("scenarioId")
    @JoinColumn(name = "scenario_id", nullable = false)
    private Scenario scenario;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("conditionId")
    @JoinColumn(name = "condition_id", nullable = false)
    private Condition condition;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("sensorId")
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;
}

