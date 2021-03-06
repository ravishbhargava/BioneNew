package com.bione.ui.dashboard.orderfragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.customerOrders.KitOrder;
import com.bione.ui.dashboard.orderfragment.KitDetailActivity;

import java.util.ArrayList;

public class KitListAdapter extends RecyclerView.Adapter<KitListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<KitOrder> customerKits;

    public KitListAdapter(final Context context) {
        this.mContext = context;
    }

    public KitListAdapter(final Context context, final ArrayList<KitOrder> customerKits) {
        this.mContext = context;
        this.customerKits = customerKits;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kit_order, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.tvOrderId.setText(customerKits.get(position).getId());
        holder.tvKitName.setText(customerKits.get(position).getKitName());
        holder.tvKitId.setText(customerKits.get(position).getBarCode());


        if ("1".equalsIgnoreCase(customerKits.get(position).getActivationStatus())) {
            holder.tvStatus.setText("Activated");
            holder.tvSecondStatus.setText("Registered");
            holder.tvStatus.setTextColor(R.color.activated_status_color);
            holder.tvActivatedBy.setText(customerKits.get(position).getFirstName());
        } else {
            holder.tvStatus.setText("Pending");
            holder.tvSecondStatus.setText("Register");
            holder.tvStatus.setTextColor(R.color.pending_status_color);
            holder.tvActivatedBy.setText("-");
        }

        if (customerKits.get(position).getSkuCode().equals("LP")) {
            holder.ivKit.setImageResource(R.drawable.lp);
        } else if (customerKits.get(position).getSkuCode().equals("LF")) {
            holder.ivKit.setImageResource(R.drawable.lf);
        } else if (customerKits.get(position).getSkuCode().equals("MM")) {
            holder.ivKit.setImageResource(R.drawable.mmb);
        } else {
            holder.ivKit.setImageResource(R.drawable.mmb);
        }

//        if ("1".equalsIgnoreCase(customerKits.get(position).getReportUrl())) {
//            holder.tvSecondStatus.setText("Registered");
//        } else {
//            holder.tvSecondStatus.setText("Register");
//        }

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, KitDetailActivity.class);
                intent.putExtra("kitName", customerKits.get(position).getKitName());
                intent.putExtra("kiStatus", customerKits.get(position).getActivationStatus());
                intent.putExtra("sampleId", customerKits.get(position).getBarCode());
                intent.putExtra("data",  (KitOrder)customerKits.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return customerKits.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;

        private AppCompatImageView ivKit;
        private AppCompatTextView tvKitId;
        private AppCompatTextView tvKitName;
        private AppCompatTextView tvStatus;
        private AppCompatTextView tvSecondStatus;
        private AppCompatTextView tvActivatedBy;
        private LinearLayout llMain;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            ivKit = v.findViewById(R.id.ivKit);
            tvKitId = v.findViewById(R.id.tvKitId);
            tvStatus = v.findViewById(R.id.tvStatus);
            tvSecondStatus = v.findViewById(R.id.tvSecondStatus);
            tvKitName = v.findViewById(R.id.tvKitName);
            tvActivatedBy = v.findViewById(R.id.tvActivatedBy);
            llMain = v.findViewById(R.id.llMain);
        }
    }

}
