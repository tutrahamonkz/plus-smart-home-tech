package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.dto.ProductCategory;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.UpdateQuantityState;

public interface ProductService {

    Page<ProductDto> getProductsByCategory(ProductCategory category, Pageable pageable);

    ProductDto createProduct(ProductDto product);

    public ProductDto updateProduct(ProductDto product);

    public Boolean removeProductFromStore(String productId);

    public Boolean quantityState(UpdateQuantityState quantity);

    public ProductDto getProduct(String productId);
}