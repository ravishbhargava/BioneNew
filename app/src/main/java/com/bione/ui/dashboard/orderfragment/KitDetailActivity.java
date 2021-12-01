package com.bione.ui.dashboard.orderfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.model.BarCodeStatus;
import com.bione.model.customerOrders.KitOrder;
import com.bione.network.ApiError;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.dashboard.bottomFragments.report.ReportPdfViewActivity;
import com.bione.ui.dashboard.kitRegistration.KitRegisterActivity;
import com.bione.utils.Log;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class KitDetailActivity extends BaseActivity {

    private AppCompatImageView ivBack;
    private AppCompatImageView ivKit;
    private AppCompatTextView tvKitId;
    private AppCompatTextView tvKitName;
    private AppCompatTextView tvStatus;
    private AppCompatTextView tvSecondStatus;
    private AppCompatTextView tvActivatedBy;
    private AppCompatTextView tvTrack;

    private KitOrder customerKits;

    private String data = "";
    private String kitName = "";
    private String kitStatus = "";
    private String sampleId = "";
    private String sampleStatus = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kit_orders_detail);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
//            newString= null;
        } else {
            kitName = extras.getString("kitName");
            kitStatus = extras.getString("kitStatus");
            sampleId = extras.getString("sampleId");
            customerKits = (KitOrder) extras.getSerializable("data");
        }

        init();
        setData();

    }

    private void init() {
        ivKit = findViewById(R.id.ivKit);
        tvKitId = findViewById(R.id.tvKitId);
        tvStatus = findViewById(R.id.tvStatus);
        tvSecondStatus = findViewById(R.id.tvSecondStatus);
        tvKitName = findViewById(R.id.tvKitName);
        tvActivatedBy = findViewById(R.id.tvActivatedBy);
        tvTrack = findViewById(R.id.tvTrack);

        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvTrack.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    private void setData() {
        tvKitName.setText(customerKits.getKitName());
        tvKitId.setText(customerKits.getBarCode());


        if ("1".equalsIgnoreCase(customerKits.getActivationStatus())) {
            tvStatus.setText("Activated");
            tvSecondStatus.setText("Registered");
            tvStatus.setTextColor(R.color.activated_status_color);
            tvActivatedBy.setText(customerKits.getFirstName());
            tvTrack.setText("Track Your Report");
        } else {
            tvStatus.setText("Pending");
            tvSecondStatus.setText("Register");
            tvStatus.setTextColor(R.color.pending_status_color);
            tvActivatedBy.setText("-");
            tvTrack.setText("Register");
        }

        if (customerKits.getSkuCode().equals("LP")) {
            ivKit.setImageResource(R.drawable.lp);
        } else if (customerKits.getSkuCode().equals("LF")) {
            ivKit.setImageResource(R.drawable.lf);
        } else if (customerKits.getSkuCode().equals("MM")) {
            ivKit.setImageResource(R.drawable.mmb);
        } else {
            ivKit.setImageResource(R.drawable.mmb);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivBack:
                finish();
                break;
            case R.id.tvTrack:
                if ("1".equalsIgnoreCase(customerKits.getActivationStatus())) {
                    if (customerKits.getSkuCode().equals("MM")) {
                        barCodeStatusMMAPI(KitDetailActivity.this, customerKits.getBarCode(), "Vipin@28");
                    }
                    else if (customerKits.getSkuCode().equals("LF")) {
                        barCodeStatusLFAPI(KitDetailActivity.this, customerKits.getBarCode(), "Vipin@28");
                    }
                } else {
                    Intent intent = new Intent(KitDetailActivity.this, KitRegisterActivity.class);
                    intent.putExtra("barcode",""+customerKits.getBarCode());
                    startActivity(intent);
                }
                break;

            default:
                break;
        }
    }

    public void barCodeStatusMMAPI(final Activity activity, final String barcode, final String password) {
        showLoading();

        JSONObject jsonObject = new JSONObject();
        try {
//            jsonObject.put("id", "MMBFTD1ZZZ84");
            jsonObject.put("id", barcode);
//            jsonObject.put("id", "MMFEA1ZZZ161");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body =
                RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        RestClient.getApiInterface4("https://mymicrobiome.bione.in/").barcodeStatus(body).enqueue(new ResponseResolver<BarCodeStatus>() {
            @Override
            public void onSuccess(BarCodeStatus commonResponse) {

                Log.d("onSuccess -----  ", "--");
                Log.d("getReportStatus -----  ", commonResponse.getReportStatus());
                Log.d("getReportUrl -----  ", commonResponse.getReportUrl());
                if(commonResponse.getReportStatus().equals("Approved")){

//                }
//                if (commonResponse.getReportUrl() != null) {
                    Intent intent = new Intent(activity, ReportPdfViewActivity.class);
                    intent.putExtra("pdfUrl", commonResponse.getReportUrl());
                    intent.putExtra("password", commonResponse.getPassword());
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Report in progress.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(ApiError error) {
                Log.d("onError", "" + error);
                showErrorMessage(error.getMessage());
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void barCodeStatusLFAPI(final Activity activity, final String barcode, final String password) {
        showLoading();

        JSONObject jsonObject = new JSONObject();
        try {
//            jsonObject.put("id", "MMBFTD1ZZZ84");
            jsonObject.put("id", barcode);
//            jsonObject.put("id", "MMFEA1ZZZ161");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body =
                RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        RestClient.getApiInterface5("https://longifit.bione.in/").barcodeStatusLongifit(body).enqueue(new ResponseResolver<BarCodeStatus>() {
            @Override
            public void onSuccess(BarCodeStatus commonResponse) {

                Log.d("onSuccess -----  ", "--");
                Log.d("getReportStatus -----  ", commonResponse.getReportStatus());
                Log.d("getReportUrl -----  ", commonResponse.getReportUrl());
                if(commonResponse.getReportStatus().equals("Approved")){

//                }
//                if (commonResponse.getReportUrl() != null) {
                    Intent intent = new Intent(activity, ReportPdfViewActivity.class);
                    intent.putExtra("pdfUrl", commonResponse.getReportUrl());
                    intent.putExtra("password", commonResponse.getPassword());
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Report in progress.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(ApiError error) {
                Log.d("onError", "" + error);
                showErrorMessage(error.getMessage());
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                showErrorMessage(throwable.getMessage());
            }
        });
    }
}
