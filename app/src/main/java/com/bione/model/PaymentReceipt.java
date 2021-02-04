package com.bione.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentReceipt {

@SerializedName("code")
@Expose
private Integer code;
@SerializedName("message")
@Expose
private String message;
@SerializedName("receipt_url")
@Expose
private String receiptUrl;

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

public String getReceiptUrl() {
return receiptUrl;
}

public void setReceiptUrl(String receiptUrl) {
this.receiptUrl = receiptUrl;
}

}