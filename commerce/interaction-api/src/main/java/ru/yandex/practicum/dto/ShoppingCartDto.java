package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;

@Getter
@Builder
@ToString
public class ShoppingCartDto {

    private UUID shoppingCartId;

    private Map<UUID, Integer> products;
}
