package com.example.springsecuritywithspring3.repository;

import com.example.springsecuritywithspring3.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByNameAndCode(String name, String code);
}
