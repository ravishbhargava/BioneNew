package com.bione.ui.dashboard.kitRegistration;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.questionnaire.Option;
import com.bione.utils.Log;

import java.util.ArrayList;
import java.util.List;

public class OptionItemAdapter extends RecyclerView.Adapter<OptionItemAdapter.OptionViewHolder> {

    private List<Option> OptionItemList;
    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
//    private int checkedPosition = -1;
    private OnOptionListener onOptionListener;
    private String selectionType;

    // Constructor
    OptionItemAdapter(List<Option> optionItemList, String selectionType, OnOptionListener onOptionListener) {
        this.OptionItemList = optionItemList;
        this.onOptionListener = onOptionListener;
        this.selectionType = selectionType;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Here we inflate the corresponding// layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_list_item, viewGroup, false);

        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder optionViewHolder, int position) {

        // Create an instance of the ChildItem// class for the given position
        Option optionItem = OptionItemList.get(position);

        // For the created instance, set title.// No need to set the image for// the ImageViews because we have//
        // provided the source for the images// in the layout file itself
        optionViewHolder.OptionItemTitle.setText("" + optionItem.getName());
//        optionViewHolder.OptionItemTitle.setText("option : " + optionItem.getName());

        if (optionItem.getSelected()) {
            optionViewHolder.root.setBackgroundResource(R.color.available_session_color);
        } else {
            optionViewHolder.root.setBackgroundResource(R.drawable.drawable_border_list_item);
        }
//        if (checkedPosition == -1) {
//            optionViewHolder.root.setBackgroundResource(R.drawable.drawable_border_list_item);
//        } else {
//            if (checkedPosition == position) {
//                optionViewHolder.root.setBackgroundResource(R.color.available_session_color);
//            } else {
//                optionViewHolder.root.setBackgroundResource(R.drawable.drawable_border_list_item);
//            }
//        }

        optionViewHolder.root.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                Log.d("option item clicked", "-----" + optionItem.getName());
//                if (checkedPosition != position) {
//                    notifyItemChanged(checkedPosition);
//                    checkedPosition = position;
//                }
                if(selectionType!=null) {
                    if (selectionType.equals("multiselect")) {
                        multiClicked(position, optionViewHolder.root);
                    } else {
                        singleClicked(position, optionViewHolder.root);
                    }
                }else{
                    singleClicked(position, optionViewHolder.root);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return OptionItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // This class is to initialize// the Views present// in the child RecyclerView
    class OptionViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView OptionItemTitle;
        private LinearLayout root;

        OptionViewHolder(View itemView) {
            super(itemView);
            OptionItemTitle = itemView.findViewById(R.id.tvQsn);
            root = itemView.findViewById(R.id.root);
        }
    }

    public interface OnOptionListener {
        void onOptionClick(int position);
    }

    public void multiClicked(int position, View view) {
        onOptionListener.onOptionClick(position);
        if (OptionItemList.get(position).getSelected()) {
            OptionItemList.get(position).setSelected(false);
            view.setBackgroundResource(R.drawable.drawable_border_list_item);
        } else {
            OptionItemList.get(position).setSelected(true);
            view.setBackgroundResource(R.color.available_session_color);
        }
        notifyItemChanged(position);
    }

    public void singleClicked(int position, View view) {
        onOptionListener.onOptionClick(position);

        for (int i = 0; i < OptionItemList.size(); i++) {
            if (i == position) {
                OptionItemList.get(i).setSelected(true);
                view.setBackgroundResource(R.color.available_session_color);
            } else {
                OptionItemList.get(i).setSelected(false);
                view.setBackgroundResource(R.drawable.drawable_border_list_item);
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<Option> getSelectedItem() {
        ArrayList<Option> selectedArray = new ArrayList<>();
        for (int i = 0; i < OptionItemList.size(); i++) {
            if (OptionItemList.get(i).getSelected()) {
                selectedArray.add(OptionItemList.get(i));
            }
        }
        return selectedArray;
    }
}