package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class ProductDto {

    @NotBlank
    private UUID productId;
    private String productName;
    private String description;
    private String imageSrc;
    private QuantityState quantityState;
    private ProductState productState;
    private ProductCategory productCategory;
    private Double price;
}
