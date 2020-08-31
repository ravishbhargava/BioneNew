package com.bione.ui.schedulecall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.CounsellorsData;
import com.bione.ui.schedulecall.CategorySelect;

import java.util.ArrayList;


public class CounsellorsAdapter extends RecyclerView.Adapter<CounsellorsAdapter.MyViewHolder> {

    private ArrayList<CounsellorsData> counsellorsData;
    private int checkedPosition = -1;
    private final CategorySelect.OnItemClickListener listener;

    public CounsellorsAdapter(final ArrayList<CounsellorsData> counsellorsData, final CategorySelect.OnItemClickListener listener) {

        this.counsellorsData = counsellorsData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cousellor, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tvName.setText(counsellorsData.get(position).getName());
        holder.tvType.setText(counsellorsData.get(position).getType());

        holder.llCounsellor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return this.counsellorsData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private View view;
        private AppCompatTextView tvCounsellorName;
        private AppCompatTextView tvName;
        private AppCompatTextView tvType;
        private LinearLayoutCompat llCounsellor;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            tvType = v.findViewById(R.id.tvType);
            tvName = v.findViewById(R.id.tvName);
            tvCounsellorName = v.findViewById(R.id.tvCounsellorName);

            llCounsellor = v.findViewById(R.id.llCounsellor);

        }

    }

    public void refreshEvents(ArrayList<CounsellorsData> counsellorsData) {
        checkedPosition = -1;
        this.counsellorsData.clear();
        this.counsellorsData.addAll(counsellorsData);
        notifyDataSetChanged();
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

}

