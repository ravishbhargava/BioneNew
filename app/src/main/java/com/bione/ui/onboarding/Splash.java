package com.bione.ui.onboarding;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.home.MainActivity;
import com.bione.ui.onboarding.walkthrough.Walk;
import com.bione.utils.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.bione.utils.AppConstant.PARAM_PASSWORD;
import static com.bione.utils.AppConstant.PARAM_USERNAME;

public class Splash extends BaseActivity {

    private Handler handler;
    private static final String TAG = Splash.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onResume() {
        super.onResume();
        call();
    }

    private void call() {
        showLoading();
        handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {

                adminTokenAPI();
//                Intent intent = new Intent(Splash.this, MainActivity.class);
//                startActivity(intent);
            }
        };

        handler.postDelayed(r, 500);

    }

    // Method to encode a string value using `UTF-8` encoding scheme
    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    private void adminTokenAPI() {

        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_USERNAME, "mobileapi")
                .add(PARAM_PASSWORD, "BIONE@123")
                .build();

        Log.d("code ", "map :: " + commonParams.getMap());

        RestClient.getApiInterface().adminToken(commonParams.getMap()).enqueue(new ResponseResolver<String>() {

            @Override
            public void onSuccess(String s) {

                CommonData.updateAdminToken(s);
                Log.d("admin ", "token :: " + CommonData.getAdminToken());

                if (CommonData.getUserData() != null) {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Splash.this, Walk.class);
                    startActivity(intent);
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

    @Override
    public void onClick(View view) {

    }
}
