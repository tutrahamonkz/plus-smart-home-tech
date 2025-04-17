package ru.yandex.practicum.dto;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class ProductDto {

    private UUID productId;

    private String productName;

    private String description;

    private String imageSrc;

    private QuantityState quantityState;

    private ProductState productState;

    private ProductCategory productCategory;

    @Min(1)
    private Double price;
}
