package com.bione.model.customerOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class MagentoOrder implements Serializable {

    @SerializedName("increment_id")
    @Expose
    private String incrementId;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;
    @SerializedName("customer_firstname")
    @Expose
    private String customerFirstname;
    @SerializedName("customer_middlename")
    @Expose
    private Object customerMiddlename;
    @SerializedName("customer_lastname")
    @Expose
    private String customerLastname;
    @SerializedName("total_item_count")
    @Expose
    private String totalItemCount;
    @SerializedName("base_grand_total")
    @Expose
    private String baseGrandTotal;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("shipping_address")
    @Expose
    private List<ShippingAddress> shippingAddress = null;
    @SerializedName("billing_address")
    @Expose
    private List<BillingAddress> billingAddress = null;

    public String getIncrementId() {
        return incrementId;
    }

    public void setIncrementId(String incrementId) {
        this.incrementId = incrementId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerFirstname() {
        return customerFirstname;
    }

    public void setCustomerFirstname(String customerFirstname) {
        this.customerFirstname = customerFirstname;
    }

    public Object getCustomerMiddlename() {
        return customerMiddlename;
    }

    public void setCustomerMiddlename(Object customerMiddlename) {
        this.customerMiddlename = customerMiddlename;
    }

    public String getCustomerLastname() {
        return customerLastname;
    }

    public void setCustomerLastname(String customerLastname) {
        this.customerLastname = customerLastname;
    }

    public String getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(String totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    public String getBaseGrandTotal() {
        return baseGrandTotal;
    }

    public void setBaseGrandTotal(String baseGrandTotal) {
        this.baseGrandTotal = baseGrandTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<ShippingAddress> getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(List<ShippingAddress> shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<BillingAddress> getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(List<BillingAddress> billingAddress) {
        this.billingAddress = billingAddress;
    }

}