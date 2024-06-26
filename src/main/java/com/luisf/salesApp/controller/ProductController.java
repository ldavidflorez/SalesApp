package com.luisf.salesApp.controller;

import com.luisf.salesApp.model.Product;
import com.luisf.salesApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/state")
    public List<Product> getAllByStatus(@RequestParam boolean available) {
        return productService.getAllByStatus(available);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getById(@PathVariable Long id) {
        Optional<Product> product = productService.getById(id);

        if (product.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        productService.save(product);
        return new ResponseEntity<Product>(product,null, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Product>> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Optional<Product> updatedProduct = productService.update(id, productDetails);

        if (updatedProduct.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.delete(id);

        if (!isDeleted)
        {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
