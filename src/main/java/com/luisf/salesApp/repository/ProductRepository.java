package com.luisf.salesApp.repository;

import com.luisf.salesApp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.available = :status")
    Page<Product> getAllByStatus(boolean status, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category = :category")
    Page<Product> getAllByCategory(String category, Pageable pageable);
}
