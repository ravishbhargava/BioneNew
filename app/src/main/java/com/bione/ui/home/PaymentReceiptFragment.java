package com.bione.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.PaymentReceipt;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseFragment;
import com.bione.utils.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class PaymentReceiptFragment extends BaseFragment {

    private AppCompatTextView tvTitle;
    private AppCompatImageView ivBack;
    private AppCompatTextView tvDate;
    private AppCompatTextView tvSubmit;
    private static AppCompatTextView tvTestNames;
    private AppCompatEditText tvRemark;

    private AppCompatTextView etSalesPerson;
    private AppCompatEditText etFirstName;
    private AppCompatEditText etLastName;
    private AppCompatEditText etTestAmount;
    private AppCompatEditText etAmountNumber;

    private AppCompatEditText etTotalAmount;
    private AppCompatEditText etBalanceAmount;
    private AppCompatImageView ivHead;
    private LinearLayout llBottom;

    private RadioGroup radioGroup;
    private String radioText = "";
    //    private MultiSelectionSpinner spinner;
    private Spinner spinner2;
    private static StringBuilder selectedTests = new StringBuilder();

    private int enteredValue = 0;
    private int actualAmount = 0;

    private Context mContext;
    private View rootView;


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

//        setContentView(R.layout.activty_customer_receipt);
            init();
//            setMultiSpinner();
            setRadio();
            setSpinner();
            etSalesPerson.setText(CommonData.getUserData().getFirstname());

            etTotalAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        actualAmount = Integer.parseInt(etAmountNumber.getText().toString());
                        enteredValue = Integer.parseInt(editable.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    etBalanceAmount.setText("" + (actualAmount - enteredValue));
                }
            });

        }
        return rootView;
    }

    private void setSpinner() {
        spinner2 = rootView.findViewById(R.id.spinner);
//        spinner2 = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.payment_modes, android.R.layout.simple_spinner_item);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.payment_modes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter);

    }

    private void init() {
        llBottom = rootView.findViewById(R.id.llBottom);
        tvSubmit = rootView.findViewById(R.id.tvSubmit);
        tvDate = rootView.findViewById(R.id.tvDate);
        tvTestNames = rootView.findViewById(R.id.tvTestNames);

        etFirstName = rootView.findViewById(R.id.etFirstName);
        etLastName = rootView.findViewById(R.id.etLastName);
        etTestAmount = rootView.findViewById(R.id.etTestAmount);
        etAmountNumber = rootView.findViewById(R.id.etAmountNumber);
        tvRemark = rootView.findViewById(R.id.tvRemark);
        etSalesPerson = rootView.findViewById(R.id.etSalesPerson);
        etTotalAmount = rootView.findViewById(R.id.etTotalAmount);
        etBalanceAmount = rootView.findViewById(R.id.etBalanceAmount);

//        llBottom = findViewById(R.id.llBottom);
//        tvSubmit = findViewById(R.id.tvSubmit);
//        tvDate = findViewById(R.id.tvDate);
//        tvTestNames = findViewById(R.id.tvTestNames);
//        ivBack = findViewById(R.id.ivBack);
//        tvTitle = findViewById(R.id.tvTitle);
//        tvTitle.setVisibility(View.GONE);
//
//        etFirstName = findViewById(R.id.etFirstName);
//        etLastName = findViewById(R.id.etLastName);
//        etAmountNumber = findViewById(R.id.etAmountNumber);
//        tvPayment = findViewById(R.id.tvPayment);
//        etSalesPerson = findViewById(R.id.etSalesPerson);
//        etTotalAmount = findViewById(R.id.etTotalAmount);
//        etBalanceAmount = findViewById(R.id.etBalanceAmount);
//        ivBack.setOnClickListener(this);


        tvSubmit.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvTestNames.setOnClickListener(this);
//        tvPayment.setOnClickListener(this);
    }

    private void setMultiSpinner() {
        // Multi spinner
//        spinner = rootView.findViewById(R.id.mySpinner1);
        String[] strArray = new String[39];
        strArray[0] = "BioneXome";
        strArray[1] = "BioneMITO";
        strArray[2] = "BioneXome + MITO";
        strArray[3] = "BioneGenome30";
        strArray[4] = "BioneGenome30 + MITO";
        strArray[5] = "BioneArrayCyto 750K";
        strArray[6] = "BioneXome + ArrayCyto 750K";
        strArray[7] = "Maternal Cell Contamination";
        strArray[8] = "Fragile X repeat expansion";
        strArray[9] = "Single gene variant Testing-SangerSeq";
        strArray[10] = "BioneXomeTrio";
        strArray[11] = "BioneArrayCyto HD";
        strArray[12] = "BioneMerosin-deficient congenital muscular dystrophy type 1A";
        strArray[13] = "BioneDMD";
        strArray[14] = "BioneLMNA";
        strArray[15] = "BioneSMA";
        strArray[16] = "Bione SCA Panel";
        strArray[17] = "BioneHD";
        strArray[18] = "BioneHBB";
        strArray[19] = "BioneHBS";
        strArray[20] = "BioneHBB Trio";
        strArray[21] = "BioneAlphaThalHBA";
        strArray[22] = "BioneCFTR del508";
        strArray[23] = "BioneGenome10";
        strArray[24] = "BioneGenome20";
        strArray[25] = "Bione Whole Metagenome";
        strArray[26] = "Bione LongiFIT";
        strArray[27] = "BioneHBB+MCC";
        strArray[28] = "BioneXome + ArrayCyto 750K + Fragile X";
        strArray[29] = "Clin-Microbiome";
        strArray[30] = "BioneClinXome";
        strArray[31] = "BioneClinXome + MITO";
        strArray[32] = "BioneDMD+MCC";
        strArray[33] = "BioneXome + MCC";
        strArray[34] = "BioneClinXome  + ArrayCyto 750K";
        strArray[35] = "BioneCFH";
        strArray[36] = "BioneArrayCyto 350K";
        strArray[37] = "BioneArrayCyto 350K+MCC+QFPCR";
        strArray[38] = "BioneFriedreich's Ataxia";


//        spinner.setItems(strArray);
//        tvTestNames.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                spinner.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        String s = spinner.getSelectedItemsAsString();
//                        Log.e("getSelected", "---" + s);
//                        selectedTests = s;
//                    }
//                });
//            }
//        });


    }

    private void setRadio() {
        llBottom.setVisibility(View.GONE);
        radioText = "Towards Full payment";
        radioGroup = rootView.findViewById(R.id.radioGroup);
//        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.check(R.id.radio1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
//                int id = group.getCheckedRadioButtonId();
//                RadioButton rb = rootView.findViewById(id);
//                radioText = rb.getText().toString();
                RadioButton rb = rootView.findViewById(checkedId);
//                RadioButton rb = findViewById(checkedId);
                radioText = rb.getText().toString();
            }
        });
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvDate:
                setDate();
                break;

            case R.id.tvTestNames:
                FragmentManager fragmentManager = getChildFragmentManager();
