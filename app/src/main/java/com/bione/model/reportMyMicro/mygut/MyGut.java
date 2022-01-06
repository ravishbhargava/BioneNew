package com.bione.model.reportMyMicro.mygut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MyGut {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("MGMI_1")
    @Expose
    private Mgmi1 mgmi1;
    @SerializedName("bacterial_pathogen")
    @Expose
    private BacterialPathogen bacterialPathogen;
    @SerializedName("signature_microbes")
    @Expose
    private SignatureMicrobes signatureMicrobes;
    @SerializedName("disease_prediction")
    @Expose
    private List<DiseasePrediction> diseasePrediction = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Mgmi1 getMgmi1() {
        return mgmi1;
    }

    public void setMgmi1(Mgmi1 mgmi1) {
        this.mgmi1 = mgmi1;
    }

    public BacterialPathogen getBacterialPathogen() {
        return bacterialPathogen;
    }

    public void setBacterialPathogen(BacterialPathogen bacterialPathogen) {
        this.bacterialPathogen = bacterialPathogen;
    }

    public SignatureMicrobes getSignatureMicrobes() {
        return signatureMicrobes;
    }

    public void setSignatureMicrobes(SignatureMicrobes signatureMicrobes) {
        this.signatureMicrobes = signatureMicrobes;
    }

    public List<DiseasePrediction> getDiseasePrediction() {
        return diseasePrediction;
    }

    public void setDiseasePrediction(List<DiseasePrediction> diseasePrediction) {
        this.diseasePrediction = diseasePrediction;
    }

}