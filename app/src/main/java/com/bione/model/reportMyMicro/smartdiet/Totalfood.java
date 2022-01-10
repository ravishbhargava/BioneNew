package com.bione.model.reportMyMicro.smartdiet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Totalfood {

@SerializedName("total_super_food")
@Expose
private Integer totalSuperFood;
@SerializedName("total_good_food")
@Expose
private Integer totalGoodFood;
@SerializedName("total_minimize_food")
@Expose
private Integer totalMinimizeFood;
@SerializedName("total_avoid_food")
@Expose
private Integer totalAvoidFood;

public Integer getTotalSuperFood() {
return totalSuperFood;
}

public void setTotalSuperFood(Integer totalSuperFood) {
this.totalSuperFood = totalSuperFood;
}

public Integer getTotalGoodFood() {
return totalGoodFood;
}

public void setTotalGoodFood(Integer totalGoodFood) {
this.totalGoodFood = totalGoodFood;
}

public Integer getTotalMinimizeFood() {
return totalMinimizeFood;
}

public void setTotalMinimizeFood(Integer totalMinimizeFood) {
this.totalMinimizeFood = totalMinimizeFood;
}

public Integer getTotalAvoidFood() {
return totalAvoidFood;
}

public void setTotalAvoidFood(Integer totalAvoidFood) {
this.totalAvoidFood = totalAvoidFood;
}

}