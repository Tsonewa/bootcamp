package com.example.productserver;

import com.example.productserver.entity.Product;
import com.example.productserver.repo.ProductRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitializationComponent {

    private final ProductRepository productRepository;

    public InitializationComponent(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void init(){

        productRepository.deleteAll();

        Product product;

        product = new Product();
        product.setName("Huawei");
        product.setCode("Huawei SP");
        product.setTitle("Lokia 12");
        product.setDescription("Description");
        product.setLocation("USA");
        product.setPrice(9000.00);
        productRepository.save(product);

        product = new Product();
        product.setName("Apple");
        product.setCode("IPhone 13");
        product.setTitle("Inovation 12");
        product.setDescription("Description");
        product.setLocation("USA");
        product.setPrice(10000.00);
        productRepository.save(product);

        product = new Product();
        product.setName("Samsung");
        product.setCode("Samsung SP");
        product.setTitle("Samsung 12");
        product.setDescription("Description");
        product.setLocation("USA");
        product.setPrice(8000.00);
        productRepository.save(product);

    }
}
