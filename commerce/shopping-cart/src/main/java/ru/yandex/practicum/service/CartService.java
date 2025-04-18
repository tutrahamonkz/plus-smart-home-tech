package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.ShoppingCartDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CartService {

    ShoppingCartDto getShoppingCart(String userName);

    ShoppingCartDto addProductToCart(String userName, Map<UUID, Integer> products);

    void deleteCart(String userName);

    ShoppingCartDto removeProductInCart(String userName, List<UUID> products);

    ShoppingCartDto changeQuantity(String userName, ChangeProductQuantityRequest changeProductQuantityRequest);
}
