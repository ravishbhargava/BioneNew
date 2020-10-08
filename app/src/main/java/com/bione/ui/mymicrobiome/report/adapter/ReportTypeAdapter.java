package com.bione.ui.mymicrobiome.report.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.CommonListData;
import com.bione.ui.mymicrobiome.ComingSoonActivity;
import com.bione.ui.mymicrobiome.report.MyReportActivity;

import java.util.ArrayList;

public class ReportTypeAdapter extends RecyclerView.Adapter<ReportTypeAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<CommonListData> reportType;

    public ReportTypeAdapter(final Context context) {
        this.mContext = context;
    }

    public ReportTypeAdapter(final Context context, final ArrayList<CommonListData> reportType) {
        this.mContext = context;
        this.reportType = reportType;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reports_type, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvHeading.setText(reportType.get(position).getHeading());
        holder.ivLeft.setImageResource(reportType.get(position).getDrawable());


        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==1) {
                    Intent intent = new Intent(mContext, MyReportActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext, ComingSoonActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("head","Report in Details");
                    intent.putExtra("body","Coming Soon");
                    intent.putExtra("link","google.com");
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return reportType.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        private TextView tvHeading;
        private AppCompatImageView ivLeft;
        private AppCompatImageView ivRight;
        private RelativeLayout llMain;

        public MyViewHolder(View v) {
            super(v);
            view = v;

            tvHeading = v.findViewById(R.id.tvHeading);
            ivLeft = v.findViewById(R.id.ivLeft);
            ivRight = v.findViewById(R.id.ivRight);
            llMain = v.findViewById(R.id.llMain);
        }
    }

}
