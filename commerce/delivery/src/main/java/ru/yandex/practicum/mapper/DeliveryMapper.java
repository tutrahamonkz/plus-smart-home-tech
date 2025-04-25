package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.model.Delivery;

import java.util.UUID;

public class DeliveryMapper {

    public static Delivery mapToDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = new Delivery();
        delivery.setFromAddress(UUID.fromString(deliveryDto.getFromAddress().getCity()));
        delivery.setToAddress(UUID.fromString(deliveryDto.getToAddress().getCity()));
        delivery.setOrderId(deliveryDto.getOrderId());
        delivery.setDeliveryState(deliveryDto.getDeliveryState());
        return delivery;
    }

    public static DeliveryDto mapToDeliveryDto(Delivery delivery) {
        return DeliveryDto.builder()
                .deliveryId(delivery.getDeliveryId())
                .fromAddress(AddressDto.builder().build())
                .toAddress(AddressDto.builder().build())
                .orderId(delivery.getOrderId())
                .deliveryState(delivery.getDeliveryState())
                .build();
    }
}
