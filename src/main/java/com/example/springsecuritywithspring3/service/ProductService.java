package com.example.springsecuritywithspring3.service;

import com.example.springsecuritywithspring3.entity.Product;
import com.example.springsecuritywithspring3.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository = repository;
    }

    public Product updateProduct(Product product){
        Optional<Product> existingProduct = repository.findById(product.getId());
        if(existingProduct.isPresent()){
            existingProduct.get().setName(product.getName());
            existingProduct.get().setCode(product.getCode());
        }
        return repository.save(product);
    }
}
