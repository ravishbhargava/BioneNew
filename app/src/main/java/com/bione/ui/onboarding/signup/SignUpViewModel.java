package com.bione.ui.onboarding.signup;

import androidx.lifecycle.ViewModel;

import com.bione.model.customerdata.SignInDatum;

import java.util.List;

public class SignUpViewModel extends ViewModel {

    private SignUpRepository repository;
    private List<SignInDatum> commonDataMutableLiveData;

    public void init() {
        if (commonDataMutableLiveData != null) {
            return;
        }
        repository = new SignUpRepository();
        commonDataMutableLiveData = repository.callVerifyOtp("","");
    }

    public void callSendOtp(final String phoneNumber, final String otpCode){
        if (commonDataMutableLiveData != null) {
            return;
        }
        repository = new SignUpRepository();
        commonDataMutableLiveData = repository.callVerifyOtp(phoneNumber,otpCode);
    }

    public List<SignInDatum> getData(){
        return commonDataMutableLiveData;
    }
}
