package com.bione.ui.dashboard.report;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.reportMyMicro.gutDiet.GutDiet;
import com.bione.model.reportMyMicro.gutDiet.GutRestorationDiet;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.dashboard.report.adapter.GutRestorationAdapter;
import com.bione.ui.dashboard.report.adapter.MyGutPagerAdapter;
import com.bione.utils.CustomViewPager;
import com.bione.utils.Log;

import java.util.ArrayList;

public class ReportGutDietActivity extends BaseActivity {

    private AppCompatTextView tvIntroduction;
    private AppCompatTextView tvRestoration;
    private AppCompatTextView tvMaintenance;
    private RecyclerView recyclerView;
    private GutRestorationDiet gutRestorationDiet;
    private String authToken = "";
    private CustomViewPager viewPager;
    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gut_diet);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            authToken = extras.getString("authToken");
            // and get whatever type user account id is
        }


        init();
        setListener();
        tvRestoration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
                upcomingSelected(tvRestoration, tvMaintenance);
            }
        });


        tvMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
                pastSelected(tvMaintenance, tvRestoration);
            }
        });

        getGutRestoration();

    }

    private void init() {
        tvRestoration = findViewById(R.id.tvRestoration);
        tvMaintenance = findViewById(R.id.tvMaintenance);
    }

    private void setListener() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    public void getGutRestoration() {
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
        RestClient.getApiInterface3().getGutRestoration(
                commonParams.getMap(),
                commonParams2.getMap())
                .enqueue(new ResponseResolver<GutDiet>() {
                    @Override
                    public void onSuccess(GutDiet commonResponse) {
                        Log.d("onSuccess -----  ", "--");
//                        Toast.makeText(getApplicationContext(), "Auth generated.", Toast.LENGTH_SHORT).show();
                        gutRestorationDiet = commonResponse.getGutRestorationDiet();

//                        setRecyclerview();
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

    private void setRecyclerview() {

//        arrayList.add("breakfast");
//        arrayList.add("lunch");
//        arrayList.add("snack");
//        arrayList.add("dinner");
//        arrayList.add("breakfast");
//        arrayList.add("lunch");
//        arrayList.add("snack");
//        arrayList.add("dinner");

        GutRestorationAdapter adapter = new GutRestorationAdapter(arrayList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPagingEnabled(false);

        arrayList.add("breakfast");
        arrayList.add("lunch");
        arrayList.add("snack");
        arrayList.add("dinner");
        arrayList.add("breakfast");
        arrayList.add("lunch");
        arrayList.add("snack");
        arrayList.add("dinner");
        // setting viewPager's pages
        final MyGutPagerAdapter adapter = new MyGutPagerAdapter(getSupportFragmentManager(), 2,arrayList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    private void upcomingSelected(final TextView tvPressed, final TextView tvUnPressed) {
        tvPressed.setBackgroundResource(R.drawable.drawable_left_border_selected);
        tvPressed.setTextColor(getResources().getColor(R.color.white));

        tvUnPressed.setBackgroundResource(R.drawable.drawable_right_border_unselected);
        tvUnPressed.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    private void pastSelected(final TextView tvPressed, final TextView tvUnPressed) {
        tvPressed.setBackgroundResource(R.drawable.drawable_right_border_selected);
        tvPressed.setTextColor(getResources().getColor(R.color.white));

        tvUnPressed.setBackgroundResource(R.drawable.drawable_left_border_unselected);
        tvUnPressed.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

}
