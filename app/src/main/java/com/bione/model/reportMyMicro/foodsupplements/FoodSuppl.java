package com.bione.model.reportMyMicro.foodsupplements;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FoodSuppl {

@SerializedName("code")
@Expose
private Integer code;
@SerializedName("message")
@Expose
private String message;
@SerializedName("foodsupplements")
@Expose
private Foodsupplements foodsupplements;

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

public Foodsupplements getFoodsupplements() {
return foodsupplements;
}

public void setFoodsupplements(Foodsupplements foodsupplements) {
this.foodsupplements = foodsupplements;
}

}