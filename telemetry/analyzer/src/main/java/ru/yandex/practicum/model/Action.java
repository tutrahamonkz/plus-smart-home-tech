package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Setter
@Table(name = "actions")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String id;

    @Column(name = "type")
    String type;

    @Column(name = "value")
    Integer value;
}
