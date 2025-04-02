package ru.yandex.practicum.model;

import jakarta.persistence.*;

@Entity
@Table(name = "actions")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "type")
    String type;

    @Column(name = "value")
    Integer value;
}
