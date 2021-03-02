package com.bione.model.availableSlots;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Slot {

@SerializedName("code")
@Expose
private Integer code;
@SerializedName("message")
@Expose
private String message;
@SerializedName("list_items")
@Expose
private List<ListItem> listItems = null;

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

public List<ListItem> getListItems() {
return listItems;
}

public void setListItems(List<ListItem> listItems) {
this.listItems = listItems;
}

}