package com.luisf.salesApp.service;

import com.luisf.salesApp.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    void save(Product product);

    List<Product> getAll();

    List<Product> getAllByStatus(boolean status);

    Optional<Product> getById(Long id);

    Optional<Product> update(Long id, Product newProduct);

    boolean delete(Long id);
}
