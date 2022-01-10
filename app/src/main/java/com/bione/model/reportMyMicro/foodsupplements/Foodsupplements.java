package com.bione.model.reportMyMicro.foodsupplements;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Foodsupplements {

    @SerializedName("foodsupplement")
    @Expose
    private String foodsupplement;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    public String getFoodsupplement() {
        return foodsupplement;
    }

    public void setFoodsupplement(String foodsupplement) {
        this.foodsupplement = foodsupplement;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}