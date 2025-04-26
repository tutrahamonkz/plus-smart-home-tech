package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.*;

import java.util.Map;
import java.util.UUID;

public interface WarehouseService {

    void addProduct(NewProductInWarehouseRequest product);

    BookedProductsDto check(ShoppingCartDto shoppingCartDto);

    void addProductQuantity(AddProductToWarehouseRequest productQuantity);

    AddressDto getAddress();

    void shipped(ShippedToDeliveryRequest shippedToDeliveryRequest);

    void returnProduct(Map<UUID, Integer> returnMap);

    BookedProductsDto assemblyProduct(AssemblyProductsForOrderRequest request);
}