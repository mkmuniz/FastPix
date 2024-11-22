package com.io.github.com.mkmuniz.flowmanager.application.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.io.github.com.mkmuniz.flowmanager.application.entities.ProductEntity;
import com.io.github.com.mkmuniz.flowmanager.core.domain.Product;
import com.io.github.com.mkmuniz.flowmanager.core.ports.out.ProductRepositoryPort;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    @Override
    public Product save(Product product) {
        ProductEntity entity = modelMapper.map(product, ProductEntity.class);

        ProductEntity save = productRepository.save(entity);

        return modelMapper.map(save, Product.class);
    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> all = productRepository.findAll();

        return all.stream()
                .map( productEntity -> modelMapper.map(productEntity, Product.class))
                .collect(Collectors.toList());
    }

    @Override
    public Product findById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Product update(Product product) {
        ProductEntity entity = modelMapper.map(product, ProductEntity.class);

        ProductEntity save = productRepository.save(entity);

        return modelMapper.map(save, Product.class);
    }

    @Override
    public Product delete(Product product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
