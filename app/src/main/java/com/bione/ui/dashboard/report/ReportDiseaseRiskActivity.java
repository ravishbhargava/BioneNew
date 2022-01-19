package com.bione.ui.dashboard.report;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.reportMyMicro.gutDiet.GutRestorationDiet;
import com.bione.model.reportMyMicro.mygut.DiseasePrediction;
import com.bione.model.reportMyMicro.mygut.MyGut;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.dashboard.report.adapter.DiseaseAdapter;
import com.bione.ui.dashboard.report.adapter.DiseaseRiskAdapter;
import com.bione.utils.Log;

import java.util.ArrayList;

public class ReportDiseaseRiskActivity extends BaseActivity {

    private AppCompatTextView tvIntroduction;
    private RecyclerView recyclerView;
    private RecyclerView recyclerviewHoriz;

    private String authToken = "";
    private ArrayList<DiseasePrediction> diseasePredictionArrayList = new ArrayList<>();
    private ArrayList<DiseasePrediction> highDiseasePredictionArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_disease_risk);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            authToken = extras.getString("authToken");
            // and get whatever type user account id is
        }

        init();
        setListener();
        getGut();
    }

    private void init() {

    }

    private void setListener() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    public void getGut() {
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
        RestClient.getApiInterface3().reportGut(
                commonParams.getMap(),
                commonParams2.getMap())
                .enqueue(new ResponseResolver<MyGut>() {
                    @Override
                    public void onSuccess(MyGut commonResponse) {
                        Log.d("onSuccess -----  ", "--");
//                        Toast.makeText(getApplicationContext(), "Auth generated.", Toast.LENGTH_SHORT).show();
//                        tips = commonResponse.getTips();
//                        Log.d("getTips", "----" + commonResponse.getTips().toString());
                        ArrayList<DiseasePrediction> diseasePredictions = (ArrayList<DiseasePrediction>) commonResponse.getDiseasePrediction();


                        for (int i = 0; i < diseasePredictions.size(); i++) {
                            if (diseasePredictions.get(i).getRisk().equals("moderate")) {
                                highDiseasePredictionArrayList.add(diseasePredictions.get(i));
                            } else {
                                diseasePredictionArrayList.add(diseasePredictions.get(i));
                            }
                        }
                        setRecyclerview();
                        setRecyclerviewHoriz();

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
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("breakfast");
        arrayList.add("lunch");
        arrayList.add("snack");
        arrayList.add("dinner");


        DiseaseAdapter adapter = new DiseaseAdapter(this, diseasePredictionArrayList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setRecyclerviewHoriz() {


        DiseaseRiskAdapter adapter = new DiseaseRiskAdapter(this, highDiseasePredictionArrayList);
        recyclerviewHoriz = findViewById(R.id.recyclerviewHoriz);
        recyclerviewHoriz.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewHoriz.setLayoutManager(linearLayoutManager);
        recyclerviewHoriz.setAdapter(adapter);
    }
}
