package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewProductInWarehouseRequest {

    private String productId;

    private Boolean fragile;

    private DimensionDto dimension;

    private Double weight;
}
