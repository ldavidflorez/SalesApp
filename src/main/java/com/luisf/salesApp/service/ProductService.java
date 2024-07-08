package com.luisf.salesApp.service;

import com.luisf.salesApp.model.Product;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {
    void save(Product product);

    Page<Product> getAll(int pageNo, int pageSize);

    Page<Product> getAllByStatus(boolean status, int pageNo, int pageSize);

    Page<Product> getAllByCategory(String category, int pageNo, int pageSize);

    Optional<Product> getById(Long id);

    Optional<Product> update(Long id, Product newProduct);

    boolean delete(Long id);
}
