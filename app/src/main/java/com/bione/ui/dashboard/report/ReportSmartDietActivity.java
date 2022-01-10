package com.bione.ui.dashboard.report;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.reportMyMicro.smartdiet.FoodRecommendation;
import com.bione.model.reportMyMicro.smartdiet.SmartDiet;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.dashboard.report.adapter.SmartDietAdapter;
import com.bione.ui.dashboard.report.adapter.SmartDietHorizAdapter;
import com.bione.utils.Log;

import java.util.ArrayList;

public class ReportSmartDietActivity extends BaseActivity {

    private AppCompatTextView tvSuper;
    private AppCompatTextView tvGood;
    private String authToken = "";
    private RecyclerView recyclerView;
    private RecyclerView horizRecyclerView;
    private SmartDietAdapter adapter;
    private ArrayList<FoodRecommendation> foodRecommendationArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_smart_diet);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            authToken = extras.getString("authToken");
            // and get whatever type user account id is
        }
        init();
        setListener();
        getSmartDiet();
    }

    private void init() {
        tvSuper = findViewById(R.id.tvSuper);
        tvGood = findViewById(R.id.tvGood);
    }

    private void setListener() {
        tvSuper.setOnClickListener(this);
        tvGood.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSuper:
                ArrayList<FoodRecommendation> foodList = new ArrayList<>();
                for (int i = 0; i < foodRecommendationArrayList.size(); i++) {
                    if (foodRecommendationArrayList.get(i).getMM2019231362FoodCategory().equals("Super Food")) {
                        foodList.add(foodRecommendationArrayList.get(i));
                    }
                }
                recyclerView.getRecycledViewPool().clear();
                adapter.setArrayList(foodList);
                break;

            case R.id.tvGood:
                ArrayList<FoodRecommendation> foodList2 = new ArrayList<>();
                for (int i = 0; i < foodRecommendationArrayList.size(); i++) {
                    if (foodRecommendationArrayList.get(i).getMM2019231362FoodCategory().equals("Avoid Food")) {
                        foodList2.add(foodRecommendationArrayList.get(i));
                    }
                }
                recyclerView.getRecycledViewPool().clear();
                adapter.setArrayList(foodList2);
                break;
        }
    }


    public void getSmartDiet() {
        final CommonParams commonParams = new CommonParams.Builder()
                .add("Authorization", "Bearer " + authToken)
                .add("Content-Type", "application/json")
//                .add("Content-Type", "text/plain")
                .build();
        final CommonParams commonParams2 = new CommonParams.Builder()
//                .add("id", "MMBFTD1ZZZ84") vipin personal
                .add("id", "MM2019231362")
                .build();


        RestClient.getApiInterface3().getSmartDiet(
                commonParams.getMap(),
                commonParams2.getMap())
                .enqueue(new ResponseResolver<SmartDiet>() {
                    @Override
                    public void onSuccess(SmartDiet commonResponse) {
                        Log.d("onSuccess -----  ", "--");


                        foodRecommendationArrayList = (ArrayList<FoodRecommendation>) commonResponse.getFoodRecommendation();
                        setRecyclerview();
                        setHorizRecyclerView();
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

        adapter = new SmartDietAdapter(foodRecommendationArrayList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void setHorizRecyclerView() {

        SmartDietHorizAdapter adapter = new SmartDietHorizAdapter(foodRecommendationArrayList);
        horizRecyclerView = findViewById(R.id.horizRecyclerView);
        horizRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        horizRecyclerView.setLayoutManager(linearLayoutManager);
        horizRecyclerView.setAdapter(adapter);

    }
}
