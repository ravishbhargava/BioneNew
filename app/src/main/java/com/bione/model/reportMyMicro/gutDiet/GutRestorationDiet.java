package com.bione.model.reportMyMicro.gutDiet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GutRestorationDiet {

@SerializedName("breakfast")
@Expose
private List<Breakfast> breakfast = null;
@SerializedName("lunch")
@Expose
private List<Lunch> lunch = null;
@SerializedName("snack")
@Expose
private List<Snack> snack = null;
@SerializedName("dinner")
@Expose
private List<Dinner> dinner = null;
@SerializedName("bedtime")
@Expose
private List<Bedtime> bedtime = null;
@SerializedName("energy_comments")
@Expose
private List<EnergyComment> energyComments = null;

public List<Breakfast> getBreakfast() {
return breakfast;
}

public void setBreakfast(List<Breakfast> breakfast) {
this.breakfast = breakfast;
}

public List<Lunch> getLunch() {
return lunch;
}

public void setLunch(List<Lunch> lunch) {
this.lunch = lunch;
}

public List<Snack> getSnack() {
return snack;
}

public void setSnack(List<Snack> snack) {
this.snack = snack;
}

public List<Dinner> getDinner() {
return dinner;
}

public void setDinner(List<Dinner> dinner) {
this.dinner = dinner;
}

public List<Bedtime> getBedtime() {
return bedtime;
}

public void setBedtime(List<Bedtime> bedtime) {
this.bedtime = bedtime;
}

public List<EnergyComment> getEnergyComments() {
return energyComments;
}

public void setEnergyComments(List<EnergyComment> energyComments) {
this.energyComments = energyComments;
}

}