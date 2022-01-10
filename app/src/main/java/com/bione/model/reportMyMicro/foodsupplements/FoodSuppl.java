package com.bione.model.reportMyMicro.foodsupplements;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;



public class FoodSuppl {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("foodsupplements")
    @Expose
    private List<Foodsupplements> foodsupplements = null;

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

    public List<Foodsupplements> getFoodsupplements() {
        return foodsupplements;
    }

    public void setFoodsupplements(List<Foodsupplements> foodsupplements) {
        this.foodsupplements = foodsupplements;
    }

}