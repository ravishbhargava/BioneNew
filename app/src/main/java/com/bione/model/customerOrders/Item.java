package com.bione.model.customerOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Item implements Serializable {

@SerializedName("name")
@Expose
private String name;
@SerializedName("sku")
@Expose
private String sku;
@SerializedName("qty")
@Expose
private Integer qty;
@SerializedName("price")
@Expose
private String price;
@SerializedName("row_total")
@Expose
private String rowTotal;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getSku() {
return sku;
}

public void setSku(String sku) {
this.sku = sku;
}

public Integer getQty() {
return qty;
}

public void setQty(Integer qty) {
this.qty = qty;
}

public String getPrice() {
return price;
}

public void setPrice(String price) {
this.price = price;
}

public String getRowTotal() {
return rowTotal;
}

public void setRowTotal(String rowTotal) {
this.rowTotal = rowTotal;
}

}