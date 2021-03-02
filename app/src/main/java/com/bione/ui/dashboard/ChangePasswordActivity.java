package com.bione.ui.dashboard;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.Log;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.bione.utils.AppConstant.PARAM_CONFIRM_PASS;
import static com.bione.utils.AppConstant.PARAM_MOBILE;
import static com.bione.utils.AppConstant.PARAM_NEW_PASS;
import static com.bione.utils.AppConstant.PARAM_OTP;

public class ChangePasswordActivity extends BaseActivity {

    private AppCompatImageView ivClose;
    private AppCompatTextView tvSave;
    private AppCompatTextView tvHead;
    private AppCompatEditText etCurrentPassword;
    private AppCompatEditText etNewPassword;
    private AppCompatEditText etConfirmPassword;
    private TextInputLayout layoutCurrentPassword;

    private JSONObject jsonObject = new JSONObject();
    private String currentPassword = "";
    private String newPassword = "";

    private boolean fromForgot = false; // changePassword/reset password logic
    private String mobile = "";
    private String otp = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
//            newString= null;
        } else {
            fromForgot = extras.getBoolean("fromForgot");
            mobile = extras.getString("mobile");
            otp = extras.getString("otp");
        }

        init();


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tvSave:
                if (fromForgot) {
                    validResetPassword();
                } else {
                    validChangePassword();
                }
                break;

            case R.id.ivClose:
                finish();
                break;
        }
    }

    private void init() {

        ivClose = findViewById(R.id.ivClose);
        tvSave = findViewById(R.id.tvSave);
        tvHead = findViewById(R.id.tvHead);
        layoutCurrentPassword = findViewById(R.id.layoutCurrentPassword);
        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        ivClose.setOnClickListener(this);
        tvSave.setOnClickListener(this);

        // changepassword / reset password logic
        if (fromForgot) {
            tvHead.setText("Reset \npassword");
            layoutCurrentPassword.setVisibility(View.GONE);
        } else {
            tvHead.setText("Change \npassword");
            layoutCurrentPassword.setVisibility(View.VISIBLE);
        }
    }

    private void validChangePassword() {
        if (etCurrentPassword.getText().toString().equals("")) {
            showErrorMessage("Please enter current password");
        } else {
            if (etNewPassword.getText().toString().equals("")) {
                showErrorMessage("Please enter new password");
            } else {
                if (etConfirmPassword.getText().toString().equals("")) {
                    showErrorMessage("Please enter confirm password");
                } else {
                    if (etConfirmPassword.getText().toString().equals(etNewPassword.getText().toString())) {
//                                updatePasswordAPI();
                        currentPassword = etCurrentPassword.getText().toString();
                        newPassword = etNewPassword.getText().toString();
                        updatePasswordAPI();
                    } else {
                        showErrorMessage("Password does not match");
                    }
                }
            }
        }
    }

    private void validResetPassword() {

        if (etNewPassword.getText().toString().equals("")) {
            showErrorMessage("Please enter new password");
        } else {
            if (etConfirmPassword.getText().toString().equals("")) {
                showErrorMessage("Please enter confirm password");
            } else {
                if (etConfirmPassword.getText().toString().equals(etNewPassword.getText().toString())) {
                    currentPassword = etCurrentPassword.getText().toString();
                    newPassword = etNewPassword.getText().toString();
                    resetPasswordAPI();
                } else {
                    showErrorMessage("Password does not match");
                }
            }
        }
    }

    private void updatePasswordAPI() {
        showLoading();
        createPasswordObject();

        final CommonParams commonParams = new CommonParams.Builder()
                .add("Authorization", "Bearer " + CommonData.getCustomerToken())
                .add("Content-Type", "application/json")
                .build();

        final CommonParams commonParams2 = new CommonParams.Builder()
                .add("customerId", CommonData.getUserData().getEntityId())
                .build();

        Log.d("headers", " data :: " + commonParams.getMap().toString());
        Log.d("params", " data :: " + commonParams2.getMap().toString());

        RequestBody body =
                RequestBody.create(MediaType.parse("text/plain"), jsonObject.toString());

        RestClient.getApiInterface().changePassword(
                commonParams.getMap(),
                commonParams.getMap(),
                body)
                .enqueue(new ResponseResolver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isChanged) {

                        try {
                            if (isChanged)
                                showErrorMessage("Password changed successfully");
                            else
                                showErrorMessage("Unexpected error!");
                        } catch (Exception e) {
                            e.printStackTrace();
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


    private void createPasswordObject() {
//        {
//            "currentPassword": "Mmmmm@1234",
//                "newPassword": "Mmmmm@123"
//        }
        jsonObject = new JSONObject();

        try {
            jsonObject.put("currentPassword", currentPassword);
            jsonObject.put("newPassword", newPassword);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("jsonObject", "data :: " + jsonObject.toString());

    }

    private void resetPasswordAPI() {
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_MOBILE, mobile)
                .add(PARAM_OTP, otp)
                .add(PARAM_NEW_PASS, etNewPassword.getText().toString())
                .add(PARAM_CONFIRM_PASS, etConfirmPassword.getText().toString())
                .build();

        Log.d("code ", "map :: " + commonParams.getMap());
        RestClient.getApiInterface().forgotPasswordUpdate(commonParams.getMap()).enqueue(new ResponseResolver<Boolean>() {
            @Override
            public void onSuccess(Boolean s) {

                Log.d("boolean ", "Value :: " + s);
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
}
