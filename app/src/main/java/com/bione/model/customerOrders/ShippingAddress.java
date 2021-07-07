package com.bione.model.customerOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class ShippingAddress implements Serializable {

@SerializedName("name")
@Expose
private String name;
@SerializedName("street")
@Expose
private List<String> street = null;
@SerializedName("city")
@Expose
private String city;
@SerializedName("state")
@Expose
private String state;
@SerializedName("country")
@Expose
private String country;
@SerializedName("post_code")
@Expose
private String postCode;
@SerializedName("Phone")
@Expose
private String phone;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public List<String> getStreet() {
return street;
}

public void setStreet(List<String> street) {
this.street = street;
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

public String getCountry() {
return country;
}

public void setCountry(String country) {
this.country = country;
}

public String getPostCode() {
return postCode;
}

public void setPostCode(String postCode) {
this.postCode = postCode;
}

public String getPhone() {
return phone;
}

public void setPhone(String phone) {
this.phone = phone;
}

}