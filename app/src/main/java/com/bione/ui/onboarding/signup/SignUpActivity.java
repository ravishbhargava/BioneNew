package com.bione.ui.onboarding.signup;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;

public class SignUpActivity extends BaseActivity {


    private AppCompatEditText etFirstName;
    private AppCompatEditText etMiddleName;
    private AppCompatEditText etLastName;
    private AppCompatEditText etEmail;
    private AppCompatEditText etPassword;
    private AppCompatEditText etConfirmPassword;
    private AppCompatEditText etPhoneNumber;
    private AppCompatEditText etOtp;

    private AppCompatTextView tvSendOtp;
    private AppCompatTextView tvRegister;

    private AppCompatCheckBox cbFirst;
    private AppCompatCheckBox cbSecond;
    private AppCompatCheckBox cbThird;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_signup);

        initView();
    }

    private void initView() {


        etFirstName = findViewById(R.id.etFirstName);
        etMiddleName = findViewById(R.id.etMiddleName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etOtp = findViewById(R.id.etOtp);

        tvSendOtp = findViewById(R.id.tvSendOtp);
        tvRegister = findViewById(R.id.tvRegister);

        cbFirst = findViewById(R.id.cbFirst);
        cbSecond = findViewById(R.id.cbSecond);
        cbThird = findViewById(R.id.cbThird);
    }

    @Override
    public void onClick(View view) {

    }
}
