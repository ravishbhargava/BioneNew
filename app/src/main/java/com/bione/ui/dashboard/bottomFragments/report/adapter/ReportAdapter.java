package com.bione.ui.dashboard.bottomFragments.report.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.customerkit.KitOrder;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<com.bione.ui.dashboard.bottomFragments.report.adapter.ReportAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<KitOrder> customerKits;

    public ReportAdapter(final Context context) {
        this.mContext = context;
    }

    public ReportAdapter(final Context context, final ArrayList<KitOrder> customerKits) {
        this.mContext = context;
        this.customerKits = customerKits;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reports, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.tvOrderId.setText(customerKits.get(position).getId());
        holder.tvKitName.setText(customerKits.get(position).getKitName());

        if (customerKits.get(position).getBarCode().equals("")) {
//            holder.tvReport.setTextColor(mContext.getResources().getColor(R.color.gray));
            holder.tvDetail.setText("Bar Code: Not Available");
        } else {
//            holder.tvReport.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.tvDetail.setText("Bar Code: " + customerKits.get(position).getBarCode());
            holder.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (customerKits.get(position).getReportUrl() != null) {
                        if (!customerKits.get(position).getReportUrl().equals("")) {
//                            Intent intent = new Intent(mContext, ReportPDFViewActivity.class);
//                            intent.putExtra("barCode", customerKits.get(position).getBarCode());
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            mContext.startActivity(intent);
                        } else {
                            callDummy();
                        }
                    } else {
                        callDummy();
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return customerKits.size();
    }

    private void callDummy() {
        Toast.makeText(mContext, "Report in progress", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(mContext, ComingSoonActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra("head", "Report in PDF");
//        intent.putExtra("body", "Our experts are working on your test results. This option will get enabled automatically soon after you receive your report.\n");
//        intent.putExtra("link", "google.com");
//        mContext.startActivity(intent);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        //        private TextView tvOrderId;
//        private TextView tvReport;
        private AppCompatTextView tvName;
        private AppCompatTextView tvWhom;
        private AppCompatTextView tvDetail;
        private AppCompatTextView tvReportedDate;
        private AppCompatTextView tvPurchaseDate;
        private TextView tvKitName;
//        private TextView tvKitId;
//        private AppCompatImageView ivKit;
        private LinearLayout llMain;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            tvName = v.findViewById(R.id.tvName);
            tvWhom = v.findViewById(R.id.tvWhom);
            tvDetail = v.findViewById(R.id.tvDetail);
            tvKitName = v.findViewById(R.id.tvKitName);
            tvReportedDate = v.findViewById(R.id.tvReportedDate);
            tvPurchaseDate = v.findViewById(R.id.tvPurchaseDate);
//            tvKitId = v.findViewById(R.id.tvKitId);
//            ivKit = v.findViewById(R.id.ivKit);
            llMain = v.findViewById(R.id.llMain);
        }
    }

}