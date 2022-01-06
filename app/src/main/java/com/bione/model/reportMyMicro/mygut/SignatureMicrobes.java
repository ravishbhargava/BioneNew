package com.bione.model.reportMyMicro.mygut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SignatureMicrobes {

@SerializedName("microbes")
@Expose
private List<Microbe> microbes = null;

public List<Microbe> getMicrobes() {
return microbes;
}

public void setMicrobes(List<Microbe> microbes) {
this.microbes = microbes;
}

}