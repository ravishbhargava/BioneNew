package com.bione.ui.home;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseFragment;
import com.bione.utils.Log;

import java.util.Calendar;

public class CustomerReceiptFragment extends BaseFragment {

    private View rootView;
    private String text = "Hello";
    private AppCompatTextView tvHeading;
    private AppCompatTextView tvChat;
    private AppCompatTextView etDate;
    private AppCompatTextView etTestNames;
    private AppCompatImageView ivHead;
    private LinearLayout llBottom;

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activty_customer_receipt, container, false);

            llBottom = rootView.findViewById(R.id.llBottom);
            etDate = rootView.findViewById(R.id.etDate);
            etTestNames = rootView.findViewById(R.id.etTestNames);
            etDate.setOnClickListener(this);
            etTestNames.setOnClickListener(this);

            // Multi spinner
            MultiSelectionSpinner spinner = (MultiSelectionSpinner) rootView.findViewById(R.id.mySpinner1);
            String[] strArray = new String[3];
            strArray[0] = "one";
            strArray[1] = "two";
            strArray[2] = "three";
            spinner.setItems(strArray);
            etTestNames.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = spinner.getSelectedItemsAsString();
                    Log.e("getSelected", "---" + s);
                }
            });

            RadioGroup rg = (RadioGroup) rootView.findViewById(R.id.radioGroup);
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.radio1:
                            llBottom.setVisibility(View.GONE);
                            break;
                        case R.id.radio2:
                            llBottom.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            });

        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.etDate:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                etDate.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();

                break;

            case R.id.etTestNames:

                break;
        }
    }


}
