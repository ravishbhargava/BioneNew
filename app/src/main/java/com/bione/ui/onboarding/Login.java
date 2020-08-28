package com.bione.ui.onboarding;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.CommonResponse;
import com.bione.model.customerdata.Customer;
import com.bione.model.customerdata.SignInDatum;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.home.MainActivity;
import com.bione.utils.Log;
import com.bione.utils.ValidationUtil;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import static com.bione.utils.AppConstant.PARAM_MOBILE;
import static com.bione.utils.AppConstant.PARAM_OTP;
import static com.bione.utils.AppConstant.PARAM_PASSWORD;
import static com.bione.utils.AppConstant.PARAM_USERNAME;
import static com.bione.utils.AppConstant.SUCCESS;

public class Login extends BaseActivity {

    private static final int RC_SIGN_IN = 101;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;

    private LinearLayoutCompat llGoogleSignIn;
    private LinearLayoutCompat llFBLogin;
    private LinearLayoutCompat llPhone;
    private LinearLayoutCompat llEmail;
    private LinearLayoutCompat llMailView;
    private LinearLayoutCompat llPhoneView;

    private String email = "";

    private AppCompatTextView tvLogin;
    private AppCompatTextView tvForgot;
    private AppCompatEditText etEmail;
    private AppCompatEditText etPassword;
    private AppCompatEditText etPhone;

    private String phoneNumber = "";
    private String otpCode = "";

    private boolean isThroughPhoneNumber = true;
//    private LoginButton loginButton;
//    private static final String EMAIL = "email_signin";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("hash key", " : " + printKeyHash(this));

        init();
        setListners();
        setView(llPhoneView, llMailView);
        setView(llEmail, llPhone);

