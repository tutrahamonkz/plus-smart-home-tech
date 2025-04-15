package ru.yandex.practicum.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.*;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Override
    public void addProduct(NewProductInWarehouseRequest product) {

    }

    @Override
    public BookedProductsDto check(ShoppingCartDto shoppingCartDto) {
        return null;
    }

    @Override
    public void addProductQuantity(AddProductToWarehouseRequest productQuantity) {

    }

    @Override
    public AddressDto getAddress() {
        return null;
    }
}
