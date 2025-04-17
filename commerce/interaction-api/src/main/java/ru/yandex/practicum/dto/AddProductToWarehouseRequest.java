package ru.yandex.practicum.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class AddProductToWarehouseRequest {

    private UUID productId;

    @Min(1)
    private Integer quantity;
}
