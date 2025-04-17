package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.mapper.WarehouseMapper;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.Warehouse;
import ru.yandex.practicum.model.WarehouseProduct;
import ru.yandex.practicum.repository.AddressRepository;
import ru.yandex.practicum.repository.WarehouseProductRepository;
import ru.yandex.practicum.repository.WarehouseRepository;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private static final String[] ADDRESSES =
            new String[] {"ADDRESS_1", "ADDRESS_2"};

    private static final String CURRENT_ADDRESS =
            ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];

    private final WarehouseRepository warehouseRepository;
    private final WarehouseProductRepository warehouseProductRepository;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public void addProduct(NewProductInWarehouseRequest product) {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        Warehouse warehouse;
        if (warehouses.isEmpty()) {
            warehouse = new Warehouse();
            Address address = addressRepository.save(getAddressRandom());
            warehouse.setAddress(address);
            warehouse.setProducts(new ArrayList<>());
            warehouseRepository.save(warehouse);
        } else {
            warehouse = warehouses.getFirst();
        }

        List<WarehouseProduct> products = warehouse.getProducts();
        WarehouseProduct warehouseProduct = WarehouseMapper.toWarehouseProduct(product);
        warehouseProduct.setQuantity(0);
        WarehouseProduct savedProduct = warehouseProductRepository.save(warehouseProduct);
        products.add(savedProduct);
        warehouseRepository.save(warehouse);
    }

    @Override
    public BookedProductsDto check(ShoppingCartDto shoppingCartDto) {
        Warehouse warehouse = warehouseRepository.findAll().getFirst();
        List<WarehouseProduct> products = warehouse.getProducts();
        Map<String, Integer> productMap = shoppingCartDto.getProducts();
        List<WarehouseProduct> productsHave = new ArrayList<>();
        List<WarehouseProduct> productsNotHave = new ArrayList<>();
        productMap.keySet().forEach(key -> {
            Integer count = productMap.get(key);
            WarehouseProduct extendsProduct = products.stream()
                    .filter(product -> product.getId().equals(key))
                    .findFirst()
                    .orElseThrow();
            if (extendsProduct.getQuantity() >= count) {
                productsHave.add(extendsProduct);
            } else {
                productsNotHave.add(extendsProduct);
            }
        });
        if (!productsNotHave.isEmpty()) {
            String response = productsNotHave.stream().map(WarehouseProduct::getId).toList().toString();
            throw new ProductInShoppingCartLowQuantityInWarehouse(response);
        }
        boolean fragile = false;
        Double deliveryWeight = null;
        Double deliveryVolume = null;
        for (WarehouseProduct product : productsHave) {
            if (!fragile && product.getFragile()) {
                fragile = true;
            }
            deliveryWeight += product.getWeight();
            deliveryVolume += product.getWidth() * product.getHeight() * product.getDepth();
        }
        return BookedProductsDto.builder()
                .fragile(fragile)
                .deliveryWeight(deliveryWeight)
                .deliveryVolume(deliveryVolume)
                .build();
    }

    @Override
    @Transactional
    public void addProductQuantity(AddProductToWarehouseRequest productQuantity) {
        Warehouse warehouse = warehouseRepository.findAll().getFirst();
        List<WarehouseProduct> products = warehouse.getProducts();
        WarehouseProduct warehouseProduct = products.stream()
                .filter(o -> o.getId().equals(productQuantity.getProductId()))
                .findFirst()
                .orElseThrow();
        warehouseProduct.setQuantity(warehouseProduct.getQuantity() + productQuantity.getQuantity());
        warehouseRepository.save(warehouse);
    }

    @Override
    public AddressDto getAddress() {
        return WarehouseMapper.toAddressDto(warehouseRepository.findAll().getFirst());
    }

    private Address getAddressRandom() {
        String randomAddress = CURRENT_ADDRESS;
        Address address = new Address();
        address.setCountry(randomAddress);
        address.setCity(randomAddress);
        address.setStreet(randomAddress);
        address.setHouse(randomAddress);
        address.setFlat(randomAddress);
        return address;
    }
}
