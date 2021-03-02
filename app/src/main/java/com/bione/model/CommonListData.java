package com.bione.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CommonListData implements Parcelable {

    private int drawable;
    private String heading;
    private String text;
    private boolean isChecked;


    public CommonListData(int drawable, String heading, String text, boolean isChecked) {
        this.drawable = drawable;
        this.heading = heading;
        this.text = text;
        this.isChecked = isChecked;

    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.drawable);
        dest.writeString(this.heading);
        dest.writeString(this.text);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected CommonListData(Parcel in) {
        this.drawable = in.readInt();
        this.heading = in.readString();
        this.text = in.readString();
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<CommonListData> CREATOR = new Creator<CommonListData>() {
        @Override
        public CommonListData createFromParcel(Parcel source) {
            return new CommonListData(source);
        }

        @Override
        public CommonListData[] newArray(int size) {
            return new CommonListData[size];
        }
    };
}
