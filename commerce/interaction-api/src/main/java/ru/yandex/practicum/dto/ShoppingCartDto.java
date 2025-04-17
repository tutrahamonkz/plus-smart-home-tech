package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ShoppingCartDto {

    private Long shoppingCartId;

    private Map<String, Integer> products;
}
