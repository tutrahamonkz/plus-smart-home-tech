package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateNewOrderRequest {

    private ShoppingCartDto shoppingCart;

    private AddressDto deliveryAddress;
}
