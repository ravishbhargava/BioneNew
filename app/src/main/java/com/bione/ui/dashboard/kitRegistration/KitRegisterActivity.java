package com.bione.ui.dashboard.kitRegistration;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class KitRegisterActivity extends BaseActivity {


    private Context mContext;
    private View rootView;

    private LinearLayout firstLayout;
    private LinearLayout secondLayout;
    private RelativeLayout imageLayout;
    private AppCompatTextView tvError;
    private AppCompatTextView tvContinue;
    private AppCompatCheckBox cbAccept;
    private TextInputEditText etBarcode;
    private TextInputEditText etDOB;

    private boolean isClicked = true;
    private Calendar myCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register_kit);

        myCalendar = Calendar.getInstance();
        init();
        setListeners();


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etDOB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(KitRegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDOB.setText(sdf.format(myCalendar.getTime()));
    }

    private void init() {
        firstLayout = findViewById(R.id.firstLayout);
        secondLayout = findViewById(R.id.secondLayout);
        imageLayout = findViewById(R.id.imageLayout);
        tvContinue = findViewById(R.id.tvContinue);
        tvError = findViewById(R.id.tvError);
        cbAccept = findViewById(R.id.cbAccept);
        etBarcode = findViewById(R.id.etBarcode);
        etDOB = findViewById(R.id.etDOB);

    }

    private void setListeners() {
        tvContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvContinue:
                if (isClicked) {
                    etBarcode.setEnabled(false);
                    cbAccept.setVisibility(View.GONE);
                    imageLayout.setVisibility(View.GONE);
                    tvError.setVisibility(View.GONE);
                    secondLayout.setVisibility(View.VISIBLE);
                    firstLayout.setVisibility(View.GONE);
                    isClicked = false;
                } else {
                    etBarcode.setEnabled(true);
                    cbAccept.setVisibility(View.VISIBLE);
                    imageLayout.setVisibility(View.VISIBLE);
                    tvError.setVisibility(View.VISIBLE);
                    secondLayout.setVisibility(View.GONE);
                    firstLayout.setVisibility(View.VISIBLE);

                    isClicked = true;
                    Intent intent = new Intent(KitRegisterActivity.this, ResearchConsent.class);
                    startActivity(intent);
                }
                break;

        }
    }
}
