package com.bione.ui.dashboard.orderfragment.adapter;

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
import com.bione.model.customerOrders.Item;
import com.bione.model.customerOrders.MagentoOrder;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<MagentoOrder> customerKits;

    public OrderListAdapter(final Context context) {
        this.mContext = context;
    }

    public OrderListAdapter(final Context context, final ArrayList<MagentoOrder> customerKits) {
        this.mContext = context;
        this.customerKits = customerKits;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_list, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvName.setText(customerKits.get(position).getCustomerFirstname() + customerKits.get(position).getCustomerLastname());
        holder.tvStatus.setText(customerKits.get(position).getStatus());

        holder.tvMagentoId.setText(customerKits.get(position).getIncrementId());

        holder.tvTotal.setText("" + customerKits.get(position).getBaseGrandTotal());
        holder.tvOrderedDate.setText("" + customerKits.get(position).getCreatedAt());


        List<Item> item = customerKits.get(position).getItems();
        String items = "";
        for (int i = 0; i < item.size(); i++) {
            if (i == 0) {
                items = items + "" + item.get(i).getSku();
//                items = items + "" + item.get(i).getSku() + "*" + item.get(i).getQty();
            } else {
                items = items + ",\n" + item.get(i).getSku();
//                items = items + ",\n" + item.get(i).getSku() + "*" + item.get(i).getQty();
            }
        }
        holder.tvKitName.setText("" + items);
//        if (customerKits.get(position).getSkuCode().equals("LP")) {
//            holder.ivKit.setImageResource(R.drawable.lp);
//        } else if (customerKits.get(position).getSkuCode().equals("LF")) {
//            holder.ivKit.setImageResource(R.drawable.lf);
//        } else if (customerKits.get(position).getSkuCode().equals("MM")) {
//            holder.ivKit.setImageResource(R.drawable.mmb);
//        } else {
//            holder.ivKit.setImageResource(R.drawable.mmb);
//        }

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
        private AppCompatTextView tvStatus;
        private AppCompatTextView tvMagentoId;
        private AppCompatTextView tvTotal;
        private AppCompatTextView tvOrderedDate;
        private TextView tvKitName;
        //        private TextView tvKitId;
//        private AppCompatImageView ivKit;
        private LinearLayout llMain;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            tvName = v.findViewById(R.id.tvName);
            tvStatus = v.findViewById(R.id.tvStatus);
            tvMagentoId = v.findViewById(R.id.tvMagentoId);
            tvKitName = v.findViewById(R.id.tvKitName);
            tvTotal = v.findViewById(R.id.tvTotal);
            tvOrderedDate = v.findViewById(R.id.tvOrderedDate);
//            tvKitId = v.findViewById(R.id.tvKitId);
//            ivKit = v.findViewById(R.id.ivKit);
            llMain = v.findViewById(R.id.llMain);
        }
    }

}
