package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Setter
@Table(name = "scenarios")
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String id;

    @Column(name = "hub_id")
    String hubId;

    @Column(name = "name")
    String name;
}