//                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogSelection dialog = new DialogSelection();
                dialog.show(fragmentManager, "tagSeleccion");
                break;

            case R.id.tvSubmit:
                valid();
                break;

//            case R.id.ivBack:
////                finish();
//                break;
        }
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        tvDate.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void valid() {
        if (etFirstName.getText().toString().isEmpty()) {
            showErrorMessage("Please enter first name");
        } else {
            if (etLastName.getText().toString().isEmpty()) {
                showErrorMessage("Please enter last name");
            } else {
                if (selectedTests.toString().isEmpty()) {
                    showErrorMessage("Please select test name");
                } else {
                    if (etTestAmount.getText().toString().isEmpty()) {
                        showErrorMessage("Please enter test amount");
                    } else {
                        if (etAmountNumber.getText().toString().isEmpty()) {
                            showErrorMessage("Please enter discount amount");
                        } else {
                            if (etSalesPerson.getText().toString().isEmpty()) {
                                showErrorMessage("Please enter sales person");
                            } else {
                                if (radioText.toString().isEmpty()) {
                                    showErrorMessage("Please select payment type");
                                } else {
                                    if (etTotalAmount.getText().toString().isEmpty()) {
                                        showErrorMessage("Please enter total paid amount");
                                    } else {
                                        if (spinner2.getSelectedItem().toString().equals("select payment mode")) {
                                            showErrorMessage("Please select payment mode");
                                        } else {
                                            if (tvRemark.getText().toString().isEmpty()) {
                                                showErrorMessage("Please enter remark for the payment mode");
                                            } else {
                                                if (radioText.equals("PartPayment")) {// payment selcted
                                                    if (etBalanceAmount.getText().toString().isEmpty()) {
                                                        showErrorMessage("Please enter balance amount");
                                                    } else {
                                                        if (tvDate.getText().toString().isEmpty()) {
                                                            showErrorMessage("Please select Date");
                                                        } else {
                                                            submit();
                                                        }
                                                    }
                                                } else {
                                                    submit();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void submit() {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
                .add("first_name", etFirstName.getText().toString())
                .add("last_name", etLastName.getText().toString())
                .add("test_name", selectedTests.toString())
                .add("sales_person_email", CommonData.getUserData().getEmail())
                .add("amount", etAmountNumber.getText().toString())
                .add("test_amount", etTestAmount.getText().toString())
                .add("paid_amount", etTotalAmount.getText().toString())
                .add("amount_words", " " + spinner2.getSelectedItem())
                .add("balance_amount", " " + etBalanceAmount.getText().toString())
                .add("balance_amount_paid_date", " " + tvDate.getText().toString())
                .add("payment_type", radioText)
                .add("payment_mode", "" + spinner2.getSelectedItem().toString())
                .add("remarks", "" + tvRemark.getText().toString())
                .add("sales_person", CommonData.getUserData().getFirstname() + " " + CommonData.getUserData().getLastname())
                .add("report_manager_email", "" + CommonData.getSalesData().getReportingManagerEmailId())
                .build();

        RestClient.getApiInterface2().customerReceiptSubmit(commonParams.getMap()).enqueue(new ResponseResolver<PaymentReceipt>() {
            @Override
            public void onSuccess(PaymentReceipt commonResponse) {
                Log.d("onSuccess", "" + commonResponse);
//                showErrorMessage(commonResponse.getReceiptUrl());
                clearData();
                String link = commonResponse.getReceiptUrl();
                link = link.replaceAll("\\/","/");
                Log.d("link", "after slash removed------ " + link);
//                link = "http://docs.google.com/gview?embedded=true&url=" +link ;
//                Log.d("link", "after url embedded removed------ " + link);
                Intent intent = new Intent(mContext, PaymentReceiptViewActivity.class);
                intent.putExtra("link", link);
                startActivity(intent);
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

    public static class DialogSelection extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            ArrayList selectedItems = new ArrayList();
            final String[] strArray = new String[39];
            strArray[0] = "BioneXome";
            strArray[1] = "BioneMITO";
            strArray[2] = "BioneXome + MITO";
            strArray[3] = "BioneGenome30";
            strArray[4] = "BioneGenome30 + MITO";
            strArray[5] = "BioneArrayCyto 750K";
            strArray[6] = "BioneXome + ArrayCyto 750K";
            strArray[7] = "Maternal Cell Contamination";
            strArray[8] = "Fragile X repeat expansion";
            strArray[9] = "Single gene variant Testing-SangerSeq";
            strArray[10] = "BioneXomeTrio";
            strArray[11] = "BioneArrayCyto HD";
            strArray[12] = "BioneMerosin-deficient congenital muscular dystrophy type 1A";
            strArray[13] = "BioneDMD";
            strArray[14] = "BioneLMNA";
            strArray[15] = "BioneSMA";
            strArray[16] = "Bione SCA Panel";
            strArray[17] = "BioneHD";
            strArray[18] = "BioneHBB";
            strArray[19] = "BioneHBS";
            strArray[20] = "BioneHBB Trio";
            strArray[21] = "BioneAlphaThalHBA";
            strArray[22] = "BioneCFTR del508";
            strArray[23] = "BioneGenome10";
            strArray[24] = "BioneGenome20";
            strArray[25] = "Bione Whole Metagenome";
            strArray[26] = "Bione LongiFIT";
            strArray[27] = "BioneHBB+MCC";
            strArray[28] = "BioneXome + ArrayCyto 750K + Fragile X";
            strArray[29] = "Clin-Microbiome";
            strArray[30] = "BioneClinXome";
            strArray[31] = "BioneClinXome + MITO";
            strArray[32] = "BioneDMD+MCC";
            strArray[33] = "BioneXome + MCC";
            strArray[34] = "BioneClinXome  + ArrayCyto 750K";
            strArray[35] = "BioneCFH";
            strArray[36] = "BioneArrayCyto 350K";
            strArray[37] = "BioneArrayCyto 350K+MCC+QFPCR";
            strArray[38] = "BioneFriedreich's Ataxia";


            AlertDialog.Builder builder =
                    new AlertDialog.Builder(getActivity());

            builder.setTitle("Selection")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            StringBuilder sb = new StringBuilder();
                            boolean foundOne = false;
                            for (int t = 0; t < selectedItems.size(); t++) {
                                if (foundOne) {
                                    sb.append(", ");
                                }
                                foundOne = true;

                                sb.append(selectedItems.get(t));
                            }
                            selectedTests = sb;
                            tvTestNames.setText(sb.toString());
                            dialogInterface.dismiss();
                        }
                    })

                    .setMultiChoiceItems(strArray, null,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                public void onClick(DialogInterface dialog, int item, boolean isChecked) {
                                    Log.i("Dialogs", "Option el: " + strArray[item]);
                                    if (isChecked) {
                                        // If the user checked the item, add it to the selected items
                                        selectedItems.add(strArray[item]);
                                    } else if (selectedItems.contains(strArray[item])) {
                                        // Else, if the item is already in the array, remove it
                                        selectedItems.remove(strArray[item]);
                                    }
                                }
                            });


            return builder.create();
        }
    }

    private void clearData() {
        enteredValue = 0;
        actualAmount = 0;
        selectedTests = new StringBuilder();
        etFirstName.setText("");
        etLastName.setText("");
        etBalanceAmount.setText("");
        etAmountNumber.setText("");
        etTotalAmount.setText("");
        tvRemark.setText("");
        tvTestNames.setText("");
        tvDate.setText("");
        setSpinner();
        setRadio();
        etSalesPerson.setText(CommonData.getUserData().getFirstname());

    }
}
