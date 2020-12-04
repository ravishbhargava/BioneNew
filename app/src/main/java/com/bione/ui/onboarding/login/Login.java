package com.bione.ui.onboarding.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
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
import com.bione.model.updateprofile.UpdateProfile;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.home.MainActivity;
import com.bione.ui.onboarding.WebviewActivity;
import com.bione.ui.onboarding.signup.SignUpActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import static com.bione.utils.AppConstant.PARAM_EMAIL;
import static com.bione.utils.AppConstant.PARAM_MOBILE;
import static com.bione.utils.AppConstant.PARAM_OTP;
import static com.bione.utils.AppConstant.PARAM_PASSWORD;
import static com.bione.utils.AppConstant.PARAM_PHONE;
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
    private String displayName = "";
    private String password = "";

    private AppCompatTextView tvCreate;
    private AppCompatTextView tvTerm;
    private AppCompatTextView tvLogin;
    private AppCompatTextView tvForgot;
    private AppCompatEditText etEmail;
    private AppCompatEditText etPassword;
    private AppCompatEditText etPhone;

    private ImageView viewEye;
    private boolean isShow = false;
    private String phoneNumber = "";
    private String otpCode = "";

    private boolean isThroughPhoneNumber = true;

    private JSONObject jsonObject = new JSONObject();
    private JSONObject customerObject = new JSONObject();
    private JSONArray customAttributeArray = new JSONArray();
    private JSONObject customAttributeObject = new JSONObject();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        DataBindingUtil.setContentView(this,R.layout.activity_login);

        Log.d("hash key", " : " + printKeyHash(this));

        init();
        setListeners();
        setView(llPhoneView, llMailView);
        setView(llEmail, llPhone);
//        createJsonObject();
        initGoogle();
        initFB();

//        String text = "<font color=#cc0029>First Color</font> <font color=#ffcc00>Second Color</font>";
//        tvCreate.setText(Html.fromHtml(text));

//        SpannableStringBuilder builder = new SpannableStringBuilder();
//
//        SpannableString str1 = new SpannableString("New to Bione? ");
//        str1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, str1.length(), 0);
//        builder.append(str1);
//
//        SpannableString str2 = new SpannableString("Create Account");
//        str2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, str2.length(), 0);
//        builder.append(str2);
//
////        TextView tv = (TextView) findViewById(android.R.id.text1);
//        tvCreate.setText(builder, TextView.BufferType.SPANNABLE);


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
        tvTerm = findViewById(R.id.tvTerm);
        tvCreate = findViewById(R.id.tvCreate);

        viewEye = findViewById(R.id.viewEye);
    }

    private void setListeners() {
        llGoogleSignIn.setOnClickListener(this);
        llFBLogin.setOnClickListener(this);
        llEmail.setOnClickListener(this);
        llPhone.setOnClickListener(this);
        tvCreate.setOnClickListener(this);
        tvTerm.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvForgot.setOnClickListener(this);
        viewEye.setOnClickListener(this);
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // Need to change google account details
            case R.id.viewEye:
                if (!isShow) {
                    isShow = true;
                    viewEye.setImageResource(R.drawable.ic_show);
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    isShow = false;
                    viewEye.setImageResource(R.drawable.ic_hidden);
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;

            case R.id.llGoogleSignIn:
                if (isThroughPhoneNumber) {
                    isThroughPhoneNumber = false;
                }
                setView(llMailView, llPhoneView);
                setView(llPhone, llEmail);
//                callSocialLogin("ravishdroid@gmail.com", "");
                signIn();
                break;
            // Need to change Application ID at LIVE
            case R.id.llFBLogin:
                if (isThroughPhoneNumber) {
                    isThroughPhoneNumber = false;
                }
                setView(llMailView, llPhoneView);
                setView(llPhone, llEmail);
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));

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

            case R.id.tvTerm:
                Intent intent = new Intent(Login.this, WebviewActivity.class);
                intent.putExtra("link","https://www.bione.in/terms-of-service");
                startActivity(intent);
                break;

            case R.id.tvCreate:
                Intent intentSignUp = new Intent(Login.this, SignUpActivity.class);
                startActivity(intentSignUp);
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
                .add(PARAM_MOBILE, "91" + phoneNumber)
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

                        Intent intent = new Intent(Login.this, MainActivity.class);
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

                    Intent intent = new Intent(Login.this, MainActivity.class);
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
            createAccountSignUp(Login.this, jsonObject, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
