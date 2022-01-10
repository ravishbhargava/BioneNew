package com.bione.model.reportMyMicro.mygut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Mgmi1 {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("report")
    @Expose
    private String report;
    @SerializedName("mgmi_score")
    @Expose
    private String mgmiScore;
    @SerializedName("mgmi_conclusion")
    @Expose
    private String mgmiConclusion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getMgmiScore() {
        return mgmiScore;
    }

    public void setMgmiScore(String mgmiScore) {
        this.mgmiScore = mgmiScore;
    }

    public String getMgmiConclusion() {
        return mgmiConclusion;
    }

    public void setMgmiConclusion(String mgmiConclusion) {
        this.mgmiConclusion = mgmiConclusion;
    }

}