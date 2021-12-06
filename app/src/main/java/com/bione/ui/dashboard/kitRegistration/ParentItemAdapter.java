package com.bione.ui.dashboard.kitRegistration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.questionnaire.Datum;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import static com.bione.utils.AppConstant.VIEW_TYPE_ONE;
import static com.bione.utils.AppConstant.VIEW_TYPE_THREE;
import static com.bione.utils.AppConstant.VIEW_TYPE_TWO;

public class ParentItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // An object of RecyclerView.RecycledViewPool is created to share the Views between the child and the parent RecyclerViews
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private RecyclerView.RecycledViewPool viewPool2 = new RecyclerView.RecycledViewPool();
    private List<Datum> itemList;

    ParentItemAdapter(List<Datum> itemList) {
        this.itemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_ONE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_recycler, parent, false);
                return new ParentViewHolder(view);

            case VIEW_TYPE_TWO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_recycler, parent, false);
                return new OptionViewHolder(view);


            case VIEW_TYPE_THREE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_edit_text, parent, false);
                return new TextTypeViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_ONE:
                ParentViewHolder viewHolder0 = (ParentViewHolder) holder;
                viewHolder0.onBind(position);
                break;

            case VIEW_TYPE_TWO:
                OptionViewHolder viewHolder1 = (OptionViewHolder) holder;
                viewHolder1.onBind(position);
                break;

            case VIEW_TYPE_THREE:
                TextTypeViewHolder viewHolder2 = (TextTypeViewHolder) holder;
                viewHolder2.onBind(position);
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.get(position).getFields() != null) {
            return VIEW_TYPE_ONE;
        } else if (itemList.get(position).getOptions() != null) {
            return VIEW_TYPE_TWO;
        } else {
            return VIEW_TYPE_THREE;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setArrayList(List<Datum> list) {
        itemList.clear();
        itemList.addAll(list);
        notifyDataSetChanged();
    }

    class ParentViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView ParentItemTitle;
        private RecyclerView ChildRecyclerView;

        ParentViewHolder(final View itemView) {
            super(itemView);

            ParentItemTitle = itemView.findViewById(R.id.tvQsn);
            ChildRecyclerView = itemView.findViewById(R.id.item_recycler);
        }

        void onBind(int position) {
            // Create an instance of the ParentItem class for the given position
            Datum parentItem = itemList.get(position);

            // For the created instance, get the title and set it as the text for the TextView
            ParentItemTitle.setText("" + parentItem.getQuestion());
//            ParentItemTitle.setText("parent : " + parentItem.getQuestion());

            // Create a layout manager// to assign a layout// to the RecyclerView.
            // Here we have assigned the layout// as LinearLayout with vertical orientation
            LinearLayoutManager layoutManager = new LinearLayoutManager(ChildRecyclerView.getContext(),
                    LinearLayoutManager.VERTICAL, false);
            if (parentItem.getFields() != null) {
                // Since this is a nested layout, so// to define how many child items// should be prefetched when the
                // child RecyclerView is nested// inside the parent RecyclerView,// we use the following method
                layoutManager.setInitialPrefetchItemCount(parentItem.getFields().size());

                // Create an instance of the child// item view adapter and set its// adapter, layout manager and RecyclerViewPool
                ChildItemAdapter childItemAdapter = new ChildItemAdapter(parentItem.getFields());
                ChildRecyclerView.setLayoutManager(layoutManager);
                ChildRecyclerView.setAdapter(childItemAdapter);
                ChildRecyclerView.setRecycledViewPool(viewPool2);
            }
        }
    }

    class OptionViewHolder extends RecyclerView.ViewHolder {

        private TextView ChildItemTitle;
        private RecyclerView OptionRecyclerView;

        OptionViewHolder(final View itemView) {
            super(itemView);

            ChildItemTitle = itemView.findViewById(R.id.tvQsn);
            OptionRecyclerView = itemView.findViewById(R.id.item_recycler);
        }

        void onBind(int position) {
            // Create an instance of the ChildItem// class for the given position
            Datum childItem = itemList.get(position);

            // For the created instance, set title.// No need to set the image for// the ImageViews because we have//
            // provided the source for the images// in the layout file itself
            ChildItemTitle.setText(childItem.getType() + "" + childItem.getQuestion());
//            ChildItemTitle.setText("parent option : " + childItem.getQuestion());

            LinearLayoutManager layoutManager;
//            if (childItem.getType().equals("radio")) {
//                layoutManager = new LinearLayoutManager(OptionRecyclerView.getContext(),
//                        LinearLayoutManager.HORIZONTAL, false);
//            } else {
            // Here we have assigned the layout// as LinearLayout with vertical orientation
            layoutManager = new LinearLayoutManager(OptionRecyclerView.getContext(),
                    LinearLayoutManager.VERTICAL, false);
//            }

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
            Datum qsnList = itemList.get(position);
            tvQsn.setText(qsnList.getQuestion());
            textInputLayout.setHint(qsnList.getQuestion());

        }
    }
}