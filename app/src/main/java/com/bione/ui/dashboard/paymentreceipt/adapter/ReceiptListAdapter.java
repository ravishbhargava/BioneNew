package com.bione.ui.dashboard.paymentreceipt.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.paymentreceiptlist.Receipt;
import com.bione.ui.dashboard.paymentreceipt.PaymentReceiptViewActivity;
import com.bione.utils.Log;

import java.util.ArrayList;


public class ReceiptListAdapter extends RecyclerView.Adapter<ReceiptListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Receipt> receiptArrayList;


    public ReceiptListAdapter(final Context mContext, final ArrayList<Receipt> receiptArrayList) {
        this.receiptArrayList = receiptArrayList;
        this.mContext = mContext;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public LinearLayout llVisible;
        public AppCompatTextView tvNumber;
        public AppCompatTextView tvDate;
        public AppCompatTextView tvName;
        public AppCompatTextView tvTestName;
        public AppCompatTextView tvAmount;
        public AppCompatTextView tvBalanceAmount;
        public AppCompatTextView tvStatus;

        public MyViewHolder(View v) {
            super(v);
            view = v;

            tvBalanceAmount = v.findViewById(R.id.tvBalanceAmount);
            tvStatus = v.findViewById(R.id.tvStatus);
            tvAmount = v.findViewById(R.id.tvAmount);
            tvNumber = v.findViewById(R.id.tvNumber);
            tvDate = v.findViewById(R.id.tvDate);
            tvName = v.findViewById(R.id.tvName);
            tvTestName = v.findViewById(R.id.tvTestName);
            llVisible = v.findViewById(R.id.llVisible);

        }
    }

    @NonNull
    @Override
    public ReceiptListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment_receipt, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        Receipt receipt = receiptArrayList.get(position);

//        holder.tvAmount.setText("\u20B9" + receipt.getPaidAmount());
        holder.tvTestName.setText(receipt.getTestName());
        holder.tvName.setText(receipt.getFirstName());
        holder.tvDate.setText(receipt.getCreatedAt());
        holder.tvNumber.setText("#" + receipt.getReceiptId());

        if (receipt.getBalanceAmount().trim().equals("") || receipt.getBalanceAmount().trim().equals("0")) {
            holder.tvAmount.setText("\u20B9" + receipt.getPaidAmount());
            holder.tvBalanceAmount.setText("Paid Amount");
            holder.tvStatus.setText("Received");
            holder.tvStatus.setBackground(mContext.getResources().getDrawable(R.drawable.background_received));
        } else {
            holder.tvAmount.setText("\u20B9" + receipt.getBalanceAmount());
            holder.tvBalanceAmount.setText("Balance Amount");
            holder.tvStatus.setText("Pending");
            holder.tvStatus.setBackground(mContext.getResources().getDrawable(R.drawable.background_pending));
        }

        holder.llVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = "";
                if (receiptArrayList.get(position).getReceiptUrl() != null) {
                    link = "https://lims.bione.in/pdfreceipts/" + receiptArrayList.get(position).getReceiptUrl();
//                link = link.replaceAll("\\/", "/");
                    Log.d("link", "after slash removed------ " + link);

                    Intent intent = new Intent(mContext, PaymentReceiptViewActivity.class);
                    intent.putExtra("link", link);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Unexpected error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return receiptArrayList.size();
    }


}
