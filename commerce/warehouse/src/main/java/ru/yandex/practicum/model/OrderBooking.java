package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "order_bookings")
public class OrderBooking {

    @Id
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "delivery_id")
    private UUID deliveryId;

    @ManyToMany
    @JoinTable(
            name = "bookings_warehouse_products",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "warehouse_product_id", referencedColumnName = "id")
    )
    private List<WarehouseProduct> products;
}
