package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.ShoppingCartDto;

import java.util.List;
import java.util.Map;

public interface CartService {

    ShoppingCartDto getShoppingCart(String userName);

    ShoppingCartDto addProductToCart(String userName, Map<String, Integer> products);

    void deleteCart(String userName);

    ShoppingCartDto removeProductInCart(String userName, List<String> products);

    ShoppingCartDto changeQuantity(String userName, ChangeProductQuantityRequest changeProductQuantityRequest);
}
