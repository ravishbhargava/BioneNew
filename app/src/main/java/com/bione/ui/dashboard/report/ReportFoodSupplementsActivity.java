package com.bione.ui.dashboard.report;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.model.reportMyMicro.foodsupplements.FoodSuppl;
import com.bione.model.reportMyMicro.foodsupplements.Foodsupplements;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.Log;

import java.util.List;

public class ReportFoodSupplementsActivity extends BaseActivity {

    private AppCompatTextView tvProbiotics;
    private AppCompatTextView tvPrebiotics;
    private AppCompatTextView tvPolyphenols;
    private AppCompatTextView tvDigestive;
    private String authToken = "";
    private List<Foodsupplements> foodsupplements;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_food_suppliments);
        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            authToken = extras.getString("authToken");
            // and get whatever type user account id is
        }

        init();
        setListener();
        getFoodSupplements();
    }

    private void init() {
        tvProbiotics = findViewById(R.id.tvProbiotics);
        tvPrebiotics = findViewById(R.id.tvPrebiotics);
        tvPolyphenols = findViewById(R.id.tvPolyphenols);
        tvDigestive = findViewById(R.id.tvDigestive);
    }

    private void setListener() {
        tvProbiotics.setOnClickListener(this);
        tvPrebiotics.setOnClickListener(this);
        tvPolyphenols.setOnClickListener(this);
        tvDigestive.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvProbiotics:
                openScreen("Probiotics", foodsupplements.get(0).getFoodsupplement());
                break;
            case R.id.tvPrebiotics:
                openScreen("Prebiotics", foodsupplements.get(1).getFoodsupplement());
                break;
            case R.id.tvPolyphenols:
                openScreen("Polyphenols", foodsupplements.get(2).getFoodsupplement());
                break;
            case R.id.tvDigestive:
                openScreen("Digestive Support", foodsupplements.get(3).getFoodsupplement());
                break;
        }
    }

    private void openScreen(String heading, String detail) {
        Intent intent = new Intent(ReportFoodSupplementsActivity.this, ReportFoodDetailActivity.class);
        intent.putExtra("heading", heading);
        intent.putExtra("detail", detail);
        startActivity(intent);
    }

    public void getFoodSupplements() {
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


        RestClient.getApiInterface3().reportFood(
                commonParams.getMap(),
                commonParams2.getMap())
                .enqueue(new ResponseResolver<FoodSuppl>() {
                    @Override
                    public void onSuccess(FoodSuppl commonResponse) {
                        Log.d("onSuccess -----  ", "--");
//                        Toast.makeText(getApplicationContext(), "Auth generated.", Toast.LENGTH_SHORT).show();

                        foodsupplements = commonResponse.getFoodsupplements();
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
