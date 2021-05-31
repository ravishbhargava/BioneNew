package com.bione.ui.dashboard.orderfragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;

public class KitDetailActivity extends BaseActivity {

    private AppCompatImageView ivBack;
    private AppCompatTextView tvKitName;
    private AppCompatTextView tvKitStatus;
    private AppCompatTextView tvSampleId;
    private AppCompatTextView tvSampleStatus;

    private String kitName = "";
    private String kitStatus = "";
    private String sampleId = "";
    private String sampleStatus = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kit_orders_detail);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
//            newString= null;
        } else {
            kitName = extras.getString("kitName");
            kitStatus = extras.getString("kitStatus");
            sampleId = extras.getString("sampleId");
        }

        init();


    }

    private void init() {
        tvKitName = findViewById(R.id.tvKitName);
        tvKitStatus = findViewById(R.id.tvKitStatus);
        tvSampleId = findViewById(R.id.tvSampleId);
        tvSampleStatus = findViewById(R.id.tvSampleStatus);

        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivBack:
                finish();
                break;

            default:
                break;
        }
    }
}
