package com.bione.model.counsellors;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Counselling implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.code);
        dest.writeString(this.message);
        dest.writeTypedList(this.listItems);
    }

    public Counselling() {
    }

    protected Counselling(Parcel in) {
        this.code = (Integer) in.readValue(Integer.class.getClassLoader());
        this.message = in.readString();
        this.listItems = in.createTypedArrayList(ListItem.CREATOR);
    }

    public static final Creator<Counselling> CREATOR = new Creator<Counselling>() {
        @Override
        public Counselling createFromParcel(Parcel source) {
            return new Counselling(source);
        }

        @Override
        public Counselling[] newArray(int size) {
            return new Counselling[size];
        }
    };
}