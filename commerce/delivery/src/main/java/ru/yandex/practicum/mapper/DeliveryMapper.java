package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.Delivery;

public class DeliveryMapper {

    public static Delivery mapToDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = new Delivery();
        delivery.setFromAddress(DeliveryMapper.mapToAddress(deliveryDto.getFromAddress()));
        delivery.setToAddress(DeliveryMapper.mapToAddress(deliveryDto.getToAddress()));
        delivery.setOrderId(deliveryDto.getOrderId());
        delivery.setDeliveryState(deliveryDto.getDeliveryState());
        return delivery;
    }

    public static Address mapToAddress(AddressDto address) {
        Address newAddress = new Address();
        newAddress.setCity(address.getCity());
        newAddress.setCountry(address.getCountry());
        newAddress.setStreet(address.getStreet());
        newAddress.setFlat(address.getFlat());
        newAddress.setHouse(address.getHouse());
        return newAddress;
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
