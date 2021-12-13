package com.bione.ui.dashboard.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.SelectedProductData;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.dashboard.ecommerce.adapter.SelectedProductsAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectedProductFragment extends BaseActivity {


    private AppCompatTextView tvCheckout;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_selected_product);

        tvCheckout = findViewById(R.id.tvCheckout);
        tvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectedProductFragment.this, AddAddressActivity.class);
                startActivity(intent);
            }
        });

        setRecyclerView();
    }

    @Override
    public void onClick(View view) {

    }

    private void setRecyclerView() {
        List<SelectedProductData> selectedProductDataArrayList = new ArrayList<>();
        selectedProductDataArrayList.add(new SelectedProductData(2999,2999, "Longifit", 1));
        selectedProductDataArrayList.add(new SelectedProductData(5999,5999, "MyMicroBiome", 1));

        SelectedProductsAdapter adapter = new SelectedProductsAdapter(selectedProductDataArrayList);
//        ParentRecyclerViewAdapter adapter = new ParentRecyclerViewAdapter(mContext, datumArrayList);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize(selectedProductDataArrayList.size());
        recyclerView.setAdapter(adapter);

    }
}
