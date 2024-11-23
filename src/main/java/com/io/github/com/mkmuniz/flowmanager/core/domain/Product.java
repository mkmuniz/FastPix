package com.io.github.com.mkmuniz.flowmanager.core.domain;

public class Product {
    private Integer id;
    private String name;
    private String description;
    private String category;
    private Integer sellerId;
    private double returnRate;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public double getReturnRate() {
        return returnRate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public void setReturnRate(double returnRate) {
        this.returnRate = returnRate;
    }
}
