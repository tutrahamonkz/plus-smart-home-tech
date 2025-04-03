package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Setter
@Table(name = "conditions")
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String id;

    @Column(name = "type")
    String type;

    @Column(name = "operation")
    String operation;

    @Column(name = "value")
    Integer value;
}
