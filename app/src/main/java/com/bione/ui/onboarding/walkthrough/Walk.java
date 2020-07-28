package com.bione.ui.onboarding.walkthrough;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bione.R;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.CustomViewPager;

public class Walk extends BaseActivity {

    private CustomViewPager viewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        initViewPager();
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPagingEnabled(true);


        // setting viewPager's pages
        final WalkPagerAdapter adapter = new WalkPagerAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setScrollIndicators(View.SCROLL_INDICATOR_BOTTOM);
    }
}
