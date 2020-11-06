package com.bione.ui.myfoodadvice.subcategory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bione.R;
import com.bione.model.CommonListData;
import com.bione.model.CommonListDataParent;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ExpandableRecyclerView extends ExpandableRecyclerViewAdapter<ParentHolder, ChildHolder> {

    private List<CommonListDataParent> parentList;

    public ExpandableRecyclerView(List<? extends ExpandableGroup> groups) {
        super(groups);
    }


    @Override
    public ParentHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_food_advice, parent, false);
        return new ParentHolder(v);
    }

    @Override
    public ChildHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_food_advice, parent, false);
        return new ChildHolder(v);
    }

    @Override
    public void onBindChildViewHolder(ChildHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        CommonListData data = (CommonListData) group.getItems().get(childIndex);
        holder.bind(data);
    }

    @Override
    public void onBindGroupViewHolder(ParentHolder holder, int flatPosition, ExpandableGroup group) {
        CommonListDataParent dataParent = (CommonListDataParent) group;
        holder.bind(dataParent);
    }
}
