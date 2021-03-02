package com.bione.ui.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.CommonResponse;
import com.bione.model.customerdata.Customer;
import com.bione.model.updateprofile.UpdateProfile;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.dashboard.MainActivity;
import com.bione.utils.AppConstant;
import com.bione.utils.CommonUtil;
import com.bione.utils.Log;

import org.json.JSONObject;

import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.bione.utils.AppConstant.PARAM_MOBILE;
//import com.bione.db.CommonData;
//import com.bione.model.CommonResponse;
//import com.bione.model.customerdata.Customer;
//import com.bione.model.updateprofile.UpdateProfile;
//import com.bione.network.CommonParams;
//import com.bione.network.ResponseResolver;
//import com.bione.network.RestClient;
//import com.bione.ui.home.MainActivity;
//import com.bione.utils.AppConstant;
//import com.bione.utils.Log;
//import com.bione.utils.ProgressDialog;
//import com.zoho.livechat.android.MbedableComponent;
//import com.zoho.salesiqembed.ZohoSalesIQ;
//
//import org.json.JSONObject;
//
//import java.util.List;
//
//import okhttp3.MediaType;
//import okhttp3.RequestBody;
//
//import static com.bione.utils.AppConstant.PARAM_MOBILE;

public abstract class BaseActivity extends AppCompatActivity implements BaseView, View.OnClickListener {

    private static final int OVERLAY_TEXT_SIZE_INT = 15;
    private static final int TEN = 10;
    //    private static final String OVERLAY_TEXT = BuildConfig.APP_NAME + "_" + BuildConfig.FLAVOR + "_v" + BuildConfig.VERSION_CODE;
    private BasePresenter mBasePresenter;

    /**
     * Receiver To handle Notification When App is in Foreground state
     */
    private BroadcastReceiver notificationReceiver;
    private AlertDialog mNotificationDialog;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        try {
//              ZohoSalesIQ.Chat.setVisibility(MbedableComponent.CHAT, false);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void showErrorMessage(final int resId) {
        showErrorMessage(getString(resId), null);
    }

    @Override
    public void showErrorMessage(final String errorMessage) {
        showErrorMessage(errorMessage, null);
    }

    @Override
    public void showErrorMessage(final String errorMessage, final OnErrorHandleCallback mOnErrorHandleCallback) {
        new AlertDialog.Builder(this)
                .setMessage(errorMessage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        if (mOnErrorHandleCallback != null) {
                            mOnErrorHandleCallback.onErrorCallback();
                        }
                    }
                })
                .show();
    }


    @Override
    public void showErrorMessage(final ApiError apiError) {
        showErrorMessage(apiError, null);
    }

    @Override
    public void showErrorMessage(final ApiError apiError, final OnErrorHandleCallback mOnErrorHandleCallback) {
        if (apiError != null) {
            if (apiError.getStatusCode() == AppConstant.SESSION_EXPIRED) {
                //todo handle session expired case
                CommonUtil.showToast(this, getString(R.string.error_session_expired));
            } else {
                showErrorMessage(apiError.getMessage(), mOnErrorHandleCallback);
            }
        } else {
            showErrorMessage(getString(R.string.error_unexpected_error), mOnErrorHandleCallback);
        }
    }


    @Override
    public boolean isNetworkConnected() {
        return CommonUtil.isNetworkAvailable(this);
    }


    @Override
    public void showLoading() {
//        ProgressDialog.showProgressDialog(this);
    }

    @Override
    public void showLoading(final String message) {
//        ProgressDialog.showProgressDialog(this, message);
    }

    @Override
    public void hideLoading() {
//        ProgressDialog.dismissProgressDialog();
    }


    @Override
    public boolean dispatchTouchEvent(final MotionEvent event) {
        final View view = getCurrentFocus();
        try {
            final boolean ret = super.dispatchTouchEvent(event);
            if (view != null && view instanceof EditText) {
                final View w = getCurrentFocus();
                final int[] scrcoords = new int[2];
                assert w != null;
                w.getLocationOnScreen(scrcoords);
                final float x = event.getRawX() + w.getLeft() - scrcoords[0];
                final float y = event.getRawY() + w.getTop() - scrcoords[1];
                if (event.getAction() == MotionEvent.ACTION_UP
                        && (x < w.getLeft() || x >= w.getRight()
                        || y < w.getTop() || y > w.getBottom())) {
                    final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                }
            }
            return ret;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method to set toolbar
     */
    public void setToolbar() {

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");
        ab.setHomeAsUpIndicator(R.drawable.ic_launcher_background);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void callSendOtp(final String phoneNumber, final boolean resend) {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_MOBILE, "91" + phoneNumber).build();

        RestClient.getApiInterface().sendOtp(commonParams.getMap()).enqueue(new ResponseResolver<List<CommonResponse>>() {
            @Override
            public void onSuccess(List<CommonResponse> commonResponse) {
                Log.d("onSuccess", "" + commonResponse);
                if (commonResponse.get(0).getStatusCode().equals("200")) {
                    if (resend) {
                        showErrorMessage(commonResponse.get(0).getMessage());
                    } else {
//                        Intent intent = new Intent(activity, OtpActivity.class);
//                        intent.putExtra("phoneNumber", phoneNumber);
//                        startActivity(intent);
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

    public void createAccountSignUp(final Activity activity, final JSONObject jsonObject, final boolean isSocialSignUp) {
        showLoading();
        RequestBody body =
                RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        RestClient.getApiInterface().createAccount(body)
                .enqueue(new ResponseResolver<UpdateProfile>() {
                    @Override
                    public void onSuccess(UpdateProfile updateProfile) {
                        try {
                            Customer customer = new Customer();
                            customer.setFirstname(updateProfile.getFirstname());
                            customer.setLastname(updateProfile.getLastname());
                            customer.setEmail(updateProfile.getEmail());
                            customer.setWebsiteId("" + updateProfile.getWebsiteId());
                            customer.setEntityId("" + updateProfile.getId());
                            if (updateProfile.getMiddlename() != null)
                                customer.setMiddlename(updateProfile.getMiddlename());

                            if (!isSocialSignUp)
                                customer.setMobilenumber(updateProfile.getCustomAttributes().get(0).getValue());

                            CommonData.saveUserData(customer);
//                            Log.d("common data", "mobile :: " + CommonData.getUserData().getMobilenumber());
                            CommonData.updateGuest(false);
                            Intent intent = new Intent(activity, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
//                            finishAffinity();
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
}
