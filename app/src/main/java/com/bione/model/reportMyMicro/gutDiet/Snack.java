package com.bione.model.reportMyMicro.gutDiet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Snack {

@SerializedName("mealname")
@Expose
private String mealname;
@SerializedName("recipename")
@Expose
private String recipename;
@SerializedName("preparation")
@Expose
private String preparation;
@SerializedName("alternatemenu")
@Expose
private String alternatemenu;

public String getMealname() {
return mealname;
}

public void setMealname(String mealname) {
this.mealname = mealname;
}

public String getRecipename() {
return recipename;
}

public void setRecipename(String recipename) {
this.recipename = recipename;
}

public String getPreparation() {
return preparation;
}

public void setPreparation(String preparation) {
this.preparation = preparation;
}

public String getAlternatemenu() {
return alternatemenu;
}

public void setAlternatemenu(String alternatemenu) {
this.alternatemenu = alternatemenu;
}

}