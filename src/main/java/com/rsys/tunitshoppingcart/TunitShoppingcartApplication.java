package com.rsys.tunitshoppingcart;

import com.rsys.tunitshoppingcart.entity.Product;
import com.rsys.tunitshoppingcart.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class TunitShoppingcartApplication {

    private final ProductRepository productRepository;

    @Autowired
    public TunitShoppingcartApplication(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TunitShoppingcartApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            log.info("ApplicationRunner");
            List<Product> products = new ArrayList<>();
            products.add(Product.builder().name("Pen").description("Pen").price(10.00).build());
            products.add(Product.builder().name("Pencil").description("Pencil").price(5.00).build());
            products.add(Product.builder().name("Eraser").description("Eraser").price(2.00).build());
            products.add(Product.builder().name("Sharpener").description("Sharpener").price(3.00).build());
            products.add(Product.builder().name("Notebook").description("Notebook").price(20.00).build());
            products.add(Product.builder().name("Stapler").description("Stapler").price(15.00).build());


            productRepository.saveAll(products);
        };
    }

}
