package com.example.springsecuritywithspring3.controller;

import com.example.springsecuritywithspring3.entity.Product;
import com.example.springsecuritywithspring3.repository.ProductRepository;
import com.example.springsecuritywithspring3.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository repository;
    private final ProductService service;

    public ProductController(ProductRepository repository, ProductService service){
        this.repository = repository;
        this.service = service;
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        try {
            return new ResponseEntity<>(repository.save(product), HttpStatus.CREATED);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/addProducts")
    public ResponseEntity<List<Product>> addProducts(@RequestBody List<Product> products){
        try {
            return new ResponseEntity<>(repository.saveAll(products), HttpStatus.CREATED);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> productList = new ArrayList<>(repository.findAll());

        if(productList.isEmpty()){
            return new ResponseEntity<>(productList, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id){
        Optional<Product> product = repository.findById(id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/getProduct")
    public ResponseEntity<Product> getProductByName(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "code") String code){
        Optional<Product> product = repository.findByNameAndCode(name, code);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        try {
            Product updatedProduct = service.updateProduct(product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.CREATED);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id){
        try {
            repository.deleteById(id);
            return new ResponseEntity<>("Product Deleted", HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
