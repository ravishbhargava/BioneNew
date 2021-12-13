package com.bione.ui.dashboard.ecommerce.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.SelectedProductData;

import java.util.List;

public class SelectedProductsAdapter extends RecyclerView.Adapter<SelectedProductsAdapter.SelectViewHolder> {

    private List<SelectedProductData> selectedProductDataList;

    // Constructor
    public SelectedProductsAdapter(List<SelectedProductData> selectedProductDataList) {
        this.selectedProductDataList = selectedProductDataList;

    }

    @NonNull
    @Override
    public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Here we inflate the corresponding// layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_selected_product, viewGroup, false);

        return new SelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectViewHolder optionViewHolder, int position) {


        optionViewHolder.tvKitName.setText("" + selectedProductDataList.get(position).getKitName());
        optionViewHolder.tvPrice.setText("" + selectedProductDataList.get(position).getTotalPrice());
        optionViewHolder.tvCount.setText("" + selectedProductDataList.get(position).getCount());

        optionViewHolder.tvPlus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                int count = selectedProductDataList.get(position).getCount();
                int price = selectedProductDataList.get(position).getPrice();
                int totalPrice = selectedProductDataList.get(position).getTotalPrice();
                count = count + 1;
                totalPrice = totalPrice + price;
                selectedProductDataList.get(position).setCount(count);
                selectedProductDataList.get(position).setTotalPrice(totalPrice);
                notifyItemChanged(position);
            }
        });
        optionViewHolder.tvMinus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                int count = selectedProductDataList.get(position).getCount();
                int price = selectedProductDataList.get(position).getPrice();
                int totalPrice = selectedProductDataList.get(position).getTotalPrice();
                if (count != 0) {
                    count = count - 1;
                    totalPrice = totalPrice - price;
                    selectedProductDataList.get(position).setCount(count);
                    selectedProductDataList.get(position).setTotalPrice(totalPrice);
                    notifyItemChanged(position);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return selectedProductDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // This class is to initialize// the Views present// in the child RecyclerView
    class SelectViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvKitName;
        private AppCompatTextView tvPrice;
        private AppCompatTextView tvPlus;
        private AppCompatTextView tvCount;
        private AppCompatTextView tvMinus;
        private LinearLayout root;

        SelectViewHolder(View itemView) {
            super(itemView);
            tvKitName = itemView.findViewById(R.id.tvKitName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvPlus = itemView.findViewById(R.id.tvPlus);
            tvCount = itemView.findViewById(R.id.tvCount);
            tvMinus = itemView.findViewById(R.id.tvMinus);
            root = itemView.findViewById(R.id.root);
        }
    }

}