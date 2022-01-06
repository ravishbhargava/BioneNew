package com.bione.model.reportMyMicro.mygut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Microbe {

@SerializedName("domain")
@Expose
private String domain;
@SerializedName("taxa")
@Expose
private String taxa;
@SerializedName("classification")
@Expose
private String classification;
@SerializedName("ab_percentage")
@Expose
private String abPercentage;
@SerializedName("status")
@Expose
private String status;
@SerializedName("outcome")
@Expose
private String outcome;

public String getDomain() {
return domain;
}

public void setDomain(String domain) {
this.domain = domain;
}

public String getTaxa() {
return taxa;
}

public void setTaxa(String taxa) {
this.taxa = taxa;
}

public String getClassification() {
return classification;
}

public void setClassification(String classification) {
this.classification = classification;
}

public String getAbPercentage() {
return abPercentage;
}

public void setAbPercentage(String abPercentage) {
this.abPercentage = abPercentage;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getOutcome() {
return outcome;
}

public void setOutcome(String outcome) {
this.outcome = outcome;
}

}