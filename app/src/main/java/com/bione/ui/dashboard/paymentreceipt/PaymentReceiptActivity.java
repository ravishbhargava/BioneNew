package com.bione.ui.dashboard.paymentreceipt;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.PaymentReceipt;
import com.bione.model.salesdetail.Data;
import com.bione.model.salesdetail.SalesDetail;
import com.bione.model.testNameList.Product;
import com.bione.model.testNameList.TestNameList;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.base.BaseFragment;
import com.bione.utils.Log;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.bione.utils.AppConstant.PARAM_EMAIL;

public class PaymentReceiptActivity extends BaseActivity {


    private static StringBuilder selectedTests = new StringBuilder();
    //    private static TextInputEditText etTestName;
    private static AppCompatTextView etTestName;
    private static TextInputEditText etTestAmount;
    private AppCompatTextView etDate;
    private TextInputEditText etFirstName;
    private TextInputEditText etLastName;
    private TextInputEditText etAmountNumber;
    private TextInputEditText etSalesPerson;
    private TextInputEditText etTotalAmount;
    private TextInputEditText etPaymentMode;
    private TextInputEditText etBalanceAmount;
    private TextInputEditText etRemarks;


    private TextInputLayout layoutBalanceAmount;
    private TextInputLayout layoutTestNames;
    private TextInputLayout layoutDate;

    private AppCompatTextView tvSubmit;
    private RadioGroup radioGroup;

    private String radioText = "";
    private Spinner spinner2;
    private int enteredValue = 0;
    private int actualAmount = 0;


