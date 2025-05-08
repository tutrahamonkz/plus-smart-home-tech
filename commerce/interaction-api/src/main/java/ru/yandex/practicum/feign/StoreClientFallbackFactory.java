package ru.yandex.practicum.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.ProductCategory;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.QuantityState;
import ru.yandex.practicum.exception.ServiceTemporarilyUnavailable;

import java.util.UUID;

@Component
public class StoreClientFallbackFactory implements FallbackFactory<StoreClient> {

    @Override
    public StoreClient create(Throwable cause) {
        return new StoreClient() {
            @Override
            public Page<ProductDto> getProducts(ProductCategory category, Pageable pageable) {
                throw new ServiceTemporarilyUnavailable("Сервер магазина недоступен");
            }

            @Override
            public ProductDto createProduct(ProductDto product) {
                throw new ServiceTemporarilyUnavailable("Сервер магазина недоступен");
            }

            @Override
            public ProductDto updateProduct(ProductDto product) {
                throw new ServiceTemporarilyUnavailable("Сервер магазина недоступен");
            }

            @Override
            public Boolean removeProductFromStore(String productId) {
                throw new ServiceTemporarilyUnavailable("Сервер магазина недоступен");
            }

            @Override
            public Boolean quantityState(UUID productId, QuantityState quantity) {
                throw new ServiceTemporarilyUnavailable("Сервер магазина недоступен");
            }

            @Override
            public ProductDto getProduct(UUID productId) {
                throw new ServiceTemporarilyUnavailable("Сервер магазина недоступен");
            }
        };
    }
}
