package com.bione.model.reportMyMicro.frontpage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FrontPage {

@SerializedName("code")
@Expose
private Integer code;
@SerializedName("message")
@Expose
private String message;
@SerializedName("customer_details")
@Expose
private CustomerDetails customerDetails;

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

public CustomerDetails getCustomerDetails() {
return customerDetails;
}

public void setCustomerDetails(CustomerDetails customerDetails) {
this.customerDetails = customerDetails;
}

}