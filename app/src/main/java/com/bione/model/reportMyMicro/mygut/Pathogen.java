package com.bione.model.reportMyMicro.mygut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Pathogen {

@SerializedName("pathogen")
@Expose
private String pathogen;
@SerializedName("ab_percentage")
@Expose
private String abPercentage;
@SerializedName("outcome")
@Expose
private String outcome;

public String getPathogen() {
return pathogen;
}

public void setPathogen(String pathogen) {
this.pathogen = pathogen;
}

public String getAbPercentage() {
return abPercentage;
}

public void setAbPercentage(String abPercentage) {
this.abPercentage = abPercentage;
}

public String getOutcome() {
return outcome;
}

public void setOutcome(String outcome) {
this.outcome = outcome;
}

}