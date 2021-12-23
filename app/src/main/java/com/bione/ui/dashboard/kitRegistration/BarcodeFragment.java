package com.bione.ui.dashboard.kitRegistration;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.bione.db.CommonData;
import com.bione.ui.base.BaseFragment;
import com.bione.utils.Log;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.bione.ui.dashboard.kitRegistration.KitRegisterActivity.birthDate;
import static com.bione.ui.dashboard.kitRegistration.KitRegisterActivity.firstName;
import static com.bione.ui.dashboard.kitRegistration.KitRegisterActivity.gender;
import static com.bione.ui.dashboard.kitRegistration.KitRegisterActivity.kitBarcode;
import static com.bione.ui.dashboard.kitRegistration.KitRegisterActivity.lastName;

public class BarcodeFragment extends BaseFragment {


    private Context mContext;
    private View rootView;

    private LinearLayout firstLayout;
    private LinearLayout secondLayout;
    private RelativeLayout imageLayout;
    private AppCompatTextView tvError;
    private AppCompatTextView tvContinue;
    private AppCompatTextView tvContinue2;
    private AppCompatCheckBox cbAccept;

    private TextInputEditText etBarcode;
    private TextInputEditText etDOB;
    private TextInputEditText etFirstName;
    private TextInputEditText etLastName;
    private TextInputEditText etPhone;
    private TextInputEditText etOtp;
    private TextInputEditText etEmail;


    private boolean isClicked = true;
    private Calendar myCalendar;

    private Spinner spinner;
    private Spinner spinner2;

    private KitRegisterActivity.OnButtonClicked mListeners;


    public BarcodeFragment(KitRegisterActivity.OnButtonClicked mOnButtonClicked) {
        super();
        mListeners = mOnButtonClicked;
    }

