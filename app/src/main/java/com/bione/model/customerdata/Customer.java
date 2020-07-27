package com.bione.model.customerdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("entity_id")
    @Expose
    private String entityId;
    @SerializedName("website_id")
    @Expose
    private String websiteId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("increment_id")
    @Expose
    private Object incrementId;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("disable_auto_group_change")
    @Expose
    private String disableAutoGroupChange;
    @SerializedName("created_in")
    @Expose
    private String createdIn;
    @SerializedName("prefix")
    @Expose
    private Object prefix;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("middlename")
    @Expose
    private Object middlename;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("suffix")
    @Expose
    private Object suffix;
    @SerializedName("dob")
    @Expose
    private Object dob;
    @SerializedName("password_hash")
    @Expose
    private String passwordHash;
    @SerializedName("rp_token")
    @Expose
    private String rpToken;
    @SerializedName("rp_token_created_at")
    @Expose
    private String rpTokenCreatedAt;
    @SerializedName("default_billing")
    @Expose
    private Object defaultBilling;
    @SerializedName("default_shipping")
    @Expose
    private Object defaultShipping;
    @SerializedName("taxvat")
    @Expose
    private Object taxvat;
    @SerializedName("confirmation")
    @Expose
    private Object confirmation;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("failures_num")
    @Expose
    private String failuresNum;
    @SerializedName("first_failure")
    @Expose
    private Object firstFailure;
    @SerializedName("lock_expires")
    @Expose
    private Object lockExpires;
    @SerializedName("mobilenumber")
    @Expose
    private String mobilenumber;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(String websiteId) {
        this.websiteId = websiteId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Object getIncrementId() {
        return incrementId;
    }

    public void setIncrementId(Object incrementId) {
        this.incrementId = incrementId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getDisableAutoGroupChange() {
        return disableAutoGroupChange;
    }

    public void setDisableAutoGroupChange(String disableAutoGroupChange) {
        this.disableAutoGroupChange = disableAutoGroupChange;
    }

    public String getCreatedIn() {
        return createdIn;
    }

    public void setCreatedIn(String createdIn) {
        this.createdIn = createdIn;
    }

    public Object getPrefix() {
        return prefix;
    }

    public void setPrefix(Object prefix) {
        this.prefix = prefix;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Object getMiddlename() {
        return middlename;
    }

    public void setMiddlename(Object middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Object getSuffix() {
        return suffix;
    }

    public void setSuffix(Object suffix) {
        this.suffix = suffix;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRpToken() {
        return rpToken;
    }

    public void setRpToken(String rpToken) {
        this.rpToken = rpToken;
    }

    public String getRpTokenCreatedAt() {
        return rpTokenCreatedAt;
    }

    public void setRpTokenCreatedAt(String rpTokenCreatedAt) {
        this.rpTokenCreatedAt = rpTokenCreatedAt;
    }

    public Object getDefaultBilling() {
        return defaultBilling;
    }

    public void setDefaultBilling(Object defaultBilling) {
        this.defaultBilling = defaultBilling;
    }

    public Object getDefaultShipping() {
        return defaultShipping;
    }

    public void setDefaultShipping(Object defaultShipping) {
        this.defaultShipping = defaultShipping;
    }

    public Object getTaxvat() {
        return taxvat;
    }

    public void setTaxvat(Object taxvat) {
        this.taxvat = taxvat;
    }

    public Object getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(Object confirmation) {
        this.confirmation = confirmation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFailuresNum() {
        return failuresNum;
    }

    public void setFailuresNum(String failuresNum) {
        this.failuresNum = failuresNum;
    }

    public Object getFirstFailure() {
        return firstFailure;
    }

    public void setFirstFailure(Object firstFailure) {
        this.firstFailure = firstFailure;
    }

    public Object getLockExpires() {
        return lockExpires;
    }

    public void setLockExpires(Object lockExpires) {
        this.lockExpires = lockExpires;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

}