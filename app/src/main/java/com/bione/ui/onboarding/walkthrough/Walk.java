package com.bione.ui.onboarding.walkthrough;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.onboarding.Login;
import com.bione.utils.CustomViewPager;

import me.relex.circleindicator.CircleIndicator;

public class Walk extends BaseActivity {

    private CustomViewPager viewPager;
    private AppCompatTextView tvGetStarted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);


        init();
        setListeners();

        initViewPager();
    }

    private void init() {
        tvGetStarted = (AppCompatTextView) findViewById(R.id.tvContinue);
    }

    private void setListeners() {
        tvGetStarted.setOnClickListener(this);
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPagingEnabled(true);


        // setting viewPager's pages
        final WalkPagerAdapter adapter = new WalkPagerAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvContinue:
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                break;
        }
    }
}
