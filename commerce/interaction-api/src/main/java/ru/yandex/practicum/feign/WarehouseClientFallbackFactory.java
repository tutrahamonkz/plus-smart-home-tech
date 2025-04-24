package ru.yandex.practicum.feign;

import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.exception.ServiceTemporarilyUnavailable;

@Component
public class WarehouseClientFallbackFactory implements FallbackFactory<WarehouseClient> {

    @Override
    public WarehouseClient create(Throwable cause) {
        return new WarehouseClient() {
            @Override
            public void addProduct(NewProductInWarehouseRequest product) {
                throw new ServiceTemporarilyUnavailable("Сервер склада недоступен");
            }

            @Override
            public BookedProductsDto check(ShoppingCartDto shoppingCartDto) {
                if (cause instanceof FeignException.BadRequest) {
                    throw new ProductInShoppingCartLowQuantityInWarehouse(cause.getMessage());
                }
                throw new ServiceTemporarilyUnavailable("Сервер склада недоступен");
            }

            @Override
            public void addProductQuantity(AddProductToWarehouseRequest productQuantity) {
                throw new ServiceTemporarilyUnavailable("Сервер склада недоступен");
            }

            @Override
            public AddressDto getAddress() {
                throw new ServiceTemporarilyUnavailable("Сервер склада недоступен");
            }
        };
    }
}