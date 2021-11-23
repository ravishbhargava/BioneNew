package com.bione.ui.dashboard.kitRegistration;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;

import static com.bione.utils.CommonUtil.makeTextViewResizable;

public class ResearchConsent extends BaseActivity {


    private LinearLayout firstLayout;
    private LinearLayout secondLayout;
    private AppCompatTextView tvLongText;
    private AppCompatTextView tvContinue;

    private boolean isClicked = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research_consent);

        init();
        setListeners();

        makeTextViewResizable(tvLongText, 5, "See More", true);
    }

    private void init() {
        firstLayout = findViewById(R.id.firstLayout);
        secondLayout = findViewById(R.id.secondLayout);
        tvLongText = findViewById(R.id.tvLongText);
        tvContinue = findViewById(R.id.tvContinue);
    }

    private void setListeners() {
        tvContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvContinue:
                if (isClicked) {

                    secondLayout.setVisibility(View.VISIBLE);
                    firstLayout.setVisibility(View.GONE);
                    isClicked = false;
                } else {
                    secondLayout.setVisibility(View.GONE);
                    firstLayout.setVisibility(View.VISIBLE);
                    isClicked = true;
                }
                break;
        }
    }
}
