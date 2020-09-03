package com.bione.ui.schedulecall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    private Context mContext;

    public SlotsAdapter(final Context mContext, final ArrayList<Slots> slots, final ScheduleNow.OnItemClickListener listener) {

        this.slots = slots;
        this.listener = listener;
        this.mContext = mContext;
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

        if (slots.get(position).getText().equals("NOT AVAILABLE")) {
            holder.tvNotAvailable.setText(slots.get(position).getText());
            holder.tvNotAvailable.setTextColor(mContext.getResources().getColor(R.color.not_available_color));
        } else {
            holder.tvNotAvailable.setText(slots.get(position).getText());
            holder.tvNotAvailable.setTextColor(mContext.getResources().getColor(R.color.available_color));

            if (checkedPosition == position) {
//                    holder.ivSelected.setVisibility(View.VISIBLE);
                holder.relRow.setBackgroundResource(R.drawable.drawable_border_selected_transparent);
            } else {
//                    holder.ivSelected.setVisibility(View.GONE);
                holder.relRow.setBackgroundResource(R.drawable.drawable_border_transparent);
            }
        }
        holder.llSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!slots.get(position).getText().equals("NOT AVAILABLE")) {
                    if (!slots.get(position).isSelected()) {
                        holder.relRow.setBackgroundResource(R.drawable.drawable_border_selected_transparent);

                        if (checkedPosition != position) {
                            notifyItemChanged(checkedPosition);
                            listener.onItemClick(slots.get(position).name);
                            checkedPosition = position;
                        }
                    }
                }
            }
        });

    }

//    @SuppressLint("ResourceAsColor")
//    @Override
//    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//
//        holder.tvSlotTime.setText(slots.get(position).name);
//        if (slots.get(position).isSelected()) {
////            holder.tvNotAvailable.setVisibility(View.VISIBLE);
//            holder.tvNotAvailable.setText("NOT AVAILABLE");
//            holder.tvNotAvailable.setTextColor(R.color.not_available_color);
////            holder.ivSelected.setVisibility(View.GONE);
//            holder.relRow.setBackgroundResource(R.drawable.drawable_border_transparent);
//
//        } else {
////            holder.tvNotAvailable.setVisibility(View.GONE);
//            holder.tvNotAvailable.setText("AVAILABLE");
//            holder.tvNotAvailable.setTextColor(R.color.available_color);
////            holder.ivSelected.setVisibility(View.GONE);
//            holder.relRow.setBackgroundResource(R.drawable.drawable_border_transparent);
//
//            if (checkedPosition == -1) {
////                holder.ivSelected.setVisibility(View.GONE);
//                holder.relRow.setBackgroundResource(R.drawable.drawable_border_transparent);
//            } else {
//                if (checkedPosition == position) {
////                    holder.ivSelected.setVisibility(View.VISIBLE);
//                    holder.relRow.setBackgroundResource(R.drawable.drawable_border_selected_transparent);
//                } else {
////                    holder.ivSelected.setVisibility(View.GONE);
//                    holder.relRow.setBackgroundResource(R.drawable.drawable_border_transparent);
//                }
//            }
//            holder.llSlots.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (!slots.get(position).isSelected()) {
////                        holder.ivSelected.setVisibility(View.VISIBLE);
//                        holder.relRow.setBackgroundResource(R.drawable.drawable_border_selected_transparent);
//
//                        if (checkedPosition != position) {
//                            notifyItemChanged(checkedPosition);
//                            listener.onItemClick(slots.get(position).name);
//                            checkedPosition = position;
//                        }
//                    }
//                }
//            });
//        }
//    }


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
        private RelativeLayout relRow;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            tvSlotTime = v.findViewById(R.id.tvSlotTime);
            tvNotAvailable = v.findViewById(R.id.tvNotAvailable);
            ivSelected = v.findViewById(R.id.ivSelected);
            llSlots = v.findViewById(R.id.llSlots);
            relRow = v.findViewById(R.id.relRow);

        }
    }


    public void refreshEvents(ArrayList<Slots> slots) {
        checkedPosition = -1;
        this.slots.clear();
        this.slots.addAll(slots);
        notifyDataSetChanged();
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

}

