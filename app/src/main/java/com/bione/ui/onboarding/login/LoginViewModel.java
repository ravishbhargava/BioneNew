package com.bione.ui.onboarding.login;

import androidx.lifecycle.ViewModel;

import com.bione.utils.Log;

public class LoginViewModel extends ViewModel {

    private void setname(String name) {

        Log.d("LoginViewModel", "setname" + name);
    }
}
