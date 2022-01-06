package com.bione.model.reportMyMicro.mygut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BacterialPathogen {

@SerializedName("pathogen")
@Expose
private List<Pathogen> pathogen = null;
@SerializedName("path_conclusion")
@Expose
private String pathConclusion;

public List<Pathogen> getPathogen() {
return pathogen;
}

public void setPathogen(List<Pathogen> pathogen) {
this.pathogen = pathogen;
}

public String getPathConclusion() {
return pathConclusion;
}

public void setPathConclusion(String pathConclusion) {
this.pathConclusion = pathConclusion;
}

}