package com.bione.ui.onboarding.signup;

import com.bione.db.CommonData;
import com.bione.model.customerdata.Customer;
import com.bione.model.customerdata.SignInDatum;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.utils.Log;

import java.util.ArrayList;
import java.util.List;

import static com.bione.utils.AppConstant.PARAM_MOBILE;
import static com.bione.utils.AppConstant.PARAM_OTP;
import static com.bione.utils.AppConstant.SUCCESS;

public class SignUpRepository {

    private List<SignInDatum> signInData = new ArrayList<>();

    public List<SignInDatum> callVerifyOtp(String phoneNumber, String otpCode) {

        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_MOBILE, "91" + phoneNumber)
                .add(PARAM_OTP, otpCode)
                .build();
        RestClient.getApiInterface().verifyOtp(commonParams.getMap()).enqueue(new ResponseResolver<List<SignInDatum>>() {
            @Override
            public void onSuccess(List<SignInDatum> commonResponse) {
                signInData = commonResponse;
                if (commonResponse.get(0).getCode() == SUCCESS) {
                    try {
                        CommonData.updateCustomerToken("");
                        CommonData.saveUserData(commonResponse.get(0).toResponseModel(Customer.class));
                        Log.d("common data", "email :: " + CommonData.getUserData().getEmail());

                        signInData = commonResponse;
//                        Intent intent = new Intent(Login.this, MainActivity.class);
//                        // set the new task and clear flags
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
//                    showErrorMessage(commonResponse.get(0).getMessage());
                }

            }

            @Override
            public void onError(ApiError error) {
                Log.d("onError", "" + error);
//                showErrorMessage(error.getMessage());
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
//                showErrorMessage(throwable.getMessage());
            }
        });
        return signInData;
    }
}
