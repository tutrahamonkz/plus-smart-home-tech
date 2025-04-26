package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ShippedToDeliveryRequest {

    private UUID orderId;

    private UUID deliveryId;
}
