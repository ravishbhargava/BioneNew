package com.bione.model.salesdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("geneticdata_id")
@Expose
private String geneticdataId;
@SerializedName("sales_zone")
@Expose
private String salesZone;
@SerializedName("reporting_manager")
@Expose
private String reportingManager;
@SerializedName("reporting_manager_email_id")
@Expose
private String reportingManagerEmailId;
@SerializedName("sales_person_name")
@Expose
private String salesPersonName;
@SerializedName("mobile_number")
@Expose
private String mobileNumber;
@SerializedName("email_id")
@Expose
private String emailId;
@SerializedName("city")
@Expose
private String city;
@SerializedName("state")
@Expose
private String state;

public String getGeneticdataId() {
return geneticdataId;
}

public void setGeneticdataId(String geneticdataId) {
this.geneticdataId = geneticdataId;
}

public String getSalesZone() {
return salesZone;
}

public void setSalesZone(String salesZone) {
this.salesZone = salesZone;
}

public String getReportingManager() {
return reportingManager;
}

public void setReportingManager(String reportingManager) {
this.reportingManager = reportingManager;
}

public String getReportingManagerEmailId() {
return reportingManagerEmailId;
}

public void setReportingManagerEmailId(String reportingManagerEmailId) {
this.reportingManagerEmailId = reportingManagerEmailId;
}

public String getSalesPersonName() {
return salesPersonName;
}

public void setSalesPersonName(String salesPersonName) {
this.salesPersonName = salesPersonName;
}

public String getMobileNumber() {
return mobileNumber;
}

public void setMobileNumber(String mobileNumber) {
this.mobileNumber = mobileNumber;
}

public String getEmailId() {
return emailId;
}

public void setEmailId(String emailId) {
this.emailId = emailId;
}

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

public String getState() {
return state;
}

public void setState(String state) {
this.state = state;
}

}