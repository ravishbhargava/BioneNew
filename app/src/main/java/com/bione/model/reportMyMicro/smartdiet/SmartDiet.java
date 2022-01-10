package com.bione.model.reportMyMicro.smartdiet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SmartDiet {

@SerializedName("code")
@Expose
private Integer code;
@SerializedName("message")
@Expose
private String message;
@SerializedName("Food_Recommendation")
@Expose
private List<FoodRecommendation> foodRecommendation = null;
@SerializedName("foodcount")
@Expose
private Foodcount foodcount;
@SerializedName("totalfood")
@Expose
private Totalfood totalfood;

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

public List<FoodRecommendation> getFoodRecommendation() {
return foodRecommendation;
}

public void setFoodRecommendation(List<FoodRecommendation> foodRecommendation) {
this.foodRecommendation = foodRecommendation;
}

public Foodcount getFoodcount() {
return foodcount;
}

public void setFoodcount(Foodcount foodcount) {
this.foodcount = foodcount;
}

public Totalfood getTotalfood() {
return totalfood;
}

public void setTotalfood(Totalfood totalfood) {
this.totalfood = totalfood;
}

}