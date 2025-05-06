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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_address", referencedColumnName = "id")
    private Address fromAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_address", referencedColumnName = "id")
    private Address toAddress;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "delivery_state")
    private DeliveryState deliveryState;
}
