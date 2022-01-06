package com.bione.ui.dashboard.report;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;

public class ReportFoodDetailActivity extends BaseActivity {

    private AppCompatTextView tvHeading;
    private AppCompatTextView tvDetail;
    private String heading = "";
    private String detail = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_food_detail);
        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            heading = extras.getString("heading");
            detail = extras.getString("detail");
            // and get whatever type user account id is
        }
        init();
        setListener();
    }

    private void init() {
        tvHeading = findViewById(R.id.tvHeading);
        tvDetail = findViewById(R.id.tvDetail);

        tvHeading.setText(Html.fromHtml(heading));
        tvDetail.setText(Html.fromHtml(detail));
    }

    private void setListener() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


}
