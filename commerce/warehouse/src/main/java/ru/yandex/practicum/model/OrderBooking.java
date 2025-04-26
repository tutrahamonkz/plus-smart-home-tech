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
    private UUID orderId;

    private UUID deliveryId;

    @ManyToMany
    @JoinTable(
            name = "bookings_warehouse_products",
            joinColumns = @JoinColumn(name = "booking_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "warehouse_product_id", referencedColumnName = "id")
    )
    private List<WarehouseProduct> products;
}
