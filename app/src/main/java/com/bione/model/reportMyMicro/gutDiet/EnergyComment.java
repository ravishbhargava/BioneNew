package com.bione.model.reportMyMicro.gutDiet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnergyComment {

@SerializedName("meal_time")
@Expose
private String mealTime;
@SerializedName("energy")
@Expose
private String energy;
@SerializedName("comments")
@Expose
private String comments;

public String getMealTime() {
return mealTime;
}

public void setMealTime(String mealTime) {
this.mealTime = mealTime;
}

public String getEnergy() {
return energy;
}

public void setEnergy(String energy) {
this.energy = energy;
}

public String getComments() {
return comments;
}

public void setComments(String comments) {
this.comments = comments;
}

}
