package ru.yandex.practicum.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateQuantityState {

    private String productId;
    private QuantityState quantityState;
}
