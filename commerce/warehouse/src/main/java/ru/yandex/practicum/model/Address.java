package ru.yandex.practicum.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "addresses")
public class Address {

    @Id
    private Long id;

    private String country;

    private String city;

    private String street;

    private String house;

    private String flat;
}
