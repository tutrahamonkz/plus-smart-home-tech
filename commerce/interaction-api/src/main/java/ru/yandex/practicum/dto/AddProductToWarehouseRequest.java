package ru.yandex.practicum.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AddProductToWarehouseRequest {

    @NotBlank
    private String productId;

    @Min(1)
    private Integer quantity;
}
