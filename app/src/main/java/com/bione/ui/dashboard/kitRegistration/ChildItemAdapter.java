package com.bione.ui.dashboard.kitRegistration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.questionnaire.Field;

import java.util.List;

public class ChildItemAdapter extends RecyclerView.Adapter<ChildItemAdapter.ChildViewHolder> {

    private List<Field> ChildItemList;
    // An object of RecyclerView.RecycledViewPool is created to share the Views between the child and the parent RecyclerViews
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    // Constructor
    ChildItemAdapter(List<Field> childItemList) {
        this.ChildItemList = childItemList;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Here we inflate the corresponding// layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_recycler, viewGroup, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position) {

        // Create an instance of the ChildItem// class for the given position
        Field childItem = ChildItemList.get(position);

        // For the created instance, set title.// No need to set the image for// the ImageViews because we have//
        // provided the source for the images// in the layout file itself
        childViewHolder.ChildItemTitle.setText("child : " + childItem.getQuestion());

        // Here we have assigned the layout// as LinearLayout with vertical orientation
        LinearLayoutManager layoutManager = new LinearLayoutManager(childViewHolder.OptionRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL, false);
        if (childItem.getOptions() != null) {
            // Since this is a nested layout, so// to define how many child items// should be prefetched when the
            // child RecyclerView is nested// inside the parent RecyclerView,// we use the following method
            layoutManager.setInitialPrefetchItemCount(childItem.getOptions().size());

            // Create an instance of the child// item view adapter and set its// adapter, layout manager and RecyclerViewPool
            OptionItemAdapter optionItemAdapter = new OptionItemAdapter(childItem.getOptions());
            childViewHolder.OptionRecyclerView.setLayoutManager(layoutManager);
            childViewHolder.OptionRecyclerView.setAdapter(optionItemAdapter);
            childViewHolder.OptionRecyclerView.setRecycledViewPool(viewPool);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        // This method returns the number// of items we have added// in the ChildItemList// i.e. the number of instances//
        // of the ChildItemList// that have been created
        return ChildItemList.size();
    }

    // This class is to initialize// the Views present// in the child RecyclerView
    class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView ChildItemTitle;
        private RecyclerView OptionRecyclerView;

        ChildViewHolder(View itemView) {
            super(itemView);
            ChildItemTitle = itemView.findViewById(R.id.tvQsn);
            OptionRecyclerView = itemView.findViewById(R.id.item_recycler);
        }
    }
}