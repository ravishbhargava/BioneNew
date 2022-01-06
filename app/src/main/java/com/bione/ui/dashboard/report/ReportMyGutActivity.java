package com.bione.ui.dashboard.report;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.reportMyMicro.mygut.Microbe;
import com.bione.model.reportMyMicro.mygut.MyGut;
import com.bione.model.reportMyMicro.mygut.Pathogen;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.dashboard.report.adapter.PathogenAdapter;
import com.bione.ui.dashboard.report.adapter.MicrobesAdapter;
import com.bione.utils.Log;

import java.util.ArrayList;

public class ReportMyGutActivity extends BaseActivity {

    private AppCompatTextView tvMGMI;
    private AppCompatTextView tvPathConclusion;
    private String authToken = "";

    private RecyclerView pathogenRecyclerView;
    private ArrayList<Pathogen> pathogenArrayList = new ArrayList<>();

    private RecyclerView microbeRecyclerView;
    private ArrayList<Microbe> microbeArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_mygut);

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
        tvPathConclusion = findViewById(R.id.tvPathConclusion);
        tvMGMI = findViewById(R.id.tvMGMI);
        pathogenRecyclerView = findViewById(R.id.pathogenRecyclerView);
        microbeRecyclerView = findViewById(R.id.microbeRecyclerView);
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

                        pathogenArrayList = (ArrayList<Pathogen>) commonResponse.getBacterialPathogen().getPathogen();
                        Log.d("pathogenArrayList", "---" + pathogenArrayList.size());
                        microbeArrayList = (ArrayList<Microbe>) commonResponse.getSignatureMicrobes().getMicrobes();
                        Log.d("microbeArrayList", "---" + microbeArrayList.size());
                        tvMGMI.setText(commonResponse.getMgmi1().getMGMIScore() + " " + Html.fromHtml(commonResponse.getMgmi1().getMgmiConclusion()));
                        setPathogenRecyclerview();
                        tvPathConclusion.setText(Html.fromHtml(commonResponse.getBacterialPathogen().getPathConclusion()));
                        setMicrobesRecyclerview();

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

    private void setPathogenRecyclerview() {

        PathogenAdapter adapter = new PathogenAdapter(pathogenArrayList);
        pathogenRecyclerView = findViewById(R.id.pathogenRecyclerView);
        pathogenRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        pathogenRecyclerView.setLayoutManager(linearLayoutManager);
        pathogenRecyclerView.setAdapter(adapter);

    }

    private void setMicrobesRecyclerview() {


        MicrobesAdapter adapter = new MicrobesAdapter(microbeArrayList);
        microbeRecyclerView = findViewById(R.id.microbeRecyclerView);
        microbeRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        microbeRecyclerView.setLayoutManager(linearLayoutManager);
        microbeRecyclerView.setAdapter(adapter);

    }
}
