package com.bione.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BarCodeStatus {

@SerializedName("code")
@Expose
private Integer code;
@SerializedName("message")
@Expose
private String message;
@SerializedName("analysis")
@Expose
private String analysis;
@SerializedName("tips")
@Expose
private String tips;
@SerializedName("gutrestoration")
@Expose
private String gutrestoration;
@SerializedName("gutmaintenance")
@Expose
private String gutmaintenance;
@SerializedName("report_status")
@Expose
private String reportStatus;
@SerializedName("report_url")
@Expose
private String reportUrl;

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

public String getAnalysis() {
return analysis;
}

public void setAnalysis(String analysis) {
this.analysis = analysis;
}

public String getTips() {
return tips;
}

public void setTips(String tips) {
this.tips = tips;
}

public String getGutrestoration() {
return gutrestoration;
}

public void setGutrestoration(String gutrestoration) {
this.gutrestoration = gutrestoration;
}

public String getGutmaintenance() {
return gutmaintenance;
}

public void setGutmaintenance(String gutmaintenance) {
this.gutmaintenance = gutmaintenance;
}

public String getReportStatus() {
return reportStatus;
}

public void setReportStatus(String reportStatus) {
this.reportStatus = reportStatus;
}

public String getReportUrl() {
return reportUrl;
}

public void setReportUrl(String reportUrl) {
this.reportUrl = reportUrl;
}

}