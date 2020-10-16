package com.bione.model.counsellors;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListItem implements Parcelable {

    @SerializedName("mobilecounselling_id")
    @Expose
    private String mobilecounsellingId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("genetic_type")
    @Expose
    private String geneticType;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time_slot")
    @Expose
    private String timeSlot;
    @SerializedName("feedback")
    @Expose
    private String feedback;
    @SerializedName("counsellor_summary")
    @Expose
    private String counsellorSummary;
    @SerializedName("counsellor_name")
    @Expose
    private String counsellorName;
    @SerializedName("stars_ratings")
    @Expose
    private String starsRatings;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("reason_cancelation")
    @Expose
    private String reasonCancelation;


    public String getReasonCancelation() {
        return reasonCancelation;
    }

    public void setReasonCancelation(String reasonCancelation) {
        this.reasonCancelation = reasonCancelation;
    }

    public String getMobilecounsellingId() {
        return mobilecounsellingId;
    }

    public void setMobilecounsellingId(String mobilecounsellingId) {
        this.mobilecounsellingId = mobilecounsellingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getGeneticType() {
        return geneticType;
    }

    public void setGeneticType(String geneticType) {
        this.geneticType = geneticType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getCounsellorSummary() {
        return counsellorSummary;
    }

    public void setCounsellorSummary(String counsellorSummary) {
        this.counsellorSummary = counsellorSummary;
    }

    public String getCounsellorName() {
        return counsellorName;
    }

    public void setCounsellorName(String counsellorName) {
        this.counsellorName = counsellorName;
    }

    public String getStarsRatings() {
        return starsRatings;
    }

    public void setStarsRatings(String starsRatings) {
        this.starsRatings = starsRatings;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ListItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mobilecounsellingId);
        dest.writeString(this.customerName);
        dest.writeString(this.customerId);
        dest.writeString(this.geneticType);
        dest.writeString(this.date);
        dest.writeString(this.timeSlot);
        dest.writeString(this.feedback);
        dest.writeString(this.counsellorSummary);
        dest.writeString(this.counsellorName);
        dest.writeString(this.starsRatings);
        dest.writeString(this.createdAt);
        dest.writeString(this.status);
        dest.writeString(this.reasonCancelation);
    }

    protected ListItem(Parcel in) {
        this.mobilecounsellingId = in.readString();
        this.customerName = in.readString();
        this.customerId = in.readString();
        this.geneticType = in.readString();
        this.date = in.readString();
        this.timeSlot = in.readString();
        this.feedback = in.readString();
        this.counsellorSummary = in.readString();
        this.counsellorName = in.readString();
        this.starsRatings = in.readString();
        this.createdAt = in.readString();
        this.status = in.readString();
        this.reasonCancelation = in.readString();
    }

    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel source) {
            return new ListItem(source);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };
}