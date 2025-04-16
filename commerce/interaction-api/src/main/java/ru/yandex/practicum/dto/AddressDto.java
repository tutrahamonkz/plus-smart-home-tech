package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressDto {

    private String country;

    private String city;

    private String street;

    private String house;

    private String flat;
}
