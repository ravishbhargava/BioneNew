package com.bione.ui.Counselling.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.counsellors.ListItem;
import com.bione.ui.Counselling.DetailCounsellorActivity;
import com.bione.ui.Counselling.OnclickItemCounsellor;
import com.bione.ui.schedulecall.ScheduleNow;
import com.bione.utils.CommonUtil;
import com.bione.utils.Log;

import java.util.ArrayList;

public class CounsellorAdapter extends RecyclerView.Adapter<CounsellorAdapter.MyViewHolder> {

    private String type = "Upcoming";
    private Context mContext;
    private ArrayList<ListItem> counsellorList;
    private OnclickItemCounsellor listener;


    public CounsellorAdapter(final String type) {
        this.type = type;
    }

    public CounsellorAdapter(final Context mContext, final String type, final ArrayList<ListItem> counsellorList, final OnclickItemCounsellor listener) {
        this.type = type;
        this.mContext = mContext;
        this.counsellorList = counsellorList;
        this.listener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        private ImageView ivCall;
        private TextView tvName;
        private TextView tvStatus;
        private TextView tvType;
        private TextView tvDate;
        private TextView tvReason;
        private TextView tvTimeSlot;
        private TextView tvReschedule;
        private TextView tvCancel;
        //        private View bottomView;
        private AppCompatRatingBar ratingBar;
        private RelativeLayout bottomRel;
        private RelativeLayout bottomRel2;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            ivCall = v.findViewById(R.id.ivCall);
            tvName = v.findViewById(R.id.tvName);
            tvReason = v.findViewById(R.id.tvReason);
            tvDate = v.findViewById(R.id.tvDate);
            tvType = v.findViewById(R.id.tvType);
            tvCancel = v.findViewById(R.id.tvCancel);
            tvReschedule = v.findViewById(R.id.tvReschedule);
            tvTimeSlot = v.findViewById(R.id.tvTimeSlot);
            tvStatus = v.findViewById(R.id.tvStatus);
            ratingBar = v.findViewById(R.id.ratingBar);
            bottomRel = v.findViewById(R.id.bottomRel);
            bottomRel2 = v.findViewById(R.id.bottomRel2);
//            bottomView = v.findViewById(R.id.bottomView);
        }
    }

    @NonNull
    @Override
    public CounsellorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_counselling, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.tvType.setText(counsellorList.get(position).getGeneticType());
        holder.tvName.setText(counsellorList.get(position).getCustomerName());
        holder.tvDate.setText(CommonUtil.getDayMonth(counsellorList.get(position).getDate()));
        holder.tvTimeSlot.setText(counsellorList.get(position).getTimeSlot());
        holder.ratingBar.setIsIndicator(true);
        if (counsellorList.get(position).getStarsRatings() == null) {
            holder.ratingBar.setVisibility(View.GONE);
        } else {
            String rating = counsellorList.get(position).getStarsRatings();
            holder.ratingBar.setVisibility(View.VISIBLE);
            holder.ratingBar.setRating(Float.parseFloat(rating));
        }

        if (counsellorList.get(position).getStatus().equals("0")) {
            holder.tvStatus.setText("Pending");
        } else if (counsellorList.get(position).getStatus().equals("1")) {
            holder.tvStatus.setText("Completed");
        } else {
            holder.tvStatus.setText("Cancelled");
            holder.tvReason.setVisibility(View.VISIBLE);
            holder.tvReason.setText(counsellorList.get(position).getReasonCancelation());
        }


        if (type.equals("Upcoming")) {
            holder.bottomRel2.setVisibility(View.VISIBLE);
            holder.bottomRel.setVisibility(View.GONE);
        } else {
            holder.bottomRel2.setVisibility(View.GONE);
            holder.bottomRel.setVisibility(View.VISIBLE);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!type.equals("Upcoming")) {
                    Intent intent = new Intent(mContext, DetailCounsellorActivity.class);
                    intent.putExtra("counsellor", counsellorList.get(position));
                    mContext.startActivity(intent);
                }
            }
        });

        holder.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("Upcoming")) {
                    listener.onItemClick(position, "call");
                }
            }
        });

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
                    Intent intent = new Intent(mContext, ScheduleNow.class);
                    intent.putExtra("bookingId", counsellorList.get(position).getMobilecounsellingId());
                    intent.putExtra("geneticType", counsellorList.get(position).getGeneticType());
                    intent.putExtra("counsellorName", counsellorList.get(position).getCounsellorName());
                    mContext.startActivity(intent);

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
