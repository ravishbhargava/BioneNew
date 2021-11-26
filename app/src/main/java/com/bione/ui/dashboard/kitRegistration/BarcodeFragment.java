package com.bione.ui.dashboard.kitRegistration;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BarcodeFragment extends BaseFragment {


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

    private Spinner spinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_register_kit);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_barcode, container, false);

            myCalendar = Calendar.getInstance();
            init(rootView);
            setListeners();
            setSpinner();
//            setSpa();

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
                    new DatePickerDialog(mContext, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        }
        return rootView;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDOB.setText(sdf.format(myCalendar.getTime()));
    }

    private void init(View view) {
        firstLayout = rootView.findViewById(R.id.firstLayout);
        secondLayout = rootView.findViewById(R.id.secondLayout);
        imageLayout = rootView.findViewById(R.id.imageLayout);
        tvContinue = rootView.findViewById(R.id.tvContinue);
        tvError = rootView.findViewById(R.id.tvError);
        cbAccept = rootView.findViewById(R.id.cbAccept);
        etBarcode = rootView.findViewById(R.id.etBarcode);
        etDOB = rootView.findViewById(R.id.etDOB);

    }

    private void setListeners() {
        tvContinue.setOnClickListener(this);
    }

    private void setSpa(){
        String[] type = new String[] {"Bed-sitter", "Single", "1- Bedroom", "2- Bedroom","3- Bedroom"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        mContext,
                        R.layout.item_test,
                        type);

        AppCompatAutoCompleteTextView editTextFilledExposedDropdown =
                rootView.findViewById(R.id.filled_exposed_dropdown);
        adapter.setDropDownViewResource(R.layout.item_test);
        editTextFilledExposedDropdown.setAdapter(adapter);
        editTextFilledExposedDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setDropDownViewResource(R.layout.item_test);
            }
        });
    }

    private void setSpinner() {

        spinner = (Spinner) rootView.findViewById(R.id.spinner);

        ArrayList<String> contacts = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            contacts.add("one");
            contacts.add("two");
            contacts.add("three");
            contacts.add("four");
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(mContext,  android.R.layout.simple_spinner_dropdown_item, contacts);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

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
//                    Intent intent = new Intent(mContext, ResearchConsentFragment.class);
//                    startActivity(intent);
                }
                break;

        }
    }
}
