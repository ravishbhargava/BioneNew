package com.bione.ui.dashboard.report;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

import com.bione.R;
import com.bione.model.reportMyMicro.tips.ReportTips;
import com.bione.model.reportMyMicro.tips.Tip;

import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.dashboard.report.adapter.TipsPagerAdapter;
import com.bione.utils.CustomViewPager;
import com.bione.utils.Log;

import java.util.ArrayList;

public class ReportTipsActivity extends BaseActivity {

    private CustomViewPager viewPager;
    private AppCompatTextView tvIntroduction;
    private String authToken = "";
    private ArrayList<Tip> tips;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_tips);
        tips = new ArrayList<>();
        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            authToken = extras.getString("authToken");
            // and get whatever type user account id is
        }
        init();
        setListener();
        getTips();

    }

    private void init() {

    }

    private void setListener() {

    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setPagingEnabled(true);


        // setting viewPager's pages
        final TipsPagerAdapter adapter = new TipsPagerAdapter(this, tips);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvIntroduction:

                break;
        }
    }

    public void getTips() {
        final CommonParams commonParams = new CommonParams.Builder()
                .add("Authorization", "Bearer " + authToken)
                .add("Content-Type", "application/json")
//                .add("Content-Type", "text/plain")
                .build();
        final CommonParams commonParams2 = new CommonParams.Builder()
//                .add("id", "MMBFTD1ZZZ84")
                .add("id", "MM2019231362")
                .build();

        Log.d("headers", " data :: " + commonParams.getMap().toString());
        Log.d("params", " data :: " + commonParams2.getMap().toString());


//        RestClient.getApiInterface4("https://mymicrobiome.bione.in/").reportCustomerDetail(
        RestClient.getApiInterface3().reportTips(
                commonParams.getMap(),
                commonParams2.getMap())
                .enqueue(new ResponseResolver<ReportTips>() {
                    @Override
                    public void onSuccess(ReportTips commonResponse) {
                        Log.d("onSuccess -----  ", "--");
//                        Toast.makeText(getApplicationContext(), "Auth generated.", Toast.LENGTH_SHORT).show();
                        tips = (ArrayList<Tip>) commonResponse.getTips();
                        Log.d("getTips", "----" + commonResponse.getTips().toString());
                        initViewPager();


                    }

                    @Override
                    public void onError(ApiError error) {
                        Log.d("onError", "" + error);
                        Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        throwable.printStackTrace();
                        Toast.makeText(getApplicationContext(), "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
