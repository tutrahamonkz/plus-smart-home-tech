package ru.yandex.practicum.fallback;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.exception.ServiceTemporarilyUnavailable;
import ru.yandex.practicum.feign.WarehouseClient;

@Component
public class WarehouseClientFallback implements WarehouseClient {

    @Override
    public void addProduct(NewProductInWarehouseRequest product) {
        throw new ServiceTemporarilyUnavailable("Service warehouse temporarily unavailable");
    }

    @Override
    public BookedProductsDto check(ShoppingCartDto shoppingCartDto) {
        throw new ServiceTemporarilyUnavailable("Service warehouse temporarily unavailable");
    }

    @Override
    public void addProductQuantity(AddProductToWarehouseRequest productQuantity) {
        throw new ServiceTemporarilyUnavailable("Service warehouse temporarily unavailable");
    }

    @Override
    public AddressDto getAddress() {
        throw new ServiceTemporarilyUnavailable("Service warehouse temporarily unavailable");
    }
}