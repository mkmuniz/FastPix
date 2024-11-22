package com.io.github.com.mkmuniz.flowmanager.core.ports.out;

import java.util.List;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Product;

public interface ProductRepositoryPort {
    List<Product> findAll();
    Product findById(Integer id);
    Product update(Product product);
    Product delete(Product product);
    Product save(Product product);
}
