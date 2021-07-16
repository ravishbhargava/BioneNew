package com.bione.ui.dashboard.kitRegistration;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;

public class KitRegisterActivity extends BaseActivity {


    private Context mContext;
    private View rootView;

    private LinearLayout secondLayout;
    private RelativeLayout imageLayout;
    private AppCompatTextView tvError;
    private AppCompatTextView tvContinue;
    private AppCompatCheckBox cbAccept;

    private boolean isClicked = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register_kit);

        init();
        setListeners();
    }

    private void init() {
        secondLayout = findViewById(R.id.secondLayout);
        imageLayout = findViewById(R.id.imageLayout);
        tvContinue = findViewById(R.id.tvContinue);
        tvError = findViewById(R.id.tvError);
        cbAccept = findViewById(R.id.cbAccept);

    }

    private void setListeners() {
        tvContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvContinue:
                if (isClicked) {
                    cbAccept.setVisibility(View.GONE);
                    imageLayout.setVisibility(View.GONE);
                    tvError.setVisibility(View.GONE);
                    secondLayout.setVisibility(View.VISIBLE);
                    isClicked = false;
                } else {
                    cbAccept.setVisibility(View.VISIBLE);
                    imageLayout.setVisibility(View.VISIBLE);
                    tvError.setVisibility(View.VISIBLE);
                    secondLayout.setVisibility(View.GONE);
                    isClicked = true;
                }
                break;

        }
    }
}
