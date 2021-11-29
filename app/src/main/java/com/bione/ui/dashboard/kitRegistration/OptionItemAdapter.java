package com.bione.ui.dashboard.kitRegistration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.questionnaire.Option;
import com.bione.utils.Log;

import java.util.List;

public class OptionItemAdapter extends RecyclerView.Adapter<OptionItemAdapter.OptionViewHolder> {

    private List<Option> OptionItemList;

    // Constructor
    OptionItemAdapter(List<Option> optionItemList) {
        this.OptionItemList = optionItemList;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Here we inflate the corresponding// layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_recycler, viewGroup, false);

        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder optionViewHolder, int position) {

        // Create an instance of the ChildItem// class for the given position
        Option optionItem = OptionItemList.get(position);

        // For the created instance, set title.// No need to set the image for// the ImageViews because we have//
        // provided the source for the images// in the layout file itself
        optionViewHolder.OptionItemTitle.setText("option : " + optionItem.getName());

        optionViewHolder.OptionItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("option item clicked", "-----" + optionItem.getName());
            }
        });
    }

    @Override
    public int getItemCount() {

        // This method returns the number// of items we have added// in the ChildItemList// i.e. the number of instances//
        // of the ChildItemList// that have been created
        return OptionItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // This class is to initialize// the Views present// in the child RecyclerView
    class OptionViewHolder extends RecyclerView.ViewHolder {

        private TextView OptionItemTitle;

        OptionViewHolder(View itemView) {
            super(itemView);
            OptionItemTitle = itemView.findViewById(R.id.tvQsn);
        }
    }
}