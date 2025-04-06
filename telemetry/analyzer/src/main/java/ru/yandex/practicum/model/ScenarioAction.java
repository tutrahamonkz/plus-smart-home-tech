package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "scenario_actions")
public class ScenarioAction {

    @EmbeddedId
    private ScenarioActionKey id = new ScenarioActionKey();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("scenarioId")
    @JoinColumn(name = "scenario_id", nullable = false)
    private Scenario scenario;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("actionId")
    @JoinColumn(name = "action_id", nullable = false)
    private Action action;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("sensorId")
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;
}

