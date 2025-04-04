package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "conditions")
@EqualsAndHashCode
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String operation;

    @Column(nullable = false)
    Integer value;

    @OneToMany(mappedBy = "condition")
    private List<ScenarioCondition> scenarioConditions;
}
