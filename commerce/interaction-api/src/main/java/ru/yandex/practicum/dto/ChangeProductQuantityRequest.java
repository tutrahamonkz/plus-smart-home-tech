package ru.yandex.practicum.dto;

import lombok.Getter;

@Getter
public class ChangeProductQuantityRequest {

    String productId;

    Integer newQuantity;
}
