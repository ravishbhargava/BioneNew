package com.bione.model.testNameList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

@SerializedName("id")
@Expose
private String id;
@SerializedName("test_code")
@Expose
private String testCode;
@SerializedName("test_name")
@Expose
private String testName;
@SerializedName("test_description")
@Expose
private String testDescription;
@SerializedName("method")
@Expose
private String method;
@SerializedName("sample_type")
@Expose
private String sampleType;
@SerializedName("sample_volume")
@Expose
private String sampleVolume;
@SerializedName("tat")
@Expose
private String tat;
@SerializedName("mrp")
@Expose
private String mrp;
@SerializedName("discounted_price")
@Expose
private String discountedPrice;
@SerializedName("aprroval_condition_less")
@Expose
private String aprrovalConditionLess;
@SerializedName("aprroval_condition_ess_and_greter")
@Expose
private String aprrovalConditionEssAndGreter;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getTestCode() {
return testCode;
}

public void setTestCode(String testCode) {
this.testCode = testCode;
}

public String getTestName() {
return testName;
}

public void setTestName(String testName) {
this.testName = testName;
}

public String getTestDescription() {
return testDescription;
}

public void setTestDescription(String testDescription) {
this.testDescription = testDescription;
}

public String getMethod() {
return method;
}

public void setMethod(String method) {
this.method = method;
}

public String getSampleType() {
return sampleType;
}

public void setSampleType(String sampleType) {
this.sampleType = sampleType;
}

public String getSampleVolume() {
return sampleVolume;
}

public void setSampleVolume(String sampleVolume) {
this.sampleVolume = sampleVolume;
}

public String getTat() {
return tat;
}

public void setTat(String tat) {
this.tat = tat;
}

public String getMrp() {
return mrp;
}

public void setMrp(String mrp) {
this.mrp = mrp;
}

public String getDiscountedPrice() {
return discountedPrice;
}

public void setDiscountedPrice(String discountedPrice) {
this.discountedPrice = discountedPrice;
}

public String getAprrovalConditionLess() {
return aprrovalConditionLess;
}

public void setAprrovalConditionLess(String aprrovalConditionLess) {
this.aprrovalConditionLess = aprrovalConditionLess;
}

public String getAprrovalConditionEssAndGreter() {
return aprrovalConditionEssAndGreter;
}

public void setAprrovalConditionEssAndGreter(String aprrovalConditionEssAndGreter) {
this.aprrovalConditionEssAndGreter = aprrovalConditionEssAndGreter;
}

}