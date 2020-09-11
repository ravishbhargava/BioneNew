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
import com.bione.utils.CommonUtil;
import com.bione.utils.Log;

import java.util.ArrayList;

public class CounsellorAdapter extends RecyclerView.Adapter<CounsellorAdapter.MyViewHolder> {

    private String type = "Upcoming";
    private Context mContext;
    private ArrayList<ListItem> counsellorList;


    public CounsellorAdapter(final String type) {
        this.type = type;
    }

    public CounsellorAdapter(final Context mContext, final String type, final ArrayList<ListItem> counsellorList) {
        this.type = type;
        this.mContext = mContext;
        this.counsellorList = counsellorList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        private ImageView ivCall;
        private TextView tvName;
        private TextView tvStatus;
        private TextView tvType;
        private TextView tvDate;
        private TextView tvDays;
        private TextView tvTimeSlot;
//        private View bottomView;
        private AppCompatRatingBar ratingBar;
        private RelativeLayout bottomRel;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            ivCall = v.findViewById(R.id.ivCall);
            tvName = v.findViewById(R.id.tvName);
            tvDays = v.findViewById(R.id.tvDays);
            tvDate = v.findViewById(R.id.tvDate);
            tvType = v.findViewById(R.id.tvType);
            tvTimeSlot = v.findViewById(R.id.tvTimeSlot);
            tvStatus = v.findViewById(R.id.tvStatus);
            ratingBar = v.findViewById(R.id.ratingBar);
            bottomRel = v.findViewById(R.id.bottomRel);
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

        if (counsellorList.get(position).getStarsRatings() == null) {
            holder.ratingBar.setVisibility(View.GONE);
        } else {
            String rating = counsellorList.get(position).getStarsRatings();
            holder.ratingBar.setVisibility(View.VISIBLE);
            holder.ratingBar.setRating(Float.parseFloat(rating));
        }

        if (counsellorList.get(position).getStatus().equals("0")) {
            holder.tvStatus.setText("Pending");
        } else {
            holder.tvStatus.setText("Completed");
        }


        if (type.equals("Upcoming")) {
            holder.bottomRel.setVisibility(View.GONE);
//            holder.bottomView.setVisibility(View.GONE);
//            holder.view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent callIntent = new Intent(Intent.ACTION_CALL);
//                    callIntent.setData(Uri.parse("tel:" + "9876543210"));//change the number
//                    if (checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PERMISSION_GRANTED) {
//                        mContext.startActivity(callIntent);
//                    } else {
//                        // TODO: Consider calling
//                        //    Activity#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for Activity#requestPermissions for more details.
//                        return;
//                    }
//                }
//            });
        } else {
            holder.bottomRel.setVisibility(View.VISIBLE);
//            holder.bottomView.setVisibility(View.VISIBLE);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailCounsellorActivity.class);
                    intent.putExtra("counsellor", counsellorList.get(position));
                    mContext.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        Log.d("item count", " ::: " + counsellorList.size());
        return counsellorList.size();
    }

    //    public ArrayList<ListItem> getCounsellorList() {
//        return counsellorList;
//    }
//
    public void setList(final ArrayList<ListItem> counseloeList) {
        this.counsellorList = counsellorList;
        notifyDataSetChanged();
    }


}
