package com.io.github.com.mkmuniz.flowmanager.application;

import org.springframework.web.bind.annotation.RestController;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Product;
import com.io.github.com.mkmuniz.flowmanager.core.ports.in.ProductServicePort;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductServicePort productServicePort;

    @GetMapping
    public List<Product> getMethodName() {
        return productServicePort.findAll();
    }

    @PostMapping
    public Product postMethodName(@RequestBody Product entity) {
        return productServicePort.save(entity);
    }
    
}
