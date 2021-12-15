package com.bione.ui.dashboard.kitRegistration;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.questionnaire.Field;
import com.bione.utils.Log;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import static com.bione.utils.AppConstant.VIEW_TYPE_THREE;
import static com.bione.utils.AppConstant.VIEW_TYPE_TWO;

public class ChildItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OptionItemAdapter.OnOptionListener {

    private List<Field> ChildItemList;
    // An object of RecyclerView.RecycledViewPool is created to share the Views between the child and the parent RecyclerViews
    private RecyclerView.RecycledViewPool viewPool3 = new RecyclerView.RecycledViewPool();

    private OnEditTextChanged onEditTextChanged;
    private OnChildListener onChildListener;

    // Constructor
    ChildItemAdapter(List<Field> childItemList, OnChildListener onChildListener, OnEditTextChanged onEditTextChanged) {
        this.ChildItemList = childItemList;
        this.onEditTextChanged = onEditTextChanged;
        this.onChildListener = onChildListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {

            case VIEW_TYPE_TWO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_recycler, parent, false);
                return new ChildViewHolder(view);


            case VIEW_TYPE_THREE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_edit_text, parent, false);
                return new TextTypeViewHolder(view, new MyCustomEditTextListener());

        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {

            case VIEW_TYPE_TWO:
                ChildViewHolder viewHolder1 = (ChildViewHolder) holder;
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

    @Override
    public void onOptionClick(int position) {
        Log.d("onOptionClick", "-----" + position);
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


            ChildItemTitle.setText(childItem.getType() + "" + childItem.getQuestion());
//            ChildItemTitle.setText("child : " + childItem.getQuestion());
            LinearLayoutManager layoutManager;

            // Here we have assigned the layout// as LinearLayout with vertical orientation
            layoutManager = new LinearLayoutManager(OptionRecyclerView.getContext(),
                    LinearLayoutManager.VERTICAL, false);


            if (childItem.getOptions() != null) {
                Log.d("ChildItemList.get(position)", "----------" + ChildItemList.get(position).getOptions().size());
                Log.d("childItem", "----------" + childItem.getOptions().size());
                layoutManager.setInitialPrefetchItemCount(childItem.getOptions().size());

                // Create an instance of the child// item view adapter and set its// adapter, layout manager and RecyclerViewPool
                OptionItemAdapter optionItemAdapter = new OptionItemAdapter(childItem.getOptions(), childItem.getType(), new OptionItemAdapter.OnOptionListener() {
                    @Override
                    public void onOptionClick(int position) {
                        Log.d(" child onOptionClick", "------" + position);
                        onChildListener.onChildClick(position, childItem.getOptions().get(position).getName());
                    }
                });
                OptionRecyclerView.setLayoutManager(layoutManager);
                OptionRecyclerView.setAdapter(optionItemAdapter);
                OptionRecyclerView.setRecycledViewPool(viewPool3);
            }
        }
    }

    class TextTypeViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvQsn;
        TextInputLayout textInputLayout;
        TextInputEditText editText;
        public MyCustomEditTextListener myCustomEditTextListener;


        public TextTypeViewHolder(View itemView, MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);

            this.tvQsn = (AppCompatTextView) itemView.findViewById(R.id.tvQsn);
            this.textInputLayout = (TextInputLayout) itemView.findViewById(R.id.textInputLayout);
            this.editText = (TextInputEditText) itemView.findViewById(R.id.editText);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.editText.addTextChangedListener(myCustomEditTextListener);

        }

        void onBind(int position) {
            Field qsnList = ChildItemList.get(position);
            tvQsn.setText(qsnList.getQuestion());
            textInputLayout.setHint(qsnList.getQuestion());
            myCustomEditTextListener.updatePosition(getAdapterPosition());
        }
    }

    // we make TextWatcher to be aware of the position it currently works with
    // this way, once a new item is attached in onBindViewHolder, it will
    // update current position MyCustomEditTextListener, reference to which is kept by ViewHolder
    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            onEditTextChanged.onTextChanged(position, charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    public interface OnEditTextChanged {
        void onTextChanged(int childPosition, String charSeq);
    }

    public interface OnChildListener {
        void onChildClick(int childPosition, String value);
    }

    void setArrayList(List<Field> childItemList) {
        ChildItemList.clear();
        ChildItemList.addAll(childItemList);
        notifyDataSetChanged();
    }
}