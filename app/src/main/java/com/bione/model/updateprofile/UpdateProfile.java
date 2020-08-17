package com.bione.model.updateprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateProfile {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("group_id")
@Expose
private Integer groupId;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;
@SerializedName("created_in")
@Expose
private String createdIn;
@SerializedName("email")
@Expose
private String email;
@SerializedName("firstname")
@Expose
private String firstname;
@SerializedName("lastname")
@Expose
private String lastname;
@SerializedName("middlename")
@Expose
private String middlename;
@SerializedName("gender")
@Expose
private Integer gender;
@SerializedName("store_id")
@Expose
private Integer storeId;
@SerializedName("website_id")
@Expose
private Integer websiteId;
@SerializedName("addresses")
@Expose
private List<Object> addresses = null;
@SerializedName("disable_auto_group_change")
@Expose
private Integer disableAutoGroupChange;
@SerializedName("extension_attributes")
@Expose
private ExtensionAttributes extensionAttributes;
@SerializedName("custom_attributes")
@Expose
private List<CustomAttribute> customAttributes = null;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getGroupId() {
return groupId;
}

public void setGroupId(Integer groupId) {
this.groupId = groupId;
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

public String getCreatedIn() {
return createdIn;
}

public void setCreatedIn(String createdIn) {
this.createdIn = createdIn;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getFirstname() {
return firstname;
}

public void setFirstname(String firstname) {
this.firstname = firstname;
}

public String getLastname() {
return lastname;
}

public void setLastname(String lastname) {
this.lastname = lastname;
}

public String getMiddlename() {
return middlename;
}

public void setMiddlename(String middlename) {
this.middlename = middlename;
}

public Integer getGender() {
return gender;
}

public void setGender(Integer gender) {
this.gender = gender;
}

public Integer getStoreId() {
return storeId;
}

public void setStoreId(Integer storeId) {
this.storeId = storeId;
}

public Integer getWebsiteId() {
return websiteId;
}

public void setWebsiteId(Integer websiteId) {
this.websiteId = websiteId;
}

public List<Object> getAddresses() {
return addresses;
}

public void setAddresses(List<Object> addresses) {
this.addresses = addresses;
}

public Integer getDisableAutoGroupChange() {
return disableAutoGroupChange;
}

public void setDisableAutoGroupChange(Integer disableAutoGroupChange) {
this.disableAutoGroupChange = disableAutoGroupChange;
}

public ExtensionAttributes getExtensionAttributes() {
return extensionAttributes;
}

public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
this.extensionAttributes = extensionAttributes;
}

public List<CustomAttribute> getCustomAttributes() {
return customAttributes;
}

public void setCustomAttributes(List<CustomAttribute> customAttributes) {
this.customAttributes = customAttributes;
}

}