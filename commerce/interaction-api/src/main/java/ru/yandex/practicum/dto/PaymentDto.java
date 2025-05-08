package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class PaymentDto {

    private UUID paymentId;

    private Double totalPayment;

    private Double deliveryTotal;

    private Double feeTotal;
}
