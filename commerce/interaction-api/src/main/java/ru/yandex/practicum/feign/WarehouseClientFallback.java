package ru.yandex.practicum.feign;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.exception.ServiceTemporarilyUnavailable;

@Component
public class WarehouseClientFallback implements WarehouseClient {

    @Override
    public void addProduct(NewProductInWarehouseRequest product) {

    }

    @Override
    public BookedProductsDto check(ShoppingCartDto shoppingCartDto) {
        throw new ServiceTemporarilyUnavailable("Сервер склада недоступен");
    }

    @Override
    public void addProductQuantity(AddProductToWarehouseRequest productQuantity) {

    }

    @Override
    public AddressDto getAddress() {
        return null;
    }
}
