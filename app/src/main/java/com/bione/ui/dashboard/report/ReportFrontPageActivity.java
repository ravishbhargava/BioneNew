package com.bione.ui.dashboard.report;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.model.reportMyMicro.MyMicrobiomeAuthLoginData;
import com.bione.model.reportMyMicro.frontpage.CustomerDetails;
import com.bione.model.reportMyMicro.frontpage.FrontPage;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.Log;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ReportFrontPageActivity extends BaseActivity {

    private AppCompatTextView tvText;
    private AppCompatTextView tvCode;
    private AppCompatTextView tvCustomer;
    private AppCompatTextView tvGender;
    private AppCompatTextView tvRegDate;
    private AppCompatTextView tvReportDate;

    private String authToken = "";
    private CustomerDetails customerDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_front_page);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            authToken = extras.getString("authToken");
            // and get whatever type user account id is
        }

        tvCode = findViewById(R.id.tvCode);
        tvCustomer = findViewById(R.id.tvCustomer);
        tvGender = findViewById(R.id.tvGender);
        tvRegDate = findViewById(R.id.tvRegDate);
        tvReportDate = findViewById(R.id.tvReportDate);


        tvText = findViewById(R.id.tvText);
        tvText.setText(Html.fromHtml(getResources().getString(R.string.with_personalised_mysmart_diet)));
        myMicroBiomeAuth();

    }

    private void setData() {
        tvCode.setText("");
        tvCustomer.setText(customerDetails.getPatientFirstName() + " " + customerDetails.getPatientLastName());
        tvGender.setText(customerDetails.getGender());
        tvRegDate.setText(customerDetails.getSampleRegistrationDate());
        tvReportDate.setText(customerDetails.getReleasedDate());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(ReportFrontPageActivity.this, ReportIndexActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
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
                .add("id", "MMBFTD1ZZZ84")
//                .add("id", "MM2019231362")
                .build();

        Log.d("headers", " data :: " + commonParams.getMap().toString());
        Log.d("params", " data :: " + commonParams2.getMap().toString());


//        RestClient.getApiInterface4("https://mymicrobiome.bione.in/").reportCustomerDetail(
        RestClient.getApiInterface3().reportCustomerDetail(
                commonParams.getMap(),
                commonParams2.getMap())
                .enqueue(new ResponseResolver<FrontPage>() {
                    @Override
                    public void onSuccess(FrontPage commonResponse) {
                        Log.d("onSuccess -----  ", "--");
//                        Toast.makeText(getApplicationContext(), "Auth generated.", Toast.LENGTH_SHORT).show();
                        customerDetails = commonResponse.getCustomerDetails();
                        setData();
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
                reportCustomerDetail();
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
