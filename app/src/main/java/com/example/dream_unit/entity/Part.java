package com.example.dream_unit.entity;

import com.google.gson.annotations.SerializedName;

public class Part {
    @SerializedName("id")
    private int id;
    @SerializedName("brand")
    private Object brand;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private float price;
    @SerializedName("price_from")
    private float priceFrom;
    @SerializedName("price_to")
    private float priceTo;

    public Part() {}

    public Part(int id, String brand, String name, float price) {
        this.id=id;
        this.brand = brand;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public Object getBrand() {
        return brand;
    }

    public void setBrand(Object brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setPriceFrom(float priceFrom) {
        this.priceFrom = priceFrom;
    }

    public void setPriceTo(float priceTo) {
        this.priceTo = priceTo;
    }

    @Override
    public String toString() {
        return "Part{" +
                "brand=" + brand +
                ", name='" + name + '\'' +
                '}';
    }
}
