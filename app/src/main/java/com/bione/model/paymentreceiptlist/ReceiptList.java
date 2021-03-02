package com.bione.model.paymentreceiptlist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReceiptList {

@SerializedName("code")
@Expose
private Integer code;
@SerializedName("message")
@Expose
private String message;
@SerializedName("receipt")
@Expose
private List<Receipt> receipt = null;

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

public List<Receipt> getReceipt() {
return receipt;
}

public void setReceipt(List<Receipt> receipt) {
this.receipt = receipt;
}

}