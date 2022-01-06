package com.bione.model.reportMyMicro.mygut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DiseasePrediction {

@SerializedName("disease")
@Expose
private String disease;
@SerializedName("image")
@Expose
private String image;
@SerializedName("risk")
@Expose
private String risk;

public String getDisease() {
return disease;
}

public void setDisease(String disease) {
this.disease = disease;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getRisk() {
return risk;
}

public void setRisk(String risk) {
this.risk = risk;
}

}