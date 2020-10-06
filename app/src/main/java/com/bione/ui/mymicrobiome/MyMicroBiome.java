package com.bione.ui.mymicrobiome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.bione.R;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.home.dashboard.banner.BannerPagerAdapter;
import com.bione.ui.mymicrobiome.report.MyReportTypeActivity;
import com.bione.ui.schedulecall.CategorySelect;
import com.bione.utils.CustomViewPager;

import me.relex.circleindicator.CircleIndicator;

public class MyMicroBiome extends BaseActivity {

    private BannerPagerAdapter bannerPagerAdapter;
    private CustomViewPager viewPager;
    private Context mContext;
    private LinearLayoutCompat llReports;
    private LinearLayoutCompat llCouselling;
    private LinearLayoutCompat llFoodAdvice;

    private AppCompatImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymicrobiome);

        ivBack = findViewById(R.id.ivBack);
        llReports = findViewById(R.id.llReports);
        llCouselling = findViewById(R.id.llCouselling);
        llFoodAdvice = findViewById(R.id.llFoodAdvice);

        ivBack.setOnClickListener(this);
        llReports.setOnClickListener(this);
        llCouselling.setOnClickListener(this);
        llFoodAdvice.setOnClickListener(this);

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

            case R.id.llFoodAdvice:
                Intent intent = new Intent(MyMicroBiome.this, ComingSoonActivity.class);
                startActivity(intent);
                break;

            case R.id.llReports:
                Intent intent1 = new Intent(MyMicroBiome.this, MyReportTypeActivity.class);
                startActivity(intent1);
                break;

            case R.id.llCouselling:
                Intent intent2 = new Intent(MyMicroBiome.this, CategorySelect.class);
                intent2.putExtra("fromFlow", "MyMicroBiome");
                intent2.putExtra("geneticType", "MyMicroBiome");
                startActivity(intent2);
                break;

            case R.id.ivBack:
                finish();
                break;

            default:
        }
    }
}
