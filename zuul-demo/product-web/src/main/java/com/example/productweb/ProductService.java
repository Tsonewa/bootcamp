package com.example.productweb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ProductService {

    @GetMapping("/products")
     public List<Product> getAllProducts();

     @GetMapping("/product/{id}")
     public Product getProduct(@PathVariable("id") String id);
}