    private static ArrayList<Product> testNameList = new ArrayList<>();

//    private Context mContext;
//    private View rootView;


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mContext = context;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_receipt);

        init();
        testNamessList();
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

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (rootView == null) {
//            rootView = inflater.inflate(R.layout.activity_payment_receipt, container, false);
//
//            init();
//
//            setRadio();
//            setSpinner();
//            etSalesPerson.setText(CommonData.getUserData().getFirstname());
//
//            etTotalAmount.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    try {
//                        actualAmount = Integer.parseInt(etAmountNumber.getText().toString());
//                        enteredValue = Integer.parseInt(editable.toString());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    etBalanceAmount.setText("" + (actualAmount - enteredValue));
//                }
//            });
//
//        }
//        return rootView;
//    }

    private void setSpinner() {
        spinner2 = findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PaymentReceiptActivity.this,
                R.array.payment_modes, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter);

    }

    private void init() {
//        llBottom = rootView.findViewById(R.id.llBottom);
        tvSubmit = findViewById(R.id.tvSubmit);


        etTestName = findViewById(R.id.etTestName);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etTestAmount = findViewById(R.id.etTestAmount);
        etAmountNumber = findViewById(R.id.etDiscountedAmount);
        etRemarks = findViewById(R.id.etRemarks);
        etSalesPerson = findViewById(R.id.etSalesPersonName);
        etTotalAmount = findViewById(R.id.etTotalAmount);
        etBalanceAmount = findViewById(R.id.etBalanceAmount);
        etDate = findViewById(R.id.etDate);

        layoutBalanceAmount = findViewById(R.id.layoutBalanceAmount);
        layoutTestNames = findViewById(R.id.layoutTestNames);
        layoutDate = findViewById(R.id.layoutDate);

        etTestName.setOnClickListener(this);
        etDate.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        layoutDate.setOnClickListener(this);
        layoutTestNames.setOnClickListener(this);
//        tvPayment.setOnClickListener(this);
    }


    private void setRadio() {
        layoutBalanceAmount.setVisibility(View.GONE);
        radioText = "Full Amount";

        radioGroup = findViewById(R.id.radioGroup);
//        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.check(R.id.radio1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio1:
                        layoutBalanceAmount.setVisibility(View.GONE);
                        break;
                    case R.id.radio2:
                        layoutBalanceAmount.setVisibility(View.VISIBLE);
                        break;
                }

                RadioButton rb = findViewById(checkedId);
                radioText = rb.getText().toString();
                if (radioText.contains("Full")) {
                    radioText = "Full Amount";
                } else {
                    radioText = "Partial Amount";
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.etDate:
            case R.id.layoutDate:
                setDate();
                break;

            case R.id.etTestName:
            case R.id.layoutTestNames:
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogSelection dialog = new DialogSelection();
                dialog.show(fragmentManager, "tagSeleccion");
                break;

            case R.id.tvSubmit:
                valid();
                break;


        }
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(PaymentReceiptActivity.this,

                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        etDate.setText(day + "/" + (month + 1) + "/" + year);
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
                                            if (etRemarks.getText().toString().isEmpty()) {
                                                showErrorMessage("Please enter remark for the payment mode");
                                            } else {
                                                if (radioText.equals("PartPayment")) {// payment selcted
                                                    if (etBalanceAmount.getText().toString().isEmpty()) {
                                                        showErrorMessage("Please enter balance amount");
                                                    } else {
                                                        if (etDate.getText().toString().isEmpty()) {
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
                .add("balance_amount_paid_date", " " + etDate.getText().toString())
                .add("payment_type", radioText)
                .add("payment_mode", "" + spinner2.getSelectedItem().toString())
                .add("remarks", "" + etRemarks.getText().toString())
                .add("sales_person", CommonData.getUserData().getFirstname() + " " + CommonData.getUserData().getLastname())
//                .add("report_manager_email", "vipin@bione.in")
                .add("report_manager_email", "" + CommonData.getSalesData().getReportingManagerEmailId())
                .build();

        RestClient.getApiInterface2().customerReceiptSubmit(commonParams.getMap()).enqueue(new ResponseResolver<PaymentReceipt>() {
            @Override
            public void onSuccess(PaymentReceipt commonResponse) {
                Log.d("onSuccess", "" + commonResponse);

                clearData();
                String link = commonResponse.getReceiptUrl();
                link = link.replaceAll("\\/", "/");
                Log.d("link", "after slash removed------ " + link);

                Intent intent = new Intent(PaymentReceiptActivity.this, PaymentReceiptViewActivity.class);
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
            ArrayList<Integer> selectedIndexes = new ArrayList();
            final String[] strArray = new String[testNameList.size()];
//            strArray[0] = "BioneXome";
//            strArray[1] = "BioneMITO";
//            strArray[2] = "BioneXome + MITO";
//            strArray[3] = "BioneGenome30";
//            strArray[4] = "BioneGenome30 + MITO";
//            strArray[5] = "BioneArrayCyto 750K";
//            strArray[6] = "BioneXome + ArrayCyto 750K";
//            strArray[7] = "Maternal Cell Contamination";
//            strArray[8] = "Fragile X repeat expansion";
//            strArray[9] = "Single gene variant Testing-SangerSeq";
//            strArray[10] = "BioneXomeTrio";
//            strArray[11] = "BioneArrayCyto HD";
//            strArray[12] = "BioneMerosin-deficient congenital muscular dystrophy type 1A";
//            strArray[13] = "BioneDMD";
//            strArray[14] = "BioneLMNA";
//            strArray[15] = "BioneSMA";
//            strArray[16] = "Bione SCA Panel";
//            strArray[17] = "BioneHD";
//            strArray[18] = "BioneHBB";
//            strArray[19] = "BioneHBS";
//            strArray[20] = "BioneHBB Trio";
//            strArray[21] = "BioneAlphaThalHBA";
//            strArray[22] = "BioneCFTR del508";
//            strArray[23] = "BioneGenome10";
//            strArray[24] = "BioneGenome20";
//            strArray[25] = "Bione Whole Metagenome";
//            strArray[26] = "Bione LongiFIT";
//            strArray[27] = "BioneHBB+MCC";
//            strArray[28] = "BioneXome + ArrayCyto 750K + Fragile X";
//            strArray[29] = "Clin-Microbiome";
//            strArray[30] = "BioneClinXome";
//            strArray[31] = "BioneClinXome + MITO";
//            strArray[32] = "BioneDMD+MCC";
//            strArray[33] = "BioneXome + MCC";
//            strArray[34] = "BioneClinXome  + ArrayCyto 750K";
//            strArray[35] = "BioneCFH";
//            strArray[36] = "BioneArrayCyto 350K";
//            strArray[37] = "BioneArrayCyto 350K+MCC+QFPCR";
//            strArray[38] = "BioneFriedreich's Ataxia";

            for (int i = 0; i < testNameList.size(); i++) {
                strArray[i] = testNameList.get(i).getTestName();
            }

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(getActivity());

            builder.setTitle("Selection")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            StringBuilder sb = new StringBuilder();
                            boolean foundOne = false;
                            Long totalMrp = 0L;
                            for (int t = 0; t < selectedItems.size(); t++) {
                                totalMrp = totalMrp + Long.parseLong(testNameList.get(selectedIndexes.get(t)).getMrp());
                                if (foundOne) {
                                    sb.append(", ");
                                }
                                foundOne = true;

                                sb.append(selectedItems.get(t));
                            }
                            selectedTests = sb;
                            etTestName.setText(sb.toString());
                            etTestAmount.setText(totalMrp.toString());
                            dialogInterface.dismiss();
                        }
                    })

//                    .setMultiChoiceItems(testNameList, null,
                    .setMultiChoiceItems(strArray, null,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                public void onClick(DialogInterface dialog, int item, boolean isChecked) {
                                    Log.i("Dialogs", "Option el: " + testNameList.get(item).getTestName());
                                    if (isChecked) {
                                        // If the user checked the item, add it to the selected items
//                                        selectedItems.add(testNameList.get(item).getTestName());
                                        selectedItems.add(strArray[item]);
                                        selectedIndexes.add(item);
                                    } else if (selectedItems.contains(strArray[item])) {
//                                    } else if (selectedItems.contains(testNameList.get(item).getTestName())) {
                                        // Else, if the item is already in the array, remove it
//                                        selectedItems.remove(testNameList.get(item).getTestName());
                                        selectedItems.remove(strArray[item]);
                                        selectedIndexes.remove(item);
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
        etRemarks.setText("");
        etTestName.setText("");
        etDate.setText("");
        setSpinner();
        setRadio();
        etSalesPerson.setText(CommonData.getUserData().getFirstname());

    }

    public void testNamessList() {
//        final CommonParams commonParams = new CommonParams.Builder()
//                .add(PARAM_EMAIL, CommonData.getUserData().getEmail())
//                .build();

        RestClient.getApiInterface().testNames().enqueue(new ResponseResolver<List<TestNameList>>() {
            @Override
            public void onSuccess(List<TestNameList> commonResponse) {
                Log.d("onSuccess", "" + commonResponse);

                testNameList = (ArrayList<Product>) commonResponse.get(0).getProduct();
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
