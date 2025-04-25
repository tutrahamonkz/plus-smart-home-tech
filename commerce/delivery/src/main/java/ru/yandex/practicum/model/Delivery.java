package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.dto.DeliveryState;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "deliveries")
public class Delivery {

    @Id
    @Column(name = "delivery_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID deliveryId;

    @Column(name = "from_address")
    private UUID fromAddress;

    @Column(name = "to_address")
    private UUID toAddress;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "delivery_state")
    private DeliveryState deliveryState;
}
