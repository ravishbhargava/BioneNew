package com.bione.ui.dashboard.kitRegistration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.questionnaire.Field;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import static com.bione.utils.AppConstant.VIEW_TYPE_THREE;
import static com.bione.utils.AppConstant.VIEW_TYPE_TWO;

public class ChildItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Field> ChildItemList;
    // An object of RecyclerView.RecycledViewPool is created to share the Views between the child and the parent RecyclerViews
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    // Constructor
    ChildItemAdapter(List<Field> childItemList) {
        this.ChildItemList = childItemList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {

            case VIEW_TYPE_TWO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_recycler, parent, false);
                return new ChildItemAdapter.ChildViewHolder(view);


            case VIEW_TYPE_THREE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_edit_text, parent, false);
                return new ChildItemAdapter.TextTypeViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {

            case VIEW_TYPE_TWO:
                ChildItemAdapter.ChildViewHolder viewHolder1 = (ChildItemAdapter.ChildViewHolder) holder;
                viewHolder1.onBind(position);
                break;

            case VIEW_TYPE_THREE:
                ChildItemAdapter.TextTypeViewHolder viewHolder2 = (ChildItemAdapter.TextTypeViewHolder) holder;
                viewHolder2.onBind(position);
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (ChildItemList.get(position).getOptions() != null) {
            return VIEW_TYPE_TWO;
        } else {
            return VIEW_TYPE_THREE;
        }
    }

    @Override
    public int getItemCount() {
        return ChildItemList.size();
    }

    // This class is to initialize// the Views present// in the child RecyclerView
    class ChildViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView ChildItemTitle;
        private RecyclerView OptionRecyclerView;

        ChildViewHolder(View itemView) {
            super(itemView);
            ChildItemTitle = itemView.findViewById(R.id.tvQsn);
            OptionRecyclerView = itemView.findViewById(R.id.item_recycler);
        }

        void onBind(int position) {
            // Create an instance of the ChildItem// class for the given position
            Field childItem = ChildItemList.get(position);

            // For the created instance, set title.// No need to set the image for// the ImageViews because we have//
            // provided the source for the images// in the layout file itself
            ChildItemTitle.setText("" + childItem.getQuestion());
//            ChildItemTitle.setText("child : " + childItem.getQuestion());

            // Here we have assigned the layout// as LinearLayout with vertical orientation
            LinearLayoutManager layoutManager = new LinearLayoutManager(OptionRecyclerView.getContext(),
                    LinearLayoutManager.VERTICAL, false);
            if (childItem.getOptions() != null) {
                // Since this is a nested layout, so// to define how many child items// should be prefetched when the
                // child RecyclerView is nested// inside the parent RecyclerView,// we use the following method
                layoutManager.setInitialPrefetchItemCount(childItem.getOptions().size());

                // Create an instance of the child// item view adapter and set its// adapter, layout manager and RecyclerViewPool
                OptionItemAdapter optionItemAdapter = new OptionItemAdapter(childItem.getOptions());
                OptionRecyclerView.setLayoutManager(layoutManager);
                OptionRecyclerView.setAdapter(optionItemAdapter);
                OptionRecyclerView.setRecycledViewPool(viewPool);
            }
        }
    }

    class TextTypeViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvQsn;
        TextInputLayout textInputLayout;
        TextInputEditText editText;


        public TextTypeViewHolder(View itemView) {
            super(itemView);

            this.tvQsn = (AppCompatTextView) itemView.findViewById(R.id.tvQsn);
            this.textInputLayout = (TextInputLayout) itemView.findViewById(R.id.textInputLayout);
            this.editText = (TextInputEditText) itemView.findViewById(R.id.editText);

        }

        void onBind(int position) {
            Field qsnList = ChildItemList.get(position);
            tvQsn.setText(qsnList.getQuestion());
            textInputLayout.setHint(qsnList.getQuestion());

        }
    }
}