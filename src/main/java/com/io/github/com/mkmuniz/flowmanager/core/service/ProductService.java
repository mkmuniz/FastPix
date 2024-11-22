package com.io.github.com.mkmuniz.flowmanager.core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Product;
import com.io.github.com.mkmuniz.flowmanager.core.ports.in.ProductServicePort;
import com.io.github.com.mkmuniz.flowmanager.core.ports.out.ProductRepositoryPort;

@Service
public class ProductService implements ProductServicePort {

    private final ProductRepositoryPort productRepositoryPort;

    public ProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    };

    @Override
    public List<Product> findAll() {
       return productRepositoryPort.findAll();
    }   

    @Override
    public Product findById(Integer id) {
        return productRepositoryPort.findById(id);
    }

    @Override
    public Product update(Product product) {
        return productRepositoryPort.update(product);
    }

    @Override
    public Product delete(Product product) {
        return productRepositoryPort.delete(product);
    }

    @Override
    public Product save(Product product) {
        return productRepositoryPort.save(product);
    }
}
