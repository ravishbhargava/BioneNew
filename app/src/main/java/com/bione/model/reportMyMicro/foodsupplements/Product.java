package com.bione.model.reportMyMicro.foodsupplements;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Product {

@SerializedName("image")
@Expose
private String image;
@SerializedName("name")
@Expose
private String name;
@SerializedName("servings")
@Expose
private String servings;
@SerializedName("note")
@Expose
private String note;

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getServings() {
return servings;
}

public void setServings(String servings) {
this.servings = servings;
}

public String getNote() {
return note;
}

public void setNote(String note) {
this.note = note;
}

}