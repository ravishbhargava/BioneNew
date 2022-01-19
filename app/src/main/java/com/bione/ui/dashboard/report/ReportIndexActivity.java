package com.bione.ui.dashboard.report;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.model.reportMyMicro.MyMicrobiomeAuthLoginData;
import com.bione.network.ApiError;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.Log;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ReportIndexActivity extends BaseActivity {

    private AppCompatTextView tvFrontPage;
    private AppCompatTextView tvIntroduction;
    private AppCompatTextView tvTips;
    private AppCompatTextView tvFood;
    private AppCompatTextView tvGut;
    private AppCompatTextView tvDisease;
    private AppCompatTextView tvSmartDiet;
    private AppCompatTextView tvFoodSupplements;
    private AppCompatTextView tvGutRestoration;
    private String authToken = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_index);

        init();
        setListener();
        myMicroBiomeAuth();
    }

    private void init() {
        tvFrontPage = findViewById(R.id.tvFrontPage);
        tvIntroduction = findViewById(R.id.tvIntroduction);
        tvTips = findViewById(R.id.tvTips);
        tvFood = findViewById(R.id.tvFood);
        tvGut = findViewById(R.id.tvGut);
        tvDisease = findViewById(R.id.tvDisease);
        tvSmartDiet = findViewById(R.id.tvSmartDiet);
        tvFoodSupplements = findViewById(R.id.tvFoodSupplements);
        tvGutRestoration = findViewById(R.id.tvGutRestoration);
    }

    private void setListener() {
        tvFrontPage.setOnClickListener(this);
        tvIntroduction.setOnClickListener(this);
        tvTips.setOnClickListener(this);
        tvFood.setOnClickListener(this);
        tvGut.setOnClickListener(this);
        tvDisease.setOnClickListener(this);
        tvSmartDiet.setOnClickListener(this);
        tvFoodSupplements.setOnClickListener(this);
        tvGutRestoration.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvFrontPage:
                Intent frontPageIntent = new Intent(ReportIndexActivity.this, ReportFrontPageActivity.class);
                frontPageIntent.putExtra("authToken", authToken);
                startActivity(frontPageIntent);
                break;

            case R.id.tvIntroduction:
                Intent introIntent = new Intent(ReportIndexActivity.this, ReportIntroActivity.class);
                introIntent.putExtra("authToken", authToken);
                startActivity(introIntent);
                break;
            case R.id.tvTips:
                Intent tipsIntent = new Intent(ReportIndexActivity.this, ReportTipsActivity.class);
                tipsIntent.putExtra("authToken", authToken);
                startActivity(tipsIntent);
                break;
            case R.id.tvFood:
                Intent foodIntent = new Intent(ReportIndexActivity.this, ReportFoodActivity.class);
                foodIntent.putExtra("authToken", authToken);
                startActivity(foodIntent);
                break;
            case R.id.tvGut:
                Intent myGutIntent = new Intent(ReportIndexActivity.this, ReportMyGutActivity.class);
                myGutIntent.putExtra("authToken", authToken);
                startActivity(myGutIntent);
                break;
            case R.id.tvDisease:
                Intent diseaseIntent = new Intent(ReportIndexActivity.this, ReportDiseaseRiskActivity.class);
                diseaseIntent.putExtra("authToken", authToken);
                startActivity(diseaseIntent);
                break;
            case R.id.tvFoodSupplements:
                Intent foodSupplementsIntent = new Intent(ReportIndexActivity.this, ReportFoodSupplementsActivity.class);
                foodSupplementsIntent.putExtra("authToken", authToken);
                startActivity(foodSupplementsIntent);
                break;
            case R.id.tvSmartDiet:
                Intent smartDietIntent = new Intent(ReportIndexActivity.this, ReportSmartDietActivity.class);
                smartDietIntent.putExtra("authToken", authToken);
                startActivity(smartDietIntent);
                break;
            case R.id.tvGutRestoration:
                Intent gutRestorationIntent = new Intent(ReportIndexActivity.this, ReportGutDietActivity.class);
                gutRestorationIntent.putExtra("authToken", authToken);
                startActivity(gutRestorationIntent);
                break;
        }
    }

    public void myMicroBiomeAuth() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user", "BioneApp");
            jsonObject.put("password", "BiONe@21fSRp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body =
                RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        RestClient.getApiInterface4("https://mymicrobiome.bione.in/").myMicroBiomeAuth(body).enqueue(new ResponseResolver<MyMicrobiomeAuthLoginData>() {
            @Override
            public void onSuccess(MyMicrobiomeAuthLoginData commonResponse) {
                Log.d("onSuccess -----  ", "--");
                authToken = commonResponse.getToken().toString();
                Toast.makeText(getApplicationContext(), "Auth generated.", Toast.LENGTH_SHORT).show();

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
