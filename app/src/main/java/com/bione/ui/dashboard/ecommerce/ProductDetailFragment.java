package com.bione.ui.dashboard.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;

public class ProductDetailFragment extends BaseActivity {

    private AppCompatImageView ivKit;
    private AppCompatTextView tvBuyNow;
    private AppCompatTextView tvKitName;
    private AppCompatTextView tvPrice;
    private AppCompatTextView tvDescription;
    private LinearLayout llDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_product_detail);


        init();
        setListener();
    }

    private void init() {
        ivKit = findViewById(R.id.ivKit);
        tvKitName = findViewById(R.id.tvKitName);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        tvBuyNow = findViewById(R.id.tvBuyNow);
        llDescription = findViewById(R.id.llDescription);
    }

    private void setListener() {
        tvBuyNow.setOnClickListener(this);
        llDescription.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llDescription:
                if (tvDescription.getVisibility() == View.VISIBLE) {
                    tvDescription.setVisibility(View.GONE);
                } else {
                    tvDescription.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.tvBuyNow:
                Intent intent = new Intent(ProductDetailFragment.this, SelectedProductFragment.class);
                startActivity(intent);
                break;

            default:
                break;

        }
    }


}
