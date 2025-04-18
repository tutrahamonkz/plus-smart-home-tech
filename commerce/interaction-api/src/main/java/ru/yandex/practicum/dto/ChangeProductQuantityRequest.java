package ru.yandex.practicum.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ChangeProductQuantityRequest {

    private UUID productId;

    @Min(1)
    private Integer newQuantity;
}
