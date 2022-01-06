package com.bione.model.reportMyMicro.mygut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Mgmi1 {

@SerializedName("firmicutes")
@Expose
private Firmicutes firmicutes;
@SerializedName("MGMI_score")
@Expose
private String mGMIScore;
@SerializedName("mgmi_conclusion")
@Expose
private String mgmiConclusion;

public Firmicutes getFirmicutes() {
return firmicutes;
}

public void setFirmicutes(Firmicutes firmicutes) {
this.firmicutes = firmicutes;
}

public String getMGMIScore() {
return mGMIScore;
}

public void setMGMIScore(String mGMIScore) {
this.mGMIScore = mGMIScore;
}

public String getMgmiConclusion() {
return mgmiConclusion;
}

public void setMgmiConclusion(String mgmiConclusion) {
this.mgmiConclusion = mgmiConclusion;
}

}