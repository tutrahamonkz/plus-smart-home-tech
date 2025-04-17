package ru.yandex.practicum.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "warehouse_products")
public class WarehouseProduct {

    @Id
    private UUID id;

    private Boolean fragile;

    private Double weight;

    private Double width;

    private Double height;

    private Double depth;

    private Integer quantity;
}
