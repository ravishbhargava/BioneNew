package com.bione.model.availableSlots;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListItem {

@SerializedName("time_slot")
@Expose
private String timeSlot;


public String getTimeSlot() {
return timeSlot;
}

public void setTimeSlot(String timeSlot) {
this.timeSlot = timeSlot;
}

}