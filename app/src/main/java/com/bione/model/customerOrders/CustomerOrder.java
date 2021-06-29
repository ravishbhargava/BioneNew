package com.bione.model.customerOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class CustomerOrder implements Serializable {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("kit_orders")
    @Expose
    private List<KitOrder> kitOrders = null;
    @SerializedName("magento_orders")
    @Expose
    private List<MagentoOrder> magentoOrders = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<KitOrder> getKitOrders() {
        return kitOrders;
    }

    public void setKitOrders(List<KitOrder> kitOrders) {
        this.kitOrders = kitOrders;
    }

    public List<MagentoOrder> getMagentoOrders() {
        return magentoOrders;
    }

    public void setMagentoOrders(List<MagentoOrder> magentoOrders) {
        this.magentoOrders = magentoOrders;
    }

}