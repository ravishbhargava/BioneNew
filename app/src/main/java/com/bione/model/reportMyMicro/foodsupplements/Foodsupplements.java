package com.bione.model.reportMyMicro.foodsupplements;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Foodsupplements {

@SerializedName("foodsupplement1")
@Expose
private String foodsupplement1;
@SerializedName("foodsupplement2")
@Expose
private String foodsupplement2;
@SerializedName("foodsupplement3")
@Expose
private String foodsupplement3;
@SerializedName("foodsupplement4")
@Expose
private String foodsupplement4;
@SerializedName("products")
@Expose
private List<Product> products = null;

public String getFoodsupplement1() {
return foodsupplement1;
}

public void setFoodsupplement1(String foodsupplement1) {
this.foodsupplement1 = foodsupplement1;
}

public String getFoodsupplement2() {
return foodsupplement2;
}

public void setFoodsupplement2(String foodsupplement2) {
this.foodsupplement2 = foodsupplement2;
}

public String getFoodsupplement3() {
return foodsupplement3;
}

public void setFoodsupplement3(String foodsupplement3) {
this.foodsupplement3 = foodsupplement3;
}

public String getFoodsupplement4() {
return foodsupplement4;
}

public void setFoodsupplement4(String foodsupplement4) {
this.foodsupplement4 = foodsupplement4;
}

public List<Product> getProducts() {
return products;
}

public void setProducts(List<Product> products) {
this.products = products;
}

}
