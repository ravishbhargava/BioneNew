package com.bione.model.reportMyMicro.frontpage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CustomerDetails {

@SerializedName("patient_first_name")
@Expose
private String patientFirstName;
@SerializedName("patient_last_name")
@Expose
private String patientLastName;
@SerializedName("gender")
@Expose
private String gender;
@SerializedName("age")
@Expose
private String age;
@SerializedName("sample_registration_date")
@Expose
private String sampleRegistrationDate;
@SerializedName("releasedDate")
@Expose
private String releasedDate;

public String getPatientFirstName() {
return patientFirstName;
}

public void setPatientFirstName(String patientFirstName) {
this.patientFirstName = patientFirstName;
}

public String getPatientLastName() {
return patientLastName;
}

public void setPatientLastName(String patientLastName) {
this.patientLastName = patientLastName;
}

public String getGender() {
return gender;
}

public void setGender(String gender) {
this.gender = gender;
}

public String getAge() {
return age;
}

public void setAge(String age) {
this.age = age;
}

public String getSampleRegistrationDate() {
return sampleRegistrationDate;
}

public void setSampleRegistrationDate(String sampleRegistrationDate) {
this.sampleRegistrationDate = sampleRegistrationDate;
}

public String getReleasedDate() {
return releasedDate;
}

public void setReleasedDate(String releasedDate) {
this.releasedDate = releasedDate;
}

}