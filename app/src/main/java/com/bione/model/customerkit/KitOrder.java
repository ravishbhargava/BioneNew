package com.bione.model.customerkit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KitOrder {

@SerializedName("customer_code")
@Expose
private String customerCode;
@SerializedName("id")
@Expose
private String id;
@SerializedName("bar_code")
@Expose
private String barCode;

@SerializedName("increment_id")
@Expose
private Object incrementId;
@SerializedName("kit_name")
@Expose
private String kitName;
@SerializedName("sku_code")
@Expose
private String skuCode;
@SerializedName("activation_status")
@Expose
private String activationStatus;
@SerializedName("report_url")
@Expose
private String reportUrl;

public String getCustomerCode() {
return customerCode;
}

public void setCustomerCode(String customerCode) {
this.customerCode = customerCode;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getBarCode() {
return barCode;
}

public void setBarCode(String barCode) {
this.barCode = barCode;
}

public Object getIncrementId() {
return incrementId;
}

public void setIncrementId(Object incrementId) {
this.incrementId = incrementId;
}

public String getKitName() {
return kitName;
}

public void setKitName(String kitName) {
this.kitName = kitName;
}

public String getSkuCode() {
return skuCode;
}

public void setSkuCode(String skuCode) {
this.skuCode = skuCode;
}

public String getActivationStatus() {
return activationStatus;
}

public void setActivationStatus(String activationStatus) {
this.activationStatus = activationStatus;
}

public String getReportUrl() {
return reportUrl;
}

public void setReportUrl(String reportUrl) {
this.reportUrl = reportUrl;
}

}