package com.bione.model;

import android.os.Parcelable;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class CommonListDataParent extends ExpandableGroup implements Parcelable {

    private String count;

    public CommonListDataParent(String title, List items,String count) {
        super(title, items);
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
