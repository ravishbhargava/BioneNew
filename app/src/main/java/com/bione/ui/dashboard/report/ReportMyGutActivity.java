package com.bione.ui.dashboard.report;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;

public class ReportMyGutActivity extends BaseActivity {

    private AppCompatTextView tvIntroduction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_mygut);

        init();
        setListener();
    }

    private void init() {

    }

    private void setListener() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
}
