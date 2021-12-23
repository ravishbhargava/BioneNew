package com.bione.ui.dashboard.kitRegistration;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseFragment;
import com.google.android.material.textfield.TextInputEditText;

import static com.bione.utils.CommonUtil.makeTextViewResizable;

public class ResearchConsentFragment extends BaseFragment {

    private Context mContext;
    private View rootView;

    private LinearLayout firstLayout;
    private LinearLayout secondLayout;
    private AppCompatTextView tvLongText;
    private AppCompatTextView tvContinue;
    private AppCompatTextView tvContinue2;

    private TextInputEditText etName;
    private TextInputEditText etDOB;
    private TextInputEditText etGender;

    private TextInputEditText etBarcode;
    private TextInputEditText etNameSecond;
    private TextInputEditText etDOBSecond;
    private TextInputEditText etGenderSecond;

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioButton rbNo;
    private RadioButton rbYes;

    private CheckBox cbAccept;
    private KitRegisterActivity.OnButtonClicked listener;

    public ResearchConsentFragment(KitRegisterActivity.OnButtonClicked mOnButtonClicked) {
        super();
        listener = mOnButtonClicked;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_research_consent, container, false);

            init();
            setListeners();
            setFirstViewData("");

            makeTextViewResizable(tvLongText, 5, "See More", true);
        }
        return rootView;
    }

    private void init() {
        firstLayout = rootView.findViewById(R.id.firstLayout);
        secondLayout = rootView.findViewById(R.id.secondLayout);
        tvLongText = rootView.findViewById(R.id.tvLongText);
        tvContinue = rootView.findViewById(R.id.tvContinue);
        tvContinue2 = rootView.findViewById(R.id.tvContinue2);
        radioGroup = rootView.findViewById(R.id.radioGroup);
        rbYes = rootView.findViewById(R.id.rbYes);
        rbNo = rootView.findViewById(R.id.rbNo);
        etName = rootView.findViewById(R.id.etName);
        etDOB = rootView.findViewById(R.id.etDOB);
        etGender = rootView.findViewById(R.id.etGender);

        etBarcode = rootView.findViewById(R.id.etBarcode);
        etNameSecond = rootView.findViewById(R.id.etNameSecond);
        etDOBSecond = rootView.findViewById(R.id.etDOBSecond);
        etGenderSecond = rootView.findViewById(R.id.etGenderSecond);

        cbAccept = rootView.findViewById(R.id.cbAccept);

    }

    private void setListeners() {
        tvContinue.setOnClickListener(this);
        tvContinue2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvContinue:
                valid();
                break;

            case R.id.tvContinue2:
                if (cbAccept.isChecked()) {
//                    setFirstViewData();
                    listener.submit(2, "");
                } else {
                    showErrorMessage("Please tick the check box.");
                }
                break;
        }
    }

    public void setFirstViewData(String name) {
        etName.setText(KitRegisterActivity.firstName + " " + KitRegisterActivity.lastName);
        etDOB.setText(KitRegisterActivity.birthDate);
        etGender.setText(KitRegisterActivity.gender);

        secondLayout.setVisibility(View.GONE);
        firstLayout.setVisibility(View.VISIBLE);
    }

    private void setSecondViewData() {
        etBarcode.setText(KitRegisterActivity.kitBarcode);
        etNameSecond.setText(KitRegisterActivity.firstName + " "+KitRegisterActivity.lastName);
        etDOBSecond.setText(KitRegisterActivity.birthDate);
        etGenderSecond.setText(KitRegisterActivity.gender);

        secondLayout.setVisibility(View.VISIBLE);
        firstLayout.setVisibility(View.GONE);
    }

    private void valid() {
        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) rootView.findViewById(selectedId);

//        Toast.makeText(mContext, ""+radioButton.getText(), Toast.LENGTH_SHORT).show();
        if (radioButton.getText().toString() != null) {
            if (radioButton.getText().toString().equals(rbYes.getText().toString())) {
                setSecondViewData();
            } else {
                showErrorMessage("You need to agree to move forward");
            }
        } else {
            showErrorMessage("Please choose one option");
        }

    }

}
