package com.luisf.salesApp.service;

import com.luisf.salesApp.model.Product;
import com.luisf.salesApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> update(Long id, Product newProduct) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(newProduct.getName());
            product.setRef(newProduct.getRef());
            product.setDescription(newProduct.getDescription());
            product.setBasePrice(newProduct.getBasePrice());
            product.setPerDiscount(newProduct.getPerDiscount());

            return Optional.of(productRepository.save(product));
        }

        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        if (productRepository.existsById(id)){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
