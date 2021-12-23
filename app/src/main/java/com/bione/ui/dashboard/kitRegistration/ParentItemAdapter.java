package com.bione.ui.dashboard.kitRegistration;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.questionnaire.Datum;
import com.bione.utils.Log;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import static com.bione.utils.AppConstant.VIEW_TYPE_ONE;
import static com.bione.utils.AppConstant.VIEW_TYPE_THREE;
import static com.bione.utils.AppConstant.VIEW_TYPE_TWO;

public class ParentItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OptionItemAdapter.OnOptionListener {

    // An object of RecyclerView.RecycledViewPool is created to share the Views between the child and the parent RecyclerViews
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private RecyclerView.RecycledViewPool viewPool2 = new RecyclerView.RecycledViewPool();
    private List<Datum> itemList;
    private OnNoteListener onNoteListener;
    private OnEditTextChanged onEditTextChanged;


    ParentItemAdapter(List<Datum> itemList, OnNoteListener onNoteListener, OnEditTextChanged onEditTextChanged) {
        this.itemList = itemList;
        this.onNoteListener = onNoteListener;
        this.onEditTextChanged = onEditTextChanged;
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
                return new TextTypeViewHolder(view, new MyCustomEditTextListener());

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

    public List<Datum> getArrayList() {
        return itemList;
    }

    @Override
    public void onOptionClick(int position) {
        Log.d("onOptionClick", "----" + position);
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
            Log.d("position", "----------" + position);


            // Create an instance of the ParentItem class for the given position
            Datum parentItem = itemList.get(position);

            // For the created instance, get the title and set it as the text for the TextView
            ParentItemTitle.setText("" + parentItem.getQuestion());
//            ParentItemTitle.setText("parent : " + parentItem.getQuestion());

            LinearLayoutManager layoutManager = new LinearLayoutManager(ChildRecyclerView.getContext(),
                    LinearLayoutManager.VERTICAL, false);

            if (parentItem.getFields() != null) {
                Log.d("itemList.get(position)", "----------" + itemList.get(position).getFields().get(0).getOptions().size());
                Log.d("parentItem", "----------" + parentItem.getFields().get(0).getOptions().size());
                layoutManager.setInitialPrefetchItemCount(parentItem.getFields().size());

                // Create an instance of the child// item view adapter and set its// adapter, layout manager and RecyclerViewPool
                ChildItemAdapter childItemAdapter = new ChildItemAdapter(parentItem.getFields(),
                        new ChildItemAdapter.OnChildListener() {
                            @Override
                            public void onChildClick(int childPosition, String value) {
                                Log.d("child option click" + childPosition, "-----" + value);
                                itemList.get(position).setAnswer(value);
                            }
                        },
                        new ChildItemAdapter.OnEditTextChanged() {
                            @Override
                            public void onTextChanged(int childPosition, String charSeq) {
//                                Log.d("child onTextChanged" + childPosition, "-----" + charSeq);
                                itemList.get(position).setAnswer(charSeq);
                            }
                        });
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

            ChildItemTitle.setText(childItem.getType() + "" + childItem.getQuestion());
//            ChildItemTitle.setText("parent option : " + childItem.getQuestion());

            // Here we have assigned the layout// as LinearLayout with vertical orientation
            LinearLayoutManager layoutManager = new LinearLayoutManager(OptionRecyclerView.getContext(),
                    LinearLayoutManager.VERTICAL, false);

            if (childItem.getOptions() != null) {

                layoutManager.setInitialPrefetchItemCount(childItem.getOptions().size());

                // Create an instance of the child// item view adapter and set its// adapter, layout manager and RecyclerViewPool
                OptionItemAdapter optionItemAdapter = new OptionItemAdapter(childItem.getOptions(), childItem.getType(), new OptionItemAdapter.OnOptionListener() {
                    @Override
                    public void onOptionClick(int positionOption) {
                        Log.d("parent onOptionClick", "------" + positionOption);
                        itemList.get(position).setAnswer(childItem.getOptions().get(positionOption).getName());
                    }
                });
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
            Datum qsnList = itemList.get(position);
            tvQsn.setText(qsnList.getQuestion());
            textInputLayout.setHint(qsnList.getQuestion());
            myCustomEditTextListener.updatePosition(getAdapterPosition());

            if (qsnList.getAnswer() != null) {
                if (qsnList.getAnswer().equals("")) {
                    editText.setText("");
                } else {
                    editText.setText(qsnList.getAnswer());
                }
            } else {
                editText.setText("");
            }


//            editText.setText("");

//            editText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                    Log.d("position", "----" + position);
////                    Log.d("charSequence", "----" + charSequence.toString());
//                    onEditTextChanged.onTextChanged(position, charSequence.toString());
////                    itemList.get(position).setAnswer(charSequence.toString());
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
////                    Log.d("position", "----" + position);
////                    Log.d("editable", "----" + editable.toString());
//
//
//                }
//            });

        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }

    public interface OnEditTextChanged {
        void onTextChanged(int position, String charSeq);
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
            itemList.get(position).setAnswer(charSequence.toString());

            onEditTextChanged.onTextChanged(position, charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}