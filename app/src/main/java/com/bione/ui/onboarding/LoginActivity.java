package com.bione.ui.onboarding;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.CommonResponse;
import com.bione.model.customerdata.Customer;
import com.bione.model.customerdata.SignInDatum;
import com.bione.model.updateprofile.UpdateProfile;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.dashboard.MainActivity;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.CustomTypefaceSpan;
import com.bione.utils.Log;
import com.bione.utils.ValidationUtil;
import com.facebook.AccessToken;
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
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import static com.bione.utils.AppConstant.PARAM_EMAIL;
import static com.bione.utils.AppConstant.PARAM_MOBILE;
import static com.bione.utils.AppConstant.PARAM_OTP;
import static com.bione.utils.AppConstant.PARAM_PASSWORD;
import static com.bione.utils.AppConstant.PARAM_PHONE;
import static com.bione.utils.AppConstant.PARAM_USERNAME;
import static com.bione.utils.AppConstant.SUCCESS;

public class LoginActivity extends BaseActivity {


    private static final int RC_SIGN_IN = 101;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;


    private LinearLayout llPhone;
    private LinearLayout llEmail;
    private AppCompatTextView tvLoginType;
    private AppCompatTextView tvHead;
    private AppCompatTextView tvLogin;
    private AppCompatTextView tvCreateAccount;
    private AppCompatTextView tvForgotPassword;
    private AppCompatTextView tvResendOtp;
    private AppCompatImageView ivGoogle;
    private AppCompatImageView ivFB;

    private AppCompatEditText etEmail;
    private AppCompatEditText etPassword;
    private AppCompatEditText etMobile;
    private AppCompatEditText etOtp;
//    private int loginType = 0;


    private String phoneNumber = "";
    private String otpCode = "";
    private String email = "";
    private String displayName = "";
    private String password = "";

    private boolean isThroughPhoneNumber = true;

    private JSONObject jsonObject = new JSONObject();
    private JSONObject customerObject = new JSONObject();
    private JSONObject customAttributeObject = new JSONObject();
    private JSONArray customAttributeArray = new JSONArray();

