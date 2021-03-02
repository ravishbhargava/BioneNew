package com.bione.model.testNameList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestNameList {

@SerializedName("code")
@Expose
private Integer code;
@SerializedName("message")
@Expose
private String message;
@SerializedName("product")
@Expose
private List<Product> product = null;

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

public List<Product> getProduct() {
return product;
}

public void setProduct(List<Product> product) {
this.product = product;
}

}