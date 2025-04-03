package ru.yandex.practicum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;

@Entity
@Table(name = "sensors")
@Setter
public class Sensor {

    @Id
    @Column(name = "id")
    String id;

    @Column(name = "hub_id")
    String hubId;
}
