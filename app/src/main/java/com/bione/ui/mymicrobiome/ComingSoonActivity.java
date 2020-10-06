package com.bione.ui.mymicrobiome;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.home.dashboard.banner.BannerPagerAdapter;
import com.bione.utils.CustomViewPager;

import me.relex.circleindicator.CircleIndicator;

public class ComingSoonActivity extends BaseActivity {

    private BannerPagerAdapter bannerPagerAdapter;
    private CustomViewPager viewPager;
    private Context mContext;


    private AppCompatImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_soon);

        ivBack = findViewById(R.id.ivBack);


        ivBack.setOnClickListener(this);


        initViewPager();

    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPagingEnabled(true);


        // setting viewPager's pages
        bannerPagerAdapter = new BannerPagerAdapter(getSupportFragmentManager(), 4);
        viewPager.setAdapter(bannerPagerAdapter);
        viewPager.setCurrentItem(0);

        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        // optional
        bannerPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivBack:
                finish();
                break;

            default:
        }
    }
}