    public BarcodeFragment() {

    }

//    public static BarcodeFragment newInstance(String index) {
//        BarcodeFragment f = new BarcodeFragment();
//        Bundle args = new Bundle();
//        args.putString("index", index);
//        f.setArguments(args);
//        return f;
//    }

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
            init();
            setListeners();
            setFirstViewData();
            setSpinner();
            setSpinner2();
//            setSpa();

            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
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
                    new DatePickerDialog(mContext, date,
                            myCalendar.get(Calendar.YEAR),
                            myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        }
        return rootView;
    }

    private void updateLabel() {
//        String myFormat = "MM/dd/yyyy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDOB.setText(sdf.format(myCalendar.getTime()));
    }

    private void init() {
        firstLayout = rootView.findViewById(R.id.firstLayout);
        secondLayout = rootView.findViewById(R.id.secondLayout);
        imageLayout = rootView.findViewById(R.id.imageLayout);
        tvContinue = rootView.findViewById(R.id.tvContinue);
        tvContinue2 = rootView.findViewById(R.id.tvContinue2);
        tvError = rootView.findViewById(R.id.tvError);
        cbAccept = rootView.findViewById(R.id.cbAccept);

        etBarcode = rootView.findViewById(R.id.etBarcode);
        etDOB = rootView.findViewById(R.id.etDOB);
        etFirstName = rootView.findViewById(R.id.etFirstName);
        etLastName = rootView.findViewById(R.id.etLastName);
        etPhone = rootView.findViewById(R.id.etPhone);
        etOtp = rootView.findViewById(R.id.etOtp);
        etEmail = rootView.findViewById(R.id.etEmail);

        etBarcode.setText(kitBarcode);
    }

    private void setListeners() {
        tvContinue.setOnClickListener(this);
        tvContinue2.setOnClickListener(this);
    }

    private void setFirstViewData() {
        etBarcode.setEnabled(true);
        firstLayout.setVisibility(View.VISIBLE);
        secondLayout.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
        cbAccept.setVisibility(View.VISIBLE);
        imageLayout.setVisibility(View.VISIBLE);

    }

    private void setSecondViewData() {

        etBarcode.setEnabled(false);
        firstLayout.setVisibility(View.GONE);
        secondLayout.setVisibility(View.VISIBLE);
        imageLayout.setVisibility(View.GONE);

    }

    private void setSpa() {
        String[] type = new String[]{"Bed-sitter", "Single", "1- Bedroom", "2- Bedroom", "3- Bedroom"};

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

        contacts.add("Choose Relation");
        contacts.add("Self");
        contacts.add("Wife");
        contacts.add("Husband");
        contacts.add("Daughter");
        contacts.add("Son");
        contacts.add("Father");
        contacts.add("Mother");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, contacts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                Log.d("selectedItem", "---" + selectedItem);
                if (selectedItem.equals("Self")) {
                    // do your stuff
                    setFilledData();
                } else {
                    setBlankData();
                }
            } // to close the onItemSelected

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setFilledData() {
        etFirstName.setText(CommonData.getUserData().getFirstname());
        etLastName.setText(CommonData.getUserData().getLastname());
        if (CommonData.getUserData().getMobilenumber().length() > 10) {
            String mobile = CommonData.getUserData().getMobilenumber().toString();
            etPhone.setText(mobile.substring(mobile.length() - 10, mobile.length()));
        } else {
            etPhone.setText(CommonData.getUserData().getMobilenumber());
        }

        etEmail.setText(CommonData.getUserData().getEmail());
        etDOB.setText("");
        etOtp.setText("");
        if (CommonData.getUserData().getGender() != null) {
            Log.d("Gender ", "----" + CommonData.getUserData().getGender());
            if (CommonData.getUserData().getGender().equals("Male")) {
                spinner2.setSelection(1);
            } else {
                spinner2.setSelection(2);
            }
        }
    }

    private void setBlankData() {
        etFirstName.setText("");
        etLastName.setText("");
        etDOB.setText("");
        etPhone.setText("");
        etOtp.setText("");
        etEmail.setText("");
        spinner.setSelection(0);
        spinner2.setSelection(0);
    }

    private void setSpinner2() {
        spinner2 = (Spinner) rootView.findViewById(R.id.spinner2);
        ArrayList<String> contacts = new ArrayList<>();

        contacts.add("Choose Gender");
        contacts.add("Male");
        contacts.add("Female");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, contacts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvContinue:
                checkFirstViewConditions();
                break;

            case R.id.tvContinue2:
                checkSecondViewConditions();
                break;

        }
    }

    private void checkFirstViewConditions() {
        if (etBarcode.getText().toString().equals("")) {
            showErrorMessage("Please enter barcode");
        } else {
            if (etBarcode.getText().toString().equals(kitBarcode)) {
//                cbAccept.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.GONE);
                if (cbAccept.isChecked()) {
                    setSecondViewData();
                } else {
                    showErrorMessage("Please tick the checkbox");
                }
            } else {
                tvError.setVisibility(View.VISIBLE);
//                cbAccept.setVisibility(View.GONE);
            }
        }
    }

    private void checkSecondViewConditions() {
        if (spinner.getSelectedItem().toString().equals("Choose Relation")) {
            showErrorMessage("Choose Relation");
        } else {
            if (etFirstName.getText().toString().equals("")) {
                showErrorMessage("Please enter first name");
            } else {
                if (etLastName.getText().toString().equals("")) {
                    showErrorMessage("Please enter last name");
                } else {
                    if (etDOB.getText().toString().equals("")) {
                        showErrorMessage("Please select Birth Date");
                    } else {
                        if (spinner2.getSelectedItem().toString().equals("Choose Gender")) {
                            showErrorMessage("Choose Gender");
                        } else {
                            if (etPhone.getText().toString().equals("")) {
                                showErrorMessage("Please enter phone number");
                            } else {
//                                if (etOtp.getText().toString().equals("")) {
//                                    showErrorMessage("Please enter Otp");
//                                } else {
                                if (etEmail.getText().toString().equals("")) {
                                    showErrorMessage("Please enter email id");
                                } else {
//                                        setFirstViewData();
                                    firstName = etFirstName.getText().toString();
                                    lastName = etLastName.getText().toString();
                                    gender = spinner2.getSelectedItem().toString();
                                    birthDate = etDOB.getText().toString();
//                                    createObject();
                                    mListeners.submit(1, "");
                                }
//                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void createObject() {

//        BarcodeData barcodeData = new BarcodeData(etFirstName.getText().toString(),
//                etLastName.getText().toString(),
//                etDOB.getText().toString(),
//                spinner2.getSelectedItem().toString(),
//                etBarcode.getText().toString());
//        return barcodeData;
    }
}

