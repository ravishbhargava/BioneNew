package com.bione.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CrouselData implements Parcelable {

    private int drawable;
    private int drawableTest;
    private String headingTest;
    private String heading;
    private String text;
    private boolean isChecked;
    private String nameCounsellor;
    private String typeCounsellor;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        this.isChecked = checked;
    }

    public String getNameCounsellor() {
        return nameCounsellor;
    }

    public void setNameCounsellor(String nameCounsellor) {
        this.nameCounsellor = nameCounsellor;
    }

    public String getTypeCounsellor() {
        return typeCounsellor;
    }

    public void setTypeCounsellor(String typeCounsellor) {
        this.typeCounsellor = typeCounsellor;
    }

    public CrouselData() {
    }

    public String getHeadingTest() {
        return headingTest;
    }

    public void setHeadingTest(String headingTest) {
        this.headingTest = headingTest;
    }

    public int getDrawableTest() {
        return drawableTest;
    }

    public void setDrawableTest(int drawableTest) {
        this.drawableTest = drawableTest;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.drawable);
        dest.writeInt(this.drawableTest);
        dest.writeString(this.headingTest);
        dest.writeString(this.heading);
        dest.writeString(this.text);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeString(this.nameCounsellor);
        dest.writeString(this.typeCounsellor);
        dest.writeString(this.url);
    }

    protected CrouselData(Parcel in) {
        this.drawable = in.readInt();
        this.drawableTest = in.readInt();
        this.headingTest = in.readString();
        this.heading = in.readString();
        this.text = in.readString();
        this.isChecked = in.readByte() != 0;
        this.nameCounsellor = in.readString();
        this.typeCounsellor = in.readString();
        this.url = in.readString();
    }

    public static final Creator<CrouselData> CREATOR = new Creator<CrouselData>() {
        @Override
        public CrouselData createFromParcel(Parcel source) {
            return new CrouselData(source);
        }

        @Override
        public CrouselData[] newArray(int size) {
            return new CrouselData[size];
        }
    };
}
