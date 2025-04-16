package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.NewProductInWarehouseRequest;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.Warehouse;
import ru.yandex.practicum.model.WarehouseProduct;

public class WarehouseMapper {

    public static AddressDto toAddressDto(Warehouse warehouse) {
        Address address = warehouse.getAddress();
        return AddressDto.builder()
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .house(address.getHouse())
                .flat(address.getFlat())
                .build();
    }

    public static WarehouseProduct toWarehouseProduct(NewProductInWarehouseRequest newProduct) {
        WarehouseProduct product = new WarehouseProduct();
        product.setId(newProduct.getProductId());
        product.setFragile(newProduct.getFragile());
        product.setWeight(newProduct.getWeight());
        product.setWidth(newProduct.getDimension().getWidth());
        product.setHeight(newProduct.getDimension().getHeight());
        product.setDepth(newProduct.getDimension().getDepth());
        return product;
    }
}
