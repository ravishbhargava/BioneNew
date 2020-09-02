package com.bione.ui.schedulecall;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
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
import com.bione.ui.home.MainActivity;
import com.bione.ui.onboarding.Login;
import com.bione.utils.Log;

import java.util.List;

import static com.bione.utils.AppConstant.PARAM_CUSTOMER_ID;
import static com.bione.utils.AppConstant.PARAM_CUSTOMER_NAME;
import static com.bione.utils.AppConstant.PARAM_DATE;
import static com.bione.utils.AppConstant.PARAM_GENETIC_TYPE;
import static com.bione.utils.AppConstant.PARAM_TIME_SLOT;

public class CounsellingConfirm extends BaseActivity {

    private AppCompatTextView tvDateTime;
    private AppCompatTextView tvConfirm;
    private AppCompatTextView tvCounsellorName;
    private AppCompatTextView tvCounsellingType;
    private AppCompatEditText etMail;
    private AppCompatEditText etName;
    private AppCompatEditText etPhone;

    private AppCompatImageView ivBack;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cousel_confirm);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
//            newString= null;
        } else {
//            intent.putExtra("geneticType", geneticType);
//            intent.putExtra("selectedDateToPass", selectedDateToPass);
//            intent.putExtra("timeToPass", tvSelectedSlot.getText().toString());
//            intent.putExtra("selectedTimeSlot", arrayTimeSlots.get(mAdapter.getCheckedPosition()).name);
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
        setdata();

    }

    private void init() {
        etMail = findViewById(R.id.etMail);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        tvDateTime = findViewById(R.id.tvDateTime);
        tvConfirm = findViewById(R.id.tvConfirm);
        tvCounsellorName = findViewById(R.id.tvCounsellorName);
        tvCounsellingType = findViewById(R.id.tvCounsellingType);
        ivBack = findViewById(R.id.ivBack);
        tvConfirm.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    private void setdata() {
        tvCounsellingType.setText("Counselling Type: " + geneticType);
        tvCounsellorName.setText(counsellorName);
        tvDateTime.setText(dayToPass + ", " + dateToPass + " " + monthToPass + " " + yearToPass + ", " + timeToPass);
        customer = CommonData.getUserData();

        etName.setText(customer.getFirstname());
        etMail.setText(customer.getEmail());
        etPhone.setText(customer.getMobilenumber());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tvConfirm:
//                openDialog();
                scheduleCallAPI();
                break;

            case R.id.ivBack:
                finish();
                break;

            default:
                break;
        }
    }


    private void scheduleCallAPI() {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_CUSTOMER_NAME, etName.getText().toString())
                .add(PARAM_CUSTOMER_ID, CommonData.getUserData().getEntityId())
                .add(PARAM_GENETIC_TYPE, geneticType)
                .add(PARAM_DATE, selectedDateToPass)
//                .add(PARAM_DATE, tvCalendarDate.getText().toString())
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

    private void openDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_confirmed);
        dialog.getWindow().setBackgroundDrawable(null);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
//        AppCompatEditText etOtp = dialog.findViewById(R.id.etOtp);
        AppCompatTextView tvOk = dialog.findViewById(R.id.tvOk);

        // if button is clicked, close the custom dialog
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(CounsellingConfirm.this, MainActivity.class);
                // set the new task and clear flags
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialog.show();
    }

}
