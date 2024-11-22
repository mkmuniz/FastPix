package com.io.github.com.mkmuniz.flowmanager.application.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Product")
public class ProductEntity {
    @Id
    @GeneratedValue
    Integer id;
    String name;
    String description;
    String category;
    Integer sellerId;
    double returnRate;
}
