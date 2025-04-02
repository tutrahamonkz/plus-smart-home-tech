package ru.yandex.practicum.model;

import jakarta.persistence.*;

@Entity
@Table(name = "scenarios")
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "hub_id")
    String hubId;

    @Column(name = "name")
    String name;
}
