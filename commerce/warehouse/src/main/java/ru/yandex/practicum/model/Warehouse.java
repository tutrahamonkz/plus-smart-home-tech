package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "warehouses")
public class Warehouse {

    @Id
    private Long id;

    @OneToOne()
    @JoinColumn(name = "address", referencedColumnName = "id")
    private Address address;

    @ManyToMany
    @JoinTable(
            name = "warehouses_warehouse_products",
            joinColumns = @JoinColumn(name = "warehouse_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "warehouse_product_id", referencedColumnName = "id")
    )
    private List<WarehouseProduct> products;
}
