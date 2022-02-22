package com.example.productweb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class ProductRestController implements ProductService {


    private final RestTemplate restTemplate;
    private static final String SERVICE_URL = "http://api-gateway/api/products";

    public ProductRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        Product[] products = restTemplate.getForObject(SERVICE_URL, Product[].class);

        return Arrays.asList(products);
    }

    @Override
    @GetMapping("/products/{id}")
    public Product getProduct(String id) {

        return restTemplate.getForObject(SERVICE_URL, Product.class);
    }
}
