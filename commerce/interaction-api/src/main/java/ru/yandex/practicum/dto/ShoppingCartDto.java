package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ShoppingCartDto {

    String shoppingCartId;

    Map<String, Integer> products;
}
