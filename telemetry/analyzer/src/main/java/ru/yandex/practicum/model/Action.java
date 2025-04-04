package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "actions")
@EqualsAndHashCode
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String type;

    @Column(nullable = false)
    Integer value;

    @OneToMany(mappedBy = "action")
    private List<ScenarioAction> scenarioActions;
}
