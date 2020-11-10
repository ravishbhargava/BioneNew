package com.bione.ui.myfoodadvice.subcategory.holder;

import android.view.View;
import android.widget.TextView;

import com.bione.R;
import com.bione.model.CommonListDataParent;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class ParentHolder extends GroupViewHolder {

    private TextView textView;
    private TextView tvNumber;

    public ParentHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.tvHeading);
        tvNumber = itemView.findViewById(R.id.tvNumber);
    }

    public void bind(final CommonListDataParent data) {
        textView.setText(data.getTitle());
        tvNumber.setText(data.getCount());
    }
}
