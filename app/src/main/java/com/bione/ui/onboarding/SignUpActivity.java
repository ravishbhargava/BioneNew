package com.bione.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.model.CommonResponse;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.Log;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static com.bione.utils.AppConstant.PARAM_MOBILE;
import static com.bione.utils.AppConstant.PARAM_OTP;

public class SignUpActivity extends BaseActivity {

    private AppCompatTextView tvSendOtp;
    private AppCompatTextView tvSignUp;

    private TextInputEditText etFirstName;
    private TextInputEditText etMiddleName;
    private TextInputEditText etLastName;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPassword;
    private TextInputEditText etPhoneNumber;
    private TextInputEditText etOtp;
    private TextInputLayout layoutOtp;

    private AppCompatCheckBox cbFirst;
    private AppCompatCheckBox cbSecond;
    private AppCompatCheckBox cbThird;

    private CountryCodePicker ccp;

    private JSONObject jsonObject = new JSONObject();
    private JSONObject customerObject = new JSONObject();
    private JSONArray customAttributeArray = new JSONArray();
    private JSONObject customAttributeObject = new JSONObject();

    private boolean isSendOtp = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();

        SpannableString ssSecond = new SpannableString(cbSecond.getText().toString());
        ClickableSpan spanSecond = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // do some thing
//                showErrorMessage("second clicked");
                Intent intent = new Intent(SignUpActivity.this, WebviewActivity.class);
                intent.putExtra("link", "https://www.bione.in/terms-of-service");
                startActivity(intent);
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setColor(getResources().getColor(R.color.colorPrimary));
//                textPaint.setUnderlineText(true);
            }
        };

        ssSecond.setSpan(spanSecond, cbSecond.getText().toString().length() - 18, cbSecond.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        cbSecond.setText(ssSecond);
        cbSecond.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString ssThird = new SpannableString(cbThird.getText().toString());
        ClickableSpan spanThird = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // do some thing
//                showErrorMessage("Third clicked");
                Intent intent = new Intent(SignUpActivity.this, WebviewActivity.class);
                intent.putExtra("link", "https://www.bione.in/privacy-policy");
                startActivity(intent);
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setColor(getResources().getColor(R.color.colorPrimary));
//                textPaint.setUnderlineText(true);
            }
        };
        ssThird.setSpan(spanThird, cbThird.getText().toString().length() - 19, cbThird.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        cbThird.setText(ssThird);
        cbThird.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void init() {
        etFirstName = findViewById(R.id.etFirstName);
        etMiddleName = findViewById(R.id.etMiddleName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etPhoneNumber = findViewById(R.id.etPhone);
        etOtp = findViewById(R.id.etOtp);
        layoutOtp = findViewById(R.id.layoutOtp);

        tvSendOtp = findViewById(R.id.tvSendOtp);
        tvSendOtp.setOnClickListener(this);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(this);

        cbFirst = findViewById(R.id.cbFirst);
        cbSecond = findViewById(R.id.cbSecond);
        cbThird = findViewById(R.id.cbThird);

        ccp = findViewById(R.id.ccp);
        ccp.setOnClickListener(this);
        ccp.setCountryForPhoneCode(91);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.tvSendOtp:
                if (!etPhoneNumber.getText().toString().isEmpty()) {
                    callSendOtp(etPhoneNumber.getText().toString(), false);
                } else {
                    showErrorMessage("Please enter mobile number");
                }

//                callSendOtp(etPhoneNumber.getText().toString(), false);
//                signUpViewModel.callSendOtp(etPhoneNumber.getText().toString(), etOtp.getText().toString());
                break;

            case R.id.tvSignUp:

                if (etFirstName.getText().toString().isEmpty()) {
                    showErrorMessage("Please enter first name");
                } else {
//                    if (etMiddleName.getText().toString().isEmpty()) {
//                        showErrorMessage("Please enter middle name");
//                    } else {
                    if (etLastName.getText().toString().isEmpty()) {
                        showErrorMessage("Please enter last name");
                    } else {
                        if (etEmail.getText().toString().isEmpty()) {
                            showErrorMessage("Please enter email address");
                        } else {
                            if (etPassword.getText().toString().isEmpty()) {
                                showErrorMessage("Please enter password");
                            } else {
                                if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                                    showErrorMessage("Password doesn't matched");
                                } else {
                                    if (etPhoneNumber.getText().toString().isEmpty()) {
                                        showErrorMessage("Please enter phone number");
                                    } else {
                                        if (etOtp.getText().toString().isEmpty()) {
                                            showErrorMessage("Please enter OTP");
                                        } else {
                                            if (!cbFirst.isChecked() || !cbSecond.isChecked() || !cbThird.isChecked()) {
                                                showErrorMessage("Please check all boxes");
                                            } else {
                                                //call api
                                                callVerifyOtpSignUp(etPhoneNumber.getText().toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
//                    }
                }
                break;

            default:
                break;

        }
    }


    public void callVerifyOtpSignUp(final String phoneNumber) {
        showLoading();
//        final CommonParams commonParams = new CommonParams.Builder()
//                .add(PARAM_MOBILE, "91" + phoneNumber).build();
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_MOBILE, ccp.getSelectedCountryCode() + phoneNumber)
                .add(PARAM_OTP, etOtp.getText().toString())
                .build();

        RestClient.getApiInterface().sendOtpVerify(commonParams.getMap()).enqueue(new ResponseResolver<List<CommonResponse>>() {
            @Override
            public void onSuccess(List<CommonResponse> commonResponse) {
                Log.d("onSuccess", "" + commonResponse);
                if (commonResponse.get(0).getStatusCode().equals("200")) {
//                    if (resend) {
//                        showErrorMessage(commonResponse.get(0).getMessage());
//                    } else {
//                        Intent intent = new Intent(activity, OtpActivity.class);
//                        intent.putExtra("phoneNumber", phoneNumber);
//                        startActivity(intent);
                    createJsonObject();
//                    }
                } else {
                    showErrorMessage(commonResponse.get(0).getMessage());
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

    private void createJsonObject() {
//        {
//            "customer": {
//                    "email": "xyz@bione.in",
//                    "firstname": "John",
//                    "middlename": "sir",
//                    "lastname": "Doe",
//                    "custom_attributes":[{"attribute_code":"mobilenumber","value":"9443088408"}]
//        },
//            "password":"Password1"
//        }

        jsonObject = new JSONObject();
        customerObject = new JSONObject();
        customAttributeArray = new JSONArray();
        customAttributeObject = new JSONObject();

        try {

            customAttributeObject.put("attribute_code", "mobilenumber");
            customAttributeObject.put("value", etPhoneNumber.getText().toString());
            customAttributeArray.put(customAttributeObject);

            customerObject.put("email", etEmail.getText().toString());
            customerObject.put("firstname", etFirstName.getText().toString());
            customerObject.put("middlename", etMiddleName.getText().toString());
            customerObject.put("lastname", etLastName.getText().toString());
            customerObject.put("custom_attributes", customAttributeArray);

            jsonObject.put("customer", customerObject);
            jsonObject.put("password", etPassword.getText().toString());

            Log.d("main json object ", "data :: " + jsonObject.toString());
            createAccountSignUp(SignUpActivity.this, jsonObject, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void callSendOtp(final String phoneNumber, final boolean resend) {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
//                .add(PARAM_MOBILE, "" + phoneNumber).build();
                .add(PARAM_MOBILE, ccp.getSelectedCountryCode() + phoneNumber).build();

        RestClient.getApiInterface().sendOtpRegister(commonParams.getMap()).enqueue(new ResponseResolver<List<CommonResponse>>() {
            @Override
            public void onSuccess(List<CommonResponse> commonResponse) {
                Log.d("onSuccess", "" + commonResponse);
                if (commonResponse.get(0).getStatusCode().equals("200")) {
//                    if (resend) {
//                        showErrorMessage(commonResponse.get(0).getMessage());
//                    } else {
                    tvSendOtp.setText("Resend OTP");
//                    tvOtpPassword.setVisibility(View.VISIBLE);
                    layoutOtp.setVisibility(View.VISIBLE);

//                    }
                } else {
                    showErrorMessage(commonResponse.get(0).getMessage());
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