        initGoogle();
        initFB();
//        callbackManager = CallbackManager.Factory.create();
//        loginButton = (LoginButton) findViewById(R.id.login_button);
//        loginButton.setReadPermissions(Arrays.asList(EMAIL));
//        // If you are using in a fragment, call loginButton.setFragment(this);
//
//        // Callback registration
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d("onSuccess", "loginResult : " + loginResult);
//                // App code
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d("onCancel", "onCancel : ");
//
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                Log.d("onError", "exception : ");
//                exception.printStackTrace();
//
//                // App code
//            }
//        });
    }

    private void init() {
        llGoogleSignIn = findViewById(R.id.llGoogleSignIn);
        llFBLogin = findViewById(R.id.llFBLogin);
        llEmail = findViewById(R.id.llEmail);
        llPhone = findViewById(R.id.llPhone);


        llMailView = findViewById(R.id.llMailView);
        llPhoneView = findViewById(R.id.llPhoneView);

        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);

        tvForgot = findViewById(R.id.tvForgot);
        tvLogin = findViewById(R.id.tvLogin);
    }

    private void setListners() {
        llGoogleSignIn.setOnClickListener(this);
        llFBLogin.setOnClickListener(this);
        llEmail.setOnClickListener(this);
        llPhone.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvForgot.setOnClickListener(this);
    }

    private void initFB() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("onSuccess 2", "loginResult : " + loginResult);
                        // App code
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                (object, response) -> {
                                    Log.v("LoginActivity", response.toString());

                                    // Application code

                                    try {
                                        email = object.getString("email_signin");
                                        etEmail.setText(email);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        String birthday = object.getString("birthday"); // 01/31/1980 format
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email_signin,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();

                        // App code
                    }

                    @Override
                    public void onCancel() {
                        Log.d("onCancel 2", "onCancel : ");

                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("onError 2", "exception : ");
                        exception.printStackTrace();
                        // App code
                    }
                });
    }

    private void initGoogle() {
        // Configure sign-in to request the user's ID, email_signin address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // Need to change google account details
            case R.id.llGoogleSignIn:
                if (isThroughPhoneNumber) {
                    isThroughPhoneNumber = false;
                }
                setView(llMailView, llPhoneView);
                setView(llPhone, llEmail);
                signIn();
                break;
            // Need to change Application ID at LIVE
            case R.id.llFBLogin:
                if (isThroughPhoneNumber) {
                    isThroughPhoneNumber = false;
                }
                setView(llMailView, llPhoneView);
                setView(llPhone, llEmail);
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email_signin"));
                break;
            case R.id.llEmail:
                if (isThroughPhoneNumber) {
                    isThroughPhoneNumber = false;
                }
                setView(llMailView, llPhoneView);
                setView(llPhone, llEmail);
                break;

            case R.id.llPhone:
                if (!isThroughPhoneNumber) {
                    isThroughPhoneNumber = true;
                }
                setView(llPhoneView, llMailView);
                setView(llEmail, llPhone);
                break;

            case R.id.tvLogin:
                if (isThroughPhoneNumber) {
                    phoneNumber = etPhone.getText().toString();
                    if (ValidationUtil.checkPhone(phoneNumber)) {
                        callSendOtp(phoneNumber, this, false);
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.error_mobile, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    getCustomerToken();
                }

                break;

        }
    }


    public void callSendOtp(final String phoneNumber, final Context context, final boolean resend) {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
//                .add(PARAM_MOBILE, "" + phoneNumber).build();
                .add(PARAM_MOBILE, "91" + phoneNumber).build();

        RestClient.getApiInterface().sendOtp(commonParams.getMap()).enqueue(new ResponseResolver<List<CommonResponse>>() {
            @Override
            public void onSuccess(List<CommonResponse> commonResponse) {
                Log.d("onSuccess", "" + commonResponse);
                if (commonResponse.get(0).getStatusCode().equals("200")) {
                    if (resend) {
                        showErrorMessage(commonResponse.get(0).getMessage());
                    } else {
//                        Intent intent = new Intent(context, OtpActivity.class);
//                        intent.putExtra("phoneNumber", phoneNumber);
//                        startActivity(intent);
                        openDialog();
                    }
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

    private void setView(LinearLayoutCompat llVisible, LinearLayoutCompat llGone) {
        llVisible.setVisibility(View.VISIBLE);
        llGone.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            com.google.android.gms.tasks.Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("handle", "+++++++++++++" + account.getEmail());
            email = account.getEmail();
            etEmail.setText(email);
            // Signed in successfully, show authenticated UI.
//            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("aeaeaea", "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        Log.d("account ", "_+_+_+_+_+_" + account);
        if (account == null) {
            Log.d("nulll", "----------------");
        } else {
            Log.d("onStart", "----------------" + account.getEmail());
            email = account.getEmail();
            etEmail.setText(email);
        }
//        updateUI(account);
    }

    @Override
    protected void onStop() {
        super.onStop();
        signOut();
        revokeAccess();
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//        LoginManager.getInstance().logOut();
    }

    //If the user deletes their account, you must delete the information that your app obtained from the Google APIs.
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    // This code clears which account is connected to the app. To sign in again, the user must choose their account again.
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void openDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_otp);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        AppCompatEditText etOtp = dialog.findViewById(R.id.etOtp);
        AppCompatTextView tvOk = dialog.findViewById(R.id.tvOk);

        // if button is clicked, close the custom dialog
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(Login.this, MainActivity.class);
//                // set the new task and clear flags
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);


                otpCode = etOtp.getText().toString();
                if (otpCode.length() < 4) {
                    Toast.makeText(getApplicationContext(), "Enter OTP Code", Toast.LENGTH_SHORT).show();
                } else {
                    callVerifyOtp();
                }
                dialog.dismiss();


            }
        });

        dialog.show();
    }


    public void callVerifyOtp() {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
//                .add(PARAM_MOBILE, "" + phoneNumber)
                .add(PARAM_MOBILE, "91" + phoneNumber)
                .add(PARAM_OTP, otpCode)
                .build();
        RestClient.getApiInterface().verifyOtp(commonParams.getMap()).enqueue(new ResponseResolver<List<SignInDatum>>() {
            @Override
            public void onSuccess(List<SignInDatum> commonResponse) {

                if (commonResponse.get(0).getCode() == SUCCESS) {
                    try {

                        CommonData.saveUserData(commonResponse.get(0).toResponseModel(Customer.class));
                        Log.d("common data", "email :: " + CommonData.getUserData().getEmail());

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        // set the new task and clear flags
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    private void getCustomerToken() {

        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_USERNAME, "murugesan@bione.in")
                .add(PARAM_PASSWORD, "admin@123")
                .build();

        Log.d("code ", "map :: " + commonParams.getMap());

        RestClient.getApiInterface().getCustomerToken(commonParams.getMap()).enqueue(new ResponseResolver<String>() {

            @Override
            public void onSuccess(String s) {

                CommonData.updateCustomerToken(s);
                Log.d("customer ", "token :: " + CommonData.getCustomerToken());

//                Intent intent = new Intent(Splash.this, Walk.class);
//                startActivity(intent);
                getCustomerDetails();
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

    public void getCustomerDetails() {
        showLoading();

        final CommonParams commonParams = new CommonParams.Builder()
                .add("Authorization", "Bearer " + CommonData.getCustomerToken())
//                .add(PARAM_TPKEN_ID, "Bearer " + CommonData.getCustomerToken())
                .add("Content-Type", "application/json")
                .build();

//        final CommonParams commonParams = new CommonParams.Builder()
//                .add(PARAM_TPKEN_ID, CommonData.getCustomerToken())
//                .build();
        RestClient.getApiInterface().getCustomerDetails(commonParams.getMap()).enqueue(new ResponseResolver<Customer>() {
            @Override
            public void onSuccess(Customer commonResponse) {

//                if (commonResponse.get(0).getCode() == SUCCESS) {
                try {

                    CommonData.saveUserData(commonResponse);
                    Log.d("common data", "email :: " + CommonData.getUserData().getEmail());

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    // set the new task and clear flags
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
//                } else {
//                    showErrorMessage(commonResponse.get(0).getMessage());
//                }
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
