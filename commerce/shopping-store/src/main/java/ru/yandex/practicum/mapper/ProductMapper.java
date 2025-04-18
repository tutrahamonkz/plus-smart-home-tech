package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.model.Product;

import java.util.Optional;

public class ProductMapper {
    public static ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .imageSrc(product.getImageSrc())
                .quantityState(product.getQuantityState())
                .productState(product.getProductState())
                .productCategory(product.getProductCategory())
                .price(product.getPrice())
                .build();
    }

    public static Product toProduct(ProductDto productDto) {
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setImageSrc(productDto.getImageSrc());
        product.setQuantityState(productDto.getQuantityState());
        product.setProductState(productDto.getProductState());
        product.setProductCategory(productDto.getProductCategory());
        product.setPrice(productDto.getPrice());
        return product;
    }

    public static Product updateToProduct(ProductDto productDto, Product product) {
        product.setId(productDto.getProductId());
        product.setProductName(Optional.ofNullable(productDto.getProductName()).orElse(product.getProductName()));
        product.setDescription(Optional.ofNullable(productDto.getDescription()).orElse(product.getDescription()));
        product.setImageSrc(Optional.ofNullable(productDto.getImageSrc()).orElse(product.getImageSrc()));
        product.setQuantityState(Optional.ofNullable(productDto.getQuantityState()).orElse(product.getQuantityState()));
        product.setProductState(Optional.ofNullable(productDto.getProductState()).orElse(product.getProductState()));
        product.setProductState(Optional.ofNullable(productDto.getProductState()).orElse(product.getProductState()));
        product.setProductCategory(Optional.ofNullable(productDto.getProductCategory()).orElse(product.getProductCategory()));
        product.setPrice(Optional.ofNullable(productDto.getPrice()).orElse(product.getPrice()));
        return product;
    }
}
