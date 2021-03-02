package com.bione.ui.walkthrough;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;


import com.bione.R;

import com.bione.db.CommonData;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.dashboard.MainActivity;
import com.bione.ui.onboarding.LoginActivity;
import com.bione.utils.CustomViewPager;

public class WalkActivity extends BaseActivity {

    private CustomViewPager viewPager;
    private AppCompatTextView tvSkip;
    private AppCompatImageView ivNext;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        initViewPager();
        tvSkip = findViewById(R.id.tvSkip);
        ivNext = findViewById(R.id.ivNext);

        tvSkip.setOnClickListener(this);
        ivNext.setOnClickListener(this);
    }


    private void initViewPager() {
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPagingEnabled(true);


        // setting viewPager's pages
        final WalkPagerAdapter adapter = new WalkPagerAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(33);//initially progress is 0

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPosition();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSkip:
                nextScreen();

                break;

            case R.id.ivNext:
                if (viewPager.getCurrentItem() != 2) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    nextScreen();
                }
                break;
        }
    }

    private void setPosition() {
        if (viewPager.getCurrentItem() == 0) {
            progressBar.setProgress(33);//initially progress is 0
        } else if (viewPager.getCurrentItem() == 1) {
            progressBar.setProgress(66);//initially progress is 0
        } else {
            progressBar.setProgress(100);//initially progress is 0
        }
    }

    private void nextScreen() {
//        Intent intent = new Intent(WalkActivity.this, LoginActivity.class);
//        startActivity(intent);
        CommonData.updateGuest(true);
        Intent intent = new Intent(WalkActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
