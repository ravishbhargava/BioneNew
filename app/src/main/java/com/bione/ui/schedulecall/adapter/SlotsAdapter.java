package com.bione.ui.schedulecall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.Slots;
import com.bione.ui.schedulecall.ScheduleNow;

import java.util.ArrayList;


public class SlotsAdapter extends RecyclerView.Adapter<SlotsAdapter.MyViewHolder> {

    private ArrayList<Slots> slots;
    private int checkedPosition = -1;
    private final ScheduleNow.OnItemClickListener listener;

    public SlotsAdapter(final ArrayList<Slots> slots, final ScheduleNow.OnItemClickListener listener) {

        this.slots = slots;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_slots, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tvSlotTime.setText(slots.get(position).name);
        if (slots.get(position).isSelected()) {
//            holder.tvNotAvailable.setVisibility(View.VISIBLE);
            holder.tvNotAvailable.setText("NOT AVAILABLE");
            holder.ivSelected.setVisibility(View.GONE);

        } else {
//            holder.tvNotAvailable.setVisibility(View.GONE);
            holder.tvNotAvailable.setText("AVAILABLE");
            holder.ivSelected.setVisibility(View.GONE);

            if (checkedPosition == -1) {
                holder.ivSelected.setVisibility(View.GONE);
            } else {
                if (checkedPosition == position) {
                    holder.ivSelected.setVisibility(View.VISIBLE);
                } else {
                    holder.ivSelected.setVisibility(View.GONE);
                }
            }
            holder.llSlots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!slots.get(position).isSelected()) {
                        holder.ivSelected.setVisibility(View.VISIBLE);

                        if (checkedPosition != position) {
                            notifyItemChanged(checkedPosition);
                            listener.onItemClick(slots.get(position).name);
                            checkedPosition = position;
                        }
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return this.slots.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private View view;
        private TextView tvSlotTime;
        private TextView tvNotAvailable;
        private ImageView ivSelected;
        private LinearLayoutCompat llSlots;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            tvSlotTime = v.findViewById(R.id.tvSlotTime);
            tvNotAvailable = v.findViewById(R.id.tvNotAvailable);
            ivSelected = v.findViewById(R.id.ivSelected);
            llSlots = v.findViewById(R.id.llSlots);

        }
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

}

