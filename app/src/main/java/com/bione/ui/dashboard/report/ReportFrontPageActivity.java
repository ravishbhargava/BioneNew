package com.bione.ui.dashboard.report;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.model.CommonResponse;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.Log;

public class ReportFrontPageActivity extends BaseActivity {

    private AppCompatTextView tvText;
    private String authToken = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_front_page);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            authToken = extras.getString("authToken");
            // and get whatever type user account id is
        }

        tvText = findViewById(R.id.tvText);
        tvText.setText(Html.fromHtml(getResources().getString(R.string.with_personalised_mysmart_diet)));
        reportCustomerDetail();
    }

    @Override
    public void onClick(View view) {

    }


    public void reportCustomerDetail() {
        final CommonParams commonParams = new CommonParams.Builder()
                .add("Authorization", "Bearer " + authToken)
                .add("Content-Type", "application/json")
//                .add("Content-Type", "text/plain")
                .build();
        final CommonParams commonParams2 = new CommonParams.Builder()
                .add("id", "MM2019231362")
                .build();

        Log.d("headers", " data :: " + commonParams.getMap().toString());
        Log.d("params", " data :: " + commonParams2.getMap().toString());


//        RestClient.getApiInterface4("https://mymicrobiome.bione.in/").reportCustomerDetail(
        RestClient.getApiInterface3().reportCustomerDetail(
                commonParams.getMap(),
                commonParams2.getMap())
                .enqueue(new ResponseResolver<CommonResponse>() {
                    @Override
                    public void onSuccess(CommonResponse commonResponse) {
                        Log.d("onSuccess -----  ", "--");
//                        Toast.makeText(getApplicationContext(), "Auth generated.", Toast.LENGTH_SHORT).show();
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
