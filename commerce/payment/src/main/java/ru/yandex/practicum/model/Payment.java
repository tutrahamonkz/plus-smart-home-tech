package ru.yandex.practicum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "total_payment")
    private Double totalPayment;

    @Column(name = "delivery_total")
    private Double deliveryTotal;

    @Column(name = "fee_total")
    private Double feeTotal;

    @Column(name = "state")
    private PaymentState state;
}
