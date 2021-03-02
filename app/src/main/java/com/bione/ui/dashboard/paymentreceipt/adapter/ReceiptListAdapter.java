package com.bione.ui.dashboard.paymentreceipt.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.CrouselData;
import com.bione.model.paymentreceiptlist.Receipt;
import com.bione.ui.dashboard.paymentreceipt.PaymentReceiptActivity;
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

        public MyViewHolder(View v) {
            super(v);
            view = v;

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


        holder.tvTestName.setText(receiptArrayList.get(position).getTestName());
        holder.tvName.setText(receiptArrayList.get(position).getFirstName());
        holder.tvDate.setText(receiptArrayList.get(position).getBalanceAmountPaidDate());
        holder.tvNumber.setText("#" + receiptArrayList.get(position).getReceiptId());


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