    private LinearLayout llCountryCode;
    private CountryCodePicker ccp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setListeners();


        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");
        SpannableStringBuilder SS = new SpannableStringBuilder(tvHead.getText().toString());
        SS.setSpan(new CustomTypefaceSpan("", font), 0, 4, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        SS.setSpan(new CustomTypefaceSpan("", font2), 4, tvHead.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvHead.setText(SS);

        initGoogle();
        initFB();


        etOtp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (etMobile.getText().toString().equals("")) {
                    etMobile.requestFocus();
                }
            }
        });
    }

    private void init() {
        ivFB = findViewById(R.id.ivFB);
        ivGoogle = findViewById(R.id.ivGoogle);
        llPhone = findViewById(R.id.llPhone);
        llEmail = findViewById(R.id.llEmail);

        tvHead = findViewById(R.id.tvHead);
        tvLoginType = findViewById(R.id.tvLoginType);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvLogin = findViewById(R.id.tvLogin);
        tvResendOtp = findViewById(R.id.tvResendOtp);

        etMobile = findViewById(R.id.etMobile);
        etOtp = findViewById(R.id.etOtp);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        tvLogin.setText("GET OTP");

        llCountryCode = findViewById(R.id.llCountryCode);
        ccp = findViewById(R.id.ccp);
        ccp.setOnClickListener(this);
        ccp.setCountryForPhoneCode(91);

    }

    private void setListeners() {
        tvLogin.setOnClickListener(this);
        tvLoginType.setOnClickListener(this);
        tvCreateAccount.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        ivFB.setOnClickListener(this);
        ivGoogle.setOnClickListener(this);
        tvResendOtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ccp:

                Toast.makeText(LoginActivity.this, "This is from OnCountryChangeListener. \n Country updated to "
                        + ccp.getSelectedCountryName() + "(" + ccp.getSelectedCountryCodeWithPlus() + ")", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tvLoginType:
                if (isThroughPhoneNumber) {
                    isThroughPhoneNumber = false;
                    setLoginType(llEmail, llPhone);
                    tvLogin.setText("Log in");
                    tvLoginType.setText(R.string.login_with_mobile);
                } else {
                    isThroughPhoneNumber = true;
                    setLoginType(llPhone, llEmail);
                    tvLogin.setText("Get otp");
                    tvLoginType.setText(R.string.login_with_email);
                }
                break;

            case R.id.tvCreateAccount:
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;

            case R.id.tvForgotPassword:
                Intent intent2 = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent2);
                break;

//            case R.id.tvLogin:
//                Intent intent3 = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent3);
//                break;

            case R.id.tvResendOtp:
                if (ValidationUtil.checkPhone(phoneNumber)) {
                    callSendOtp(phoneNumber, false);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.error_mobile, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ivGoogle:
                if (isThroughPhoneNumber) {
                    isThroughPhoneNumber = false;
                }
//                callSocialLogin("ravishdroid@gmail.com", "");
                googleSignIn();
                break;
            // Need to change Application ID at LIVE
            case R.id.ivFB:
                if (isThroughPhoneNumber) {
                    isThroughPhoneNumber = false;
                }

                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));

                break;

            case R.id.tvLogin:
                if (isThroughPhoneNumber) {
                    phoneNumber = etMobile.getText().toString();
                    if (tvLogin.getText().equals("GET OTP")) {
                        if (ValidationUtil.checkPhone(phoneNumber)) {
                            callSendOtp(phoneNumber, false);
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.error_mobile, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        phoneNumber = etMobile.getText().toString();
                        if (ValidationUtil.checkPhone(phoneNumber)) {
                            otpCode = etOtp.getText().toString();
                            if (otpCode.length() < 4) {
                                Toast.makeText(getApplicationContext(), "Enter OTP Code", Toast.LENGTH_SHORT).show();
                            } else {
                                callVerifyOtp();
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), R.string.error_mobile, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    email = etEmail.getText().toString();
                    if (ValidationUtil.checkEmail(email)) {
                        password = etPassword.getText().toString();
                        if (ValidationUtil.checkPassword(password)) {
                            getCustomerToken();
                        } else {
                            showErrorMessage("Please enter valid password");
                        }
                    } else {
                        showErrorMessage("Please enter valid email");
                    }
                }
                break;

        }

    }

    private void setLoginType(LinearLayout visible, LinearLayout gone) {
        visible.setVisibility(View.VISIBLE);
        gone.setVisibility(View.GONE);

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
                                    try {
                                        email = object.getString("email");
                                        displayName = "";
                                        displayName = object.getString("name");
                                        Log.d("displayName", "-------------" + displayName);
                                        etEmail.setText(email);
                                        callSocialLogin(email, " ");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Log.d("onCancel 2", "onCancel : ");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("onError 2", "exception : ");
                        exception.printStackTrace();
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

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("resultCode", " ---- " + resultCode);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            com.google.android.gms.tasks.Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            Log.d("callbackManager", "------------- aaaaya");
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("handle", "+++++++++++++" + account.getEmail());
            email = account.getEmail();
            displayName = "";
            displayName = account.getDisplayName();
            Log.d("displayName", "-------------" + displayName);
            etEmail.setText(email);
            callSocialLogin(email, " ");
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
            displayName = "";
            displayName = account.getDisplayName();
            etEmail.setText(email);
            callSocialLogin(email, " ");
        }
//        updateUI(account);
    }

    @Override
    protected void onStop() {
        super.onStop();
        signOut();
        revokeAccess();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        LoginManager.getInstance().logOut();
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


    public void callSocialLogin(final String email, final String phone) {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_PHONE, phone)
                .add(PARAM_EMAIL, email)
                .build();
        RestClient.getApiInterface().socialLogin(commonParams.getMap()).enqueue(new ResponseResolver<List<SignInDatum>>() {
            @Override
            public void onSuccess(List<SignInDatum> commonResponse) {

                if (commonResponse.get(0).getCode() == SUCCESS) {
                    try {
                        CommonData.updateCustomerToken("");
                        CommonData.saveUserData(commonResponse.get(0).toResponseModel(Customer.class));
                        Log.d("common data", "email :: " + CommonData.getUserData().getEmail());
                        CommonData.updateGuest(false);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        // set the new task and clear flags
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (commonResponse.get(0).getCode() == 404) {
                    // not available
                    createJsonObject();
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

    public void callSendOtp(final String phoneNumber, final boolean resend) {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
//                .add(PARAM_MOBILE, "" + phoneNumber).build();
                .add(PARAM_MOBILE, "" + ccp.getSelectedCountryCode() + phoneNumber).build();

        RestClient.getApiInterface().sendOtp(commonParams.getMap()).enqueue(new ResponseResolver<List<CommonResponse>>() {
            @Override
            public void onSuccess(List<CommonResponse> commonResponse) {
                Log.d("onSuccess", "" + commonResponse);
                if (commonResponse.get(0).getStatusCode().equals("200")) {
                    if (resend) {
                        showErrorMessage(commonResponse.get(0).getMessage());
                    } else {
//                        openDialog();

                        tvLogin.setText("Log in");
                        etOtp.setVisibility(View.VISIBLE);
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


    public void callVerifyOtp() {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_MOBILE, ccp.getSelectedCountryCode() + phoneNumber)
                .add(PARAM_OTP, otpCode)
                .build();
        RestClient.getApiInterface().verifyOtp(commonParams.getMap()).enqueue(new ResponseResolver<List<SignInDatum>>() {
            @Override
            public void onSuccess(List<SignInDatum> commonResponse) {

                if (commonResponse.get(0).getCode() == SUCCESS) {
                    try {
                        CommonData.updateCustomerToken("");
                        CommonData.saveUserData(commonResponse.get(0).toResponseModel(Customer.class));
                        Log.d("common data", "email :: " + CommonData.getUserData().getEmail());
                        CommonData.updateGuest(false);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
                .add(PARAM_USERNAME, email)
                .add(PARAM_PASSWORD, password)
                .build();

        Log.d("code ", "map :: " + commonParams.getMap());
        RestClient.getApiInterface().getCustomerToken(commonParams.getMap()).enqueue(new ResponseResolver<String>() {
            @Override
            public void onSuccess(String s) {
                CommonData.updateCustomerToken(s);
                Log.d("customer ", "token :: " + CommonData.getCustomerToken());
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
                .add("Content-Type", "application/json")
                .build();

        RestClient.getApiInterface().getCustomerDetails(commonParams.getMap()).enqueue(new ResponseResolver<UpdateProfile>() {
            @Override
            public void onSuccess(UpdateProfile updateProfile) {


                try {
                    Log.d("update ", "mobile :: " + updateProfile.getCustomAttributes().get(0).getValue());

                    Customer customer = new Customer();
                    customer.setFirstname(updateProfile.getFirstname());
//                            customer.setMiddlename(updateProfile.getMiddlename());
                    customer.setLastname(updateProfile.getLastname());
                    customer.setEmail(updateProfile.getEmail());
                    customer.setWebsiteId("" + updateProfile.getWebsiteId());
                    customer.setEntityId("" + updateProfile.getId());
                    customer.setMobilenumber(updateProfile.getCustomAttributes().get(0).getValue());

                    if (updateProfile.getMiddlename() != null) {
                        customer.setMiddlename(updateProfile.getMiddlename());
                    }

                    CommonData.saveUserData(customer);

                    Log.d("common data", "mobile :: " + CommonData.getUserData().getMobilenumber());
                    CommonData.updateGuest(false);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    // set the new task and clear flags
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

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

        String firstname = " ";
        String middlename = " ";
        String lastname = " ";

        String str = displayName;
        String[] splitStr = str.split("\\s+");

        if (splitStr.length == 1) {
            firstname = splitStr[0];
        } else if (splitStr.length == 2) {
            firstname = splitStr[0];
            lastname = splitStr[1];
        } else if (splitStr.length == 3) {
            firstname = splitStr[0];
            middlename = splitStr[1];
            lastname = splitStr[2];
        }

//        String middleName = "";
//
//        if (etMiddleName.getText().toString().equals("")) {
//            middleName = " ";
//        } else {
//            middleName = etMiddleName.getText().toString();
//        }

        jsonObject = new JSONObject();
        customerObject = new JSONObject();
        customAttributeArray = new JSONArray();
        customAttributeObject = new JSONObject();

        try {

            customAttributeObject.put("attribute_code", "mobilenumber");
            customAttributeObject.put("value", "");
            customAttributeArray.put(customAttributeObject);

//            customerObject.put("id", "" + CommonData.getUserData().getEntityId());
            customerObject.put("email", email);
            customerObject.put("firstname", firstname);
            customerObject.put("middlename", middlename);
            customerObject.put("lastname", lastname);
//            customerObject.put("website_id", CommonData.getUserData().getWebsiteId());
            customerObject.put("custom_attributes", customAttributeArray);

            jsonObject.put("customer", customerObject);
            jsonObject.put("password", "Password1");

            Log.d("main json object ", "data :: " + jsonObject.toString());
            createAccountSignUp(LoginActivity.this, jsonObject, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
