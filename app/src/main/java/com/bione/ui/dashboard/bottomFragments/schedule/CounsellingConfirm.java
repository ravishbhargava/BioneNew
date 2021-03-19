package com.bione.ui.dashboard.bottomFragments.schedule;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.CommonResponse;
import com.bione.model.customerdata.Customer;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.Log;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import static com.bione.utils.AppConstant.PARAM_COUNSELLOR_NAME;
import static com.bione.utils.AppConstant.PARAM_CUSTOMER_ID;
import static com.bione.utils.AppConstant.PARAM_CUSTOMER_NAME;
import static com.bione.utils.AppConstant.PARAM_DATE;
import static com.bione.utils.AppConstant.PARAM_ENTITY_ID;
import static com.bione.utils.AppConstant.PARAM_GENETIC_TYPE;
import static com.bione.utils.AppConstant.PARAM_SLOT;
import static com.bione.utils.AppConstant.PARAM_TIME_SLOT;

public class CounsellingConfirm extends BaseActivity {

    private TextInputEditText tvDateTime;
    private AppCompatTextView tvConfirm;
    private AppCompatTextView tvTitle;
    //    private AppCompatTextView tvCounsellorName;
//    private AppCompatTextView tvCounsellingType;
    private TextInputEditText etEmail;
    private TextInputEditText etName;
    private TextInputEditText etPhone;

    private AppCompatImageView ivBack;
    private AppCompatImageView ivLogo;

    private String counsellorName = "counsellorName";
    private String geneticType = "Genetic";
    private String selectedDateToPass = "";
    private String selectedTimeSlot = "";

    private Customer customer;

    private String dayToPass;
    private String dateToPass;
    private String monthToPass;
    private String yearToPass;
    private String timeToPass;
    private String bookingId = "";
    private String category = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_session);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
//            newString= null;
        } else {
            category = extras.getString("category");
            bookingId = extras.getString("bookingId");
            dayToPass = extras.getString("dayToPass");
            dateToPass = extras.getString("dateToPass");
            monthToPass = extras.getString("monthToPass");
            yearToPass = extras.getString("yearToPass");
            timeToPass = extras.getString("timeToPass");
            geneticType = extras.getString("geneticType");
            counsellorName = extras.getString("counsellorName");
            selectedDateToPass = extras.getString("selectedDateToPass");
            selectedTimeSlot = extras.getString("selectedTimeSlot");
        }

        Log.d("geneticType", "---" + geneticType);
        Log.d("counsellorName", "---" + counsellorName);
        init();
        setData();

    }

    private void init() {


        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        tvDateTime = findViewById(R.id.tvDateTime);
        tvConfirm = findViewById(R.id.tvConfirm);
//        tvCounsellorName = findViewById(R.id.tvCounsellorName);
//        tvCounsellingType = findViewById(R.id.tvCounsellingType);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);

        ivLogo = findViewById(R.id.ivLogo);
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(geneticType);
        tvTitle.setVisibility(View.VISIBLE);
        ivLogo.setVisibility(View.GONE);
    }

    private void setData() {
//        tvCounsellingType.setText("Counselling Type: " + geneticType);
//        tvCounsellorName.setText(counsellorName);
        tvDateTime.setText(dayToPass + ", " + dateToPass + " " + monthToPass + " " + yearToPass + ", " + timeToPass);
        customer = CommonData.getUserData();

        etName.setText(customer.getFirstname());
        etEmail.setText(customer.getEmail());
        if (customer.getMobilenumber().length() == 12) {
            String number = customer.getMobilenumber();
            number = number.substring(2, number.length());
            etPhone.setText(number);
        } else {
            etPhone.setText(customer.getMobilenumber());
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tvConfirm:
//                openDialog();
                showLoading();
                if (bookingId.equals("bookingId")) {
                    scheduleCallAPI();
                } else {
                    call();
                }

                break;

            case R.id.ivBack:
                finish();
                break;

            default:
                break;
        }
    }


    private void scheduleCallAPI() {

        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_CUSTOMER_NAME, etName.getText().toString())
                .add(PARAM_COUNSELLOR_NAME, counsellorName)
                .add(PARAM_CUSTOMER_ID, CommonData.getUserData().getEntityId())
                .add(PARAM_GENETIC_TYPE, geneticType)
                .add(PARAM_DATE, selectedDateToPass)
                .add(PARAM_TIME_SLOT, selectedTimeSlot)

                .build();

        RestClient.getApiInterface().scheduleCall(commonParams.getMap()).enqueue(new ResponseResolver<List<CommonResponse>>() {
            @Override
            public void onSuccess(List<CommonResponse> commonResponses) {

                if (commonResponses.get(0).getStatusCode().equals("200")) {

                    openDialog();
                } else {
                    showErrorMessage(commonResponses.get(0).getMessage());
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


    private void call() {

        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_ENTITY_ID, bookingId)
                .add(PARAM_DATE, selectedDateToPass)
                .add(PARAM_SLOT, selectedTimeSlot)

                .build();

        RestClient.getApiInterface().updateBooking(commonParams.getMap()).enqueue(new ResponseResolver<List<CommonResponse>>() {
            @Override
            public void onSuccess(List<CommonResponse> commonResponses) {

                if (commonResponses.get(0).getStatusCode().equals("200")) {
                    openDialog();
                } else {
                    showErrorMessage(commonResponses.get(0).getMessage());
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

    private void openDialog() {
// custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_success_reset);
//        dialog.getWindow().setBackgroundDrawable(null);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("Title...");
        // set the custom dialog components - text, image and button

        AppCompatImageView ivSuccess = dialog.findViewById(R.id.ivSuccess);
        AppCompatTextView tvOk = dialog.findViewById(R.id.tvOk);
        AppCompatTextView tvSubHeading = dialog.findViewById(R.id.tvSubHeading);
        AppCompatTextView tvHeading = dialog.findViewById(R.id.tvHeading);

        tvHeading.setText("Your booking \n" + "has been confirmed");
        tvSubHeading.setText("Our consultant will get in touch with you");
        ivSuccess.setImageResource(R.drawable.ic_group_133);


        // if button is clicked, close the custom dialog
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.putExtra("status", "Done");
                setResult(101, intent);
                finish();
            }
        });

        dialog.show();
    }


}
