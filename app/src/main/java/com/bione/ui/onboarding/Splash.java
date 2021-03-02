package com.bione.ui.onboarding;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;

import androidx.annotation.Nullable;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.dashboard.MainActivity;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.walkthrough.WalkActivity;
import com.bione.utils.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.bione.utils.AppConstant.PARAM_PASSWORD;
import static com.bione.utils.AppConstant.PARAM_USERNAME;


public class Splash extends BaseActivity {


    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d("hash key", " : " + printKeyHash(this));

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

    @Override
    public void onClick(View view) {

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
                Intent intent;
                if (CommonData.getUserData() != null) {
                    CommonData.updateGuest(false);
                    intent = new Intent(Splash.this, MainActivity.class);
                } else {
                    intent = new Intent(Splash.this, WalkActivity.class);
                }
                startActivity(intent);
                finish();
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


    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }
}
