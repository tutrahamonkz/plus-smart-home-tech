package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.dto.ProductCategory;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.QuantityState;

import java.util.UUID;

public interface ProductService {

    Page<ProductDto> getProductsByCategory(ProductCategory category, Pageable pageable);

    ProductDto createProduct(ProductDto product);

    public ProductDto updateProduct(ProductDto product);

    public Boolean removeProductFromStore(UUID productId);

    public Boolean quantityState(UUID productId, QuantityState quantityState);

    public ProductDto getProduct(UUID productId);
}