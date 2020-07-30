package com.bione.ui.onboarding;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.bione.R;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.home.MainActivity;
import com.bione.utils.Log;
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

public class Login extends BaseActivity implements View.OnClickListener {

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

//    private LoginButton loginButton;
//    private static final String EMAIL = "email";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("hash key", " : " + printKeyHash(this));

        init();
        setListners();
        setView(llPhoneView, llMailView);

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
                                        email = object.getString("email");
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
                        parameters.putString("fields", "id,name,email,gender,birthday");
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
        // Configure sign-in to request the user's ID, email address, and basic
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
                setView(llMailView, llPhoneView);
                signIn();
                break;
            // Need to change Application ID at LIVE
            case R.id.llFBLogin:
                setView(llMailView, llPhoneView);
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
                break;
            case R.id.llEmail:
                setView(llMailView, llPhoneView);
                break;

            case R.id.llPhone:
                setView(llPhoneView, llMailView);
                break;

            case R.id.tvLogin:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

        }
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
