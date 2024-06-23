package com.luisf.salesApp.service;

import com.luisf.salesApp.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product save(Product product);

    List<Product> getAll();

    Optional<Product> getById(Long id);

    Optional<Product> update(Long id, Product newProduct);

    void delete(Long id);
}
