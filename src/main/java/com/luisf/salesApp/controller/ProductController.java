package com.luisf.salesApp.controller;

import com.luisf.salesApp.model.Product;
import com.luisf.salesApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public Page<Product> getAll(@RequestParam(defaultValue = "0") int pageNo,
                                @RequestParam(defaultValue = "10") int pageSize) {
        return productService.getAll(pageNo, pageSize);
    }

    @GetMapping("/state")
    public Page<Product> getAllByStatus(@RequestParam boolean available,
                                        @RequestParam(defaultValue = "0") int pageNo,
                                        @RequestParam(defaultValue = "10") int pageSize) {
        return productService.getAllByStatus(available, pageNo, pageSize);
    }

    @GetMapping("/category")
    public Page<Product> getAllByCategory(@RequestParam String category,
                                        @RequestParam(defaultValue = "0") int pageNo,
                                        @RequestParam(defaultValue = "10") int pageSize) {
        return productService.getAllByCategory(category, pageNo, pageSize);
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
