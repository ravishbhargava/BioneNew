package com.bione.model.customerOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class KitOrder implements Serializable {

@SerializedName("bioneorders_id")
@Expose
private String bioneordersId;
@SerializedName("magento_order_id")
@Expose
private Object magentoOrderId;
@SerializedName("customer_id")
@Expose
private String customerId;
@SerializedName("corporate_customer_id")
@Expose
private Object corporateCustomerId;
@SerializedName("bar_code")
@Expose
private String barCode;
@SerializedName("blood_bar_code")
@Expose
private String bloodBarCode;
@SerializedName("sku_code")
@Expose
private String skuCode;
@SerializedName("kit_name")
@Expose
private String kitName;
@SerializedName("activation_status")
@Expose
private String activationStatus;
@SerializedName("invite_status")
@Expose
private Object inviteStatus;
@SerializedName("first_name")
@Expose
private String firstName;
@SerializedName("last_name")
@Expose
private String lastName;
@SerializedName("phone_number")
@Expose
private String phoneNumber;
@SerializedName("email_id")
@Expose
private String emailId;
@SerializedName("relationship")
@Expose
private String relationship;
@SerializedName("gender")
@Expose
private String gender;
@SerializedName("date_of_birth")
@Expose
private String dateOfBirth;
@SerializedName("terms_and_condition")
@Expose
private String termsAndCondition;
@SerializedName("accept_consent")
@Expose
private String acceptConsent;
@SerializedName("sample_registration_date")
@Expose
private Object sampleRegistrationDate;
@SerializedName("created_at")
@Expose
private String createdAt;

public String getBioneordersId() {
return bioneordersId;
}

public void setBioneordersId(String bioneordersId) {
this.bioneordersId = bioneordersId;
}

public Object getMagentoOrderId() {
return magentoOrderId;
}

public void setMagentoOrderId(Object magentoOrderId) {
this.magentoOrderId = magentoOrderId;
}

public String getCustomerId() {
return customerId;
}

public void setCustomerId(String customerId) {
this.customerId = customerId;
}

public Object getCorporateCustomerId() {
return corporateCustomerId;
}

public void setCorporateCustomerId(Object corporateCustomerId) {
this.corporateCustomerId = corporateCustomerId;
}

public String getBarCode() {
return barCode;
}

public void setBarCode(String barCode) {
this.barCode = barCode;
}

public String getBloodBarCode() {
return bloodBarCode;
}

public void setBloodBarCode(String bloodBarCode) {
this.bloodBarCode = bloodBarCode;
}

public String getSkuCode() {
return skuCode;
}

public void setSkuCode(String skuCode) {
this.skuCode = skuCode;
}

public String getKitName() {
return kitName;
}

public void setKitName(String kitName) {
this.kitName = kitName;
}

public String getActivationStatus() {
return activationStatus;
}

public void setActivationStatus(String activationStatus) {
this.activationStatus = activationStatus;
}

public Object getInviteStatus() {
return inviteStatus;
}

public void setInviteStatus(Object inviteStatus) {
this.inviteStatus = inviteStatus;
}

public String getFirstName() {
return firstName;
}

public void setFirstName(String firstName) {
this.firstName = firstName;
}

public String getLastName() {
return lastName;
}

public void setLastName(String lastName) {
this.lastName = lastName;
}

public String getPhoneNumber() {
return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
this.phoneNumber = phoneNumber;
}

public String getEmailId() {
return emailId;
}

public void setEmailId(String emailId) {
this.emailId = emailId;
}

public String getRelationship() {
return relationship;
}

public void setRelationship(String relationship) {
this.relationship = relationship;
}

public String getGender() {
return gender;
}

public void setGender(String gender) {
this.gender = gender;
}

public String getDateOfBirth() {
return dateOfBirth;
}

public void setDateOfBirth(String dateOfBirth) {
this.dateOfBirth = dateOfBirth;
}

public String getTermsAndCondition() {
return termsAndCondition;
}

public void setTermsAndCondition(String termsAndCondition) {
this.termsAndCondition = termsAndCondition;
}

public String getAcceptConsent() {
return acceptConsent;
}

public void setAcceptConsent(String acceptConsent) {
this.acceptConsent = acceptConsent;
}

public Object getSampleRegistrationDate() {
return sampleRegistrationDate;
}

public void setSampleRegistrationDate(Object sampleRegistrationDate) {
this.sampleRegistrationDate = sampleRegistrationDate;
}

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

}