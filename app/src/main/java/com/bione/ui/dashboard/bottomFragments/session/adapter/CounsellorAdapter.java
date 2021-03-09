package com.bione.ui.dashboard.bottomFragments.session.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.counsellors.ListItem;
import com.bione.ui.dashboard.bottomFragments.session.OnclickItemCounsellor;
import com.bione.utils.Log;

import java.util.ArrayList;

//import com.bione.ui.dashboard.bottomFragments.session.DetailCounsellorActivity;
//import com.bione.ui.dashboard.bottomFragments.session.ScheduleNow;

public class CounsellorAdapter extends RecyclerView.Adapter<CounsellorAdapter.MyViewHolder> {

    private String type = "Upcoming";
    private Context mContext;
    private ArrayList<ListItem> counsellorList;
    private OnclickItemCounsellor listener;


    public CounsellorAdapter(final Context mContext, final String type, final ArrayList<ListItem> counsellorList, final OnclickItemCounsellor listener) {
        this.type = type;
        this.mContext = mContext;
        this.counsellorList = counsellorList;
        this.listener = listener;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        private AppCompatImageView ivNext;
        private AppCompatTextView tvName;
        private AppCompatTextView tvStatus;
        private AppCompatTextView tvType;
        private AppCompatTextView tvDate;
        private AppCompatTextView tvReschedule;
        private AppCompatTextView tvCancel;
        private LinearLayout layoutCancel;


        public MyViewHolder(View v) {
            super(v);
            view = v;
            ivNext = v.findViewById(R.id.ivNext);
            tvName = v.findViewById(R.id.tvName);
            tvDate = v.findViewById(R.id.tvDate);
            tvType = v.findViewById(R.id.tvType);
            tvCancel = v.findViewById(R.id.tvCancel);
            tvReschedule = v.findViewById(R.id.tvReschedule);
            tvStatus = v.findViewById(R.id.tvStatus);
            layoutCancel = v.findViewById(R.id.layoutCancel);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_upcoming, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.tvType.setText(counsellorList.get(position).getGeneticType());
        holder.tvName.setText(counsellorList.get(position).getCustomerName());
//        holder.tvDate.setText(CommonUtil.getDayMonth(counsellorList.get(position).getDate()));
        holder.tvDate.setText(counsellorList.get(position).getTimeSlot());

//        holder.ratingBar.setIsIndicator(true);
//        if (counsellorList.get(position).getStarsRatings() == null) {
//            holder.ratingBar.setVisibility(View.GONE);
//        } else {
//            String rating = counsellorList.get(position).getStarsRatings();
//            holder.ratingBar.setVisibility(View.VISIBLE);
//            holder.ratingBar.setRating(Float.parseFloat(rating));
//        }

        if (counsellorList.get(position).getStatus().equals("0")) {
            holder.tvStatus.setText("Pending");
//            holder.tvReason.setVisibility(View.GONE);
        } else if (counsellorList.get(position).getStatus().equals("1")) {
            holder.tvStatus.setText("Completed");
//            holder.tvReason.setVisibility(View.GONE);
        } else {
            holder.tvStatus.setText("Cancelled");
//            holder.tvReason.setVisibility(View.VISIBLE);
//            holder.tvReason.setText(counsellorList.get(position).getReasonCancelation());
        }

        if (type.equals("Upcoming")) {
            holder.layoutCancel.setVisibility(View.VISIBLE);
//            holder.bottomRel.setVisibility(View.GONE);
        } else {
            holder.layoutCancel.setVisibility(View.GONE);
//            holder.bottomRel.setVisibility(View.VISIBLE);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!type.equals("Upcoming")) {
//                    Intent intent = new Intent(mContext, DetailCounsellorActivity.class);
//                    intent.putExtra("counsellor", counsellorList.get(position));
//                    mContext.startActivity(intent);
                }
            }
        });
//
//        holder.ivCall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (type.equals("Upcoming")) {
//                    listener.onItemClick(position, "call");
//                }
//            }
//        });
//
        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("Upcoming")) {
                    listener.onItemClick(position, "cancel");
                }
            }
        });

        holder.tvReschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("Upcoming")) {
                    Log.d("geneticType","----"+counsellorList.get(position).getGeneticType());
//                    Intent intent = new Intent(mContext, ScheduleNow.class);
//                    intent.putExtra("bookingId", counsellorList.get(position).getMobilecounsellingId());
//                    intent.putExtra("geneticType", counsellorList.get(position).getGeneticType());
//                    intent.putExtra("counsellorName", counsellorList.get(position).getCounsellorName());
//                    mContext.startActivity(intent);

                }
            }
        });
    }


    @Override
    public int getItemCount() {
        Log.d("item count", " ::: " + counsellorList.size());
        return counsellorList.size();
    }

    public void refreshEvents(ArrayList<ListItem> counsellorList) {

        this.counsellorList.clear();
        this.counsellorList.addAll(counsellorList);
        notifyDataSetChanged();
    }

}
