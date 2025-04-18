package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookedProductsDto {

    private Double deliveryWeight;

    private Double deliveryVolume;

    private Boolean fragile;
}
