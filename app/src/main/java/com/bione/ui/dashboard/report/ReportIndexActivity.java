package com.bione.ui.dashboard.report;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;

public class ReportIndexActivity extends BaseActivity {

    private AppCompatTextView tvIntroduction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_index);

        init();
        setListener();
    }

    private void init() {
        tvIntroduction = findViewById(R.id.tvIntroduction);
    }

    private void setListener() {
        tvIntroduction.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvIntroduction:

                break;
        }
    }
}
