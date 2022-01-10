package com.bione.model.reportMyMicro.smartdiet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FoodRecommendation {

@SerializedName("Index")
@Expose
private Integer index;
@SerializedName("MM2019231362_Food_Type")
@Expose
private String mM2019231362FoodType;
@SerializedName("MM2019231362_Food_Items")
@Expose
private String mM2019231362FoodItems;
@SerializedName("MM2019231362_Food_Items_Hindi")
@Expose
private String mM2019231362FoodItemsHindi;
@SerializedName("MM2019231362_Food_Category")
@Expose
private String mM2019231362FoodCategory;
@SerializedName("MM2019231362_Food_Sub_Category")
@Expose
private String mM2019231362FoodSubCategory;
@SerializedName("MM2019231362_Food_Servings")
@Expose
private String mM2019231362FoodServings;
@SerializedName("MM2019231362_GR")
@Expose
private String mm2019231362Gr;
@SerializedName("MM2019231362_GM")
@Expose
private String mm2019231362Gm;
@SerializedName("image")
@Expose
private String image;

public Integer getIndex() {
return index;
}

public void setIndex(Integer index) {
this.index = index;
}

public String getMM2019231362FoodType() {
return mM2019231362FoodType;
}

public void setMM2019231362FoodType(String mM2019231362FoodType) {
this.mM2019231362FoodType = mM2019231362FoodType;
}

public String getMM2019231362FoodItems() {
return mM2019231362FoodItems;
}

public void setMM2019231362FoodItems(String mM2019231362FoodItems) {
this.mM2019231362FoodItems = mM2019231362FoodItems;
}

public String getMM2019231362FoodItemsHindi() {
return mM2019231362FoodItemsHindi;
}

public void setMM2019231362FoodItemsHindi(String mM2019231362FoodItemsHindi) {
this.mM2019231362FoodItemsHindi = mM2019231362FoodItemsHindi;
}

public String getMM2019231362FoodCategory() {
return mM2019231362FoodCategory;
}

public void setMM2019231362FoodCategory(String mM2019231362FoodCategory) {
this.mM2019231362FoodCategory = mM2019231362FoodCategory;
}

public String getMM2019231362FoodSubCategory() {
return mM2019231362FoodSubCategory;
}

public void setMM2019231362FoodSubCategory(String mM2019231362FoodSubCategory) {
this.mM2019231362FoodSubCategory = mM2019231362FoodSubCategory;
}

public String getMM2019231362FoodServings() {
return mM2019231362FoodServings;
}

public void setMM2019231362FoodServings(String mM2019231362FoodServings) {
this.mM2019231362FoodServings = mM2019231362FoodServings;
}

public String getMm2019231362Gr() {
return mm2019231362Gr;
}

public void setMm2019231362Gr(String mm2019231362Gr) {
this.mm2019231362Gr = mm2019231362Gr;
}

public String getMm2019231362Gm() {
return mm2019231362Gm;
}

public void setMm2019231362Gm(String mm2019231362Gm) {
this.mm2019231362Gm = mm2019231362Gm;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

}