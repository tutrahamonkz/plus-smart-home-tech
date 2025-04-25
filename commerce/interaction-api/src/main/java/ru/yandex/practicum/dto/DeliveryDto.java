package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DeliveryDto {

    private UUID deliveryId;

    private AddressDto fromAddress;

    private AddressDto toAddress;

    private UUID orderId;

    private DeliveryState deliveryState;
}
