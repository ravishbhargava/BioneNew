package com.bione.ui.dashboard.bottomFragments.report.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.BarCodeStatus;
import com.bione.model.customerOrders.KitOrder;
import com.bione.network.ApiError;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.dashboard.bottomFragments.report.ReportPdfViewActivity;
import com.bione.utils.CommonUtil;
import com.bione.utils.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<KitOrder> customerKits;


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
        holder.tvName.setText(customerKits.get(position).getFirstName() + customerKits.get(position).getLastName());
        holder.tvKitName.setText(customerKits.get(position).getKitName());
        holder.tvReportedDate.setText(CommonUtil.dateformat(customerKits.get(position).getCreatedAt()));
        holder.tvPurchaseDate.setText(CommonUtil.dateformat(customerKits.get(position).getSampleRegistrationDate()));

        if (customerKits.get(position).getBarCode().equals("")) {
//            holder.tvReport.setTextColor(mContext.getResources().getColor(R.color.gray));
            holder.tvDetail.setText("Bar Code: Not Available");
        } else {
//            holder.tvReport.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.tvDetail.setText("Bar Code: " + customerKits.get(position).getBarCode());

            if (customerKits.get(position).getSkuCode().equals("LP")) {
                holder.ivKit.setImageResource(R.drawable.lp);
            } else if (customerKits.get(position).getSkuCode().equals("LF")) {
                holder.ivKit.setImageResource(R.drawable.lf);
            } else if (customerKits.get(position).getSkuCode().equals("MM")) {
                holder.ivKit.setImageResource(R.drawable.mmb);
            } else {
                holder.ivKit.setImageResource(R.drawable.mmb);
            }

            holder.llMain.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    barCodeStatusAPI(customerKits.get(position).getBarCode(), "");
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
        private AppCompatImageView ivKit;
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
            ivKit = v.findViewById(R.id.ivKit);
            llMain = v.findViewById(R.id.llMain);
        }

    }

    public void barCodeStatusAPI(final String barcode, final String password) {


        JSONObject jsonObject = new JSONObject();
        try {
//            jsonObject.put("id", "MMBFTD1ZZZ84");
            jsonObject.put("id", barcode);
//            jsonObject.put("id", "MMFEA1ZZZ161");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body =
                RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        RestClient.getApiInterface4("https://mymicrobiome.bione.in/").barcodeStatus(body).enqueue(new ResponseResolver<BarCodeStatus>() {
            @Override
            public void onSuccess(BarCodeStatus commonResponse) {

                Log.d("onSuccess -----  ", "--");
                Log.d("getReportStatus -----  ", commonResponse.getReportStatus());
                Log.d("getReportUrl -----  ", commonResponse.getReportUrl());
                if (commonResponse.getReportStatus().equals("Approved")) {

//                }
//                if (commonResponse.getReportUrl() != null) {
                    Intent intent = new Intent(mContext, ReportPdfViewActivity.class);
                    intent.putExtra("pdfUrl", commonResponse.getReportUrl());
                    intent.putExtra("password", commonResponse.getPassword());
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Report in progress.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(ApiError error) {
                Log.d("onError", "" + error);
                Toast.makeText(mContext, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(mContext, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}


