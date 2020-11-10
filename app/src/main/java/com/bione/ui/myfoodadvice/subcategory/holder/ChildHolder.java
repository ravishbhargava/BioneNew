package com.bione.ui.myfoodadvice.subcategory.holder;

import android.view.View;
import android.widget.TextView;

import com.bione.R;
import com.bione.model.CommonListData;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ChildHolder extends ChildViewHolder {

    private TextView textView;

    public ChildHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.tvHeading);
    }

    public void bind(final CommonListData data) {
        textView.setText(data.getHeading());
    }
}
