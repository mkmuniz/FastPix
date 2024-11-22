package com.io.github.com.mkmuniz.flowmanager.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.io.github.com.mkmuniz.flowmanager.application.entities.ProductEntity;

@Service
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    
}
