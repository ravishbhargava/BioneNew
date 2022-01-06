package com.bione.model.reportMyMicro.mygut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Firmicutes {

@SerializedName("value")
@Expose
private String value;
@SerializedName("report")
@Expose
private String report;

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

}