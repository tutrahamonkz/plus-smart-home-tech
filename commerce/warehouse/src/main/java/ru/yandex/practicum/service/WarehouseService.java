package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.*;

public interface WarehouseService {

    void addProduct(NewProductInWarehouseRequest product);

    BookedProductsDto check(ShoppingCartDto shoppingCartDto);

    void addProductQuantity(AddProductToWarehouseRequest productQuantity);

    AddressDto getAddress();
}