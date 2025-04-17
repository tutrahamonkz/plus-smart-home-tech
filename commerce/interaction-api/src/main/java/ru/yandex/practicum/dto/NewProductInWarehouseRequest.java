package ru.yandex.practicum.dto;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@ToString
public class NewProductInWarehouseRequest {

    private UUID productId;

    private Boolean fragile;

    private DimensionDto dimension;

    @Min(1)
    private Double weight;
}
