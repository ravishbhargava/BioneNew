package com.bione.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.customerdata.Customer;
import com.bione.model.updateprofile.UpdateProfile;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseFragment;
import com.bione.utils.Log;
import com.bione.utils.ValidationUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class ProfileFragment extends BaseFragment {

    private View rootView;

    private RelativeLayout relPassword;

    private AppCompatTextView tvName;
    private AppCompatTextView tvEditFirstName;
    private AppCompatTextView tvEditLastName;
    private AppCompatTextView tvEditEmail;
    private AppCompatTextView tvEditPassword;

    private AppCompatEditText etFirstName;
    private AppCompatEditText etLastName;
    private AppCompatEditText etEmail;
    private AppCompatEditText etPassword;
    private AppCompatEditText etPhone;


    private JSONObject jsonObject = new JSONObject();
    private JSONObject customerObject = new JSONObject();
    private JSONArray  customAttributeArray = new JSONArray();
    private JSONObject customAttributeObject = new JSONObject();


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
            rootView = inflater.inflate(R.layout.fragment_profile, container, false);


            init();
            setData();

            etFirstName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        Log.i("TAG", "Enter pressed");

                        valid();
                    }
                    return false;
                }
            });

            etLastName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        Log.i("TAG", "Enter pressed");

                        valid();
                    }
                    return false;
                }
            });


            etEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        Log.i("TAG", "Enter pressed");

                        valid();
                    }
                    return false;
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

            case R.id.tvEditPassword:
            case R.id.relPassword:
                Intent intent = new Intent(mContext, ChangePasswordActivity.class);
                intent.putExtra("fromForgot", false);
                intent.putExtra("mobile", "");
                intent.putExtra("otp", "");
                startActivity(intent);
                break;

            case R.id.tvEditFirstName:
                etFirstName.setEnabled(true);
                tvEditFirstName.setText("Done");
                break;

            case R.id.tvEditLastName:
                etLastName.setEnabled(true);
                tvEditLastName.setText("Done");
                break;

            case R.id.tvEditEmail:
                etEmail.setEnabled(true);
                tvEditEmail.setText("Done");
                break;

//            case R.id.tvEditPassword:
//                etPassword.setEnabled(true);
//                tvEditPassword.setText("Done");
//                break;
        }
    }

    private void init() {
        relPassword = rootView.findViewById(R.id.relPassword);

        tvName = rootView.findViewById(R.id.tvName);
        tvEditEmail = rootView.findViewById(R.id.tvEditEmail);
        tvEditFirstName = rootView.findViewById(R.id.tvEditFirstName);
        tvEditLastName = rootView.findViewById(R.id.tvEditLastName);
        tvEditPassword = rootView.findViewById(R.id.tvEditPassword);

        etFirstName = rootView.findViewById(R.id.etFirstName);
        etLastName = rootView.findViewById(R.id.etLastName);
        etEmail = rootView.findViewById(R.id.etEmail);
        etPassword = rootView.findViewById(R.id.etPassword);
        etPhone = rootView.findViewById(R.id.etPhone);

        tvEditFirstName.setOnClickListener(this);
        tvEditLastName.setOnClickListener(this);
        tvEditEmail.setOnClickListener(this);
        tvEditPassword.setOnClickListener(this);
    }

    private void setData() {
        etFirstName.setEnabled(false);
        etLastName.setEnabled(false);
        etEmail.setEnabled(false);
        etPhone.setEnabled(false);
        etPassword.setEnabled(false);

        tvEditFirstName.setText("Edit");
        tvEditLastName.setText("Edit");
        tvEditEmail.setText("Edit");
        tvEditPassword.setText("Change");

        Customer customer = CommonData.getUserData();

        String fullname = "";

        if (customer.getMiddlename() != null) {
            if (customer.getMiddlename().toString().trim().equals("")) {
                fullname = customer.getFirstname().trim() + " " + customer.getLastname().trim();
            } else {
                fullname = customer.getFirstname().trim() + " " + customer.getMiddlename().toString().trim() + " " + customer.getLastname().trim();
            }

        } else {
            fullname = customer.getFirstname().trim() + " " + customer.getLastname().trim();
        }


        tvName.setText(fullname);
        etFirstName.setText(customer.getFirstname());
        etLastName.setText(customer.getLastname());
        etEmail.setText(customer.getEmail());
        etPhone.setText(customer.getMobilenumber());

//        if (customer.getGender() != null) {
//            if (customer.getGender().equals("0")) {
//                tvGender.setText("Male");
//            } else {
//                tvGender.setText("Female");
//            }
//        }

        if (CommonData.getCustomerToken().equals("")) {
            relPassword.setVisibility(View.GONE);
        } else {
            relPassword.setVisibility(View.VISIBLE);
        }

        Log.d("getCustomerToken", " ------------------ " + CommonData.getCustomerToken());

//        if (CommonData.getUserPhoto() != null) {
//            photoFile = new File(CommonData.getUserPhoto().get(0).getThumbnailPath());
//
//            Glide.with(mContext)
//                    .load(photoFile)
//                    .apply(RequestOptions.circleCropTransform())
//                    .into(cvProfilePic);
//        }

    }

    private void updateProfileAPI() {
        showLoading();
        createJsonObject();

        final CommonParams commonParams = new CommonParams.Builder()
                .add("Authorization", "Bearer " + CommonData.getAdminToken())
                .add("Content-Type", "application/json")
                .build();

        Log.d("headers", " data :: " + commonParams.getMap().toString());

        RequestBody body =
                RequestBody.create(MediaType.parse("text/plain"), jsonObject.toString());

        RestClient.getApiInterface().updateProfile2(
                commonParams.getMap(),
                "" + CommonData.getUserData().getEntityId(),
                body)
                .enqueue(new ResponseResolver<UpdateProfile>() {
                    @Override
                    public void onSuccess(UpdateProfile updateProfile) {

                        try {
                            Log.d("update ", "mobile :: " + updateProfile.getCustomAttributes().get(0).getValue());

                            Customer customer = CommonData.getUserData();
                            customer.setFirstname(updateProfile.getFirstname());
//                            customer.setMiddlename(updateProfile.getMiddlename());
                            customer.setLastname(updateProfile.getLastname());
                            customer.setEmail(updateProfile.getEmail());
                            customer.setWebsiteId("" + updateProfile.getWebsiteId());
                            customer.setEntityId("" + updateProfile.getId());
                            customer.setMobilenumber(updateProfile.getCustomAttributes().get(0).getValue());

                            if (updateProfile.getMiddlename() != null) {
                                customer.setMiddlename(updateProfile.getMiddlename());
                            }

                            CommonData.saveUserData(customer);

                            Log.d("common data", "mobile :: " + CommonData.getUserData().getMobilenumber());
                            setData();

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

    private void createJsonObject() {
//        {
//            "customer":{
//            "id":978,
//                    "email":"murugesan2@bione.in",
//                    "firstname":"Murugesan",
//                    "lastname":"MM",
//                    "website_id":1,
//                    "custom_attributes":[{
//                "attribute_code":"mobilenumber", "value":"6366644473"
//            }]
//        }
//        }

//        String firstname = " ";
//        String middlename = " ";
//        String lastname = " ";
//
//        String str = tvName.getText().toString().trim();
//        String[] splitStr = str.split("\\s+");
//
//        if (splitStr.length == 1) {
//            firstname = splitStr[0];
//        } else if (splitStr.length == 2) {
//            firstname = splitStr[0];
//            lastname = splitStr[1];
//        } else if (splitStr.length == 3) {
//            firstname = splitStr[0];
//            middlename = splitStr[1];
//            lastname = splitStr[2];
//        }

//        String middleName = "";
//
//        if (etMiddleName.getText().toString().equals("")) {
//            middleName = " ";
//        } else {
//            middleName = etMiddleName.getText().toString();
//        }

        jsonObject = new JSONObject();
        customerObject = new JSONObject();
        customAttributeArray = new JSONArray();
        customAttributeObject = new JSONObject();

        try {

            customAttributeObject.put("attribute_code", "mobilenumber");
            customAttributeObject.put("value", "" + etPhone.getText().toString());
//            customAttributeObject.put("value", "91" + etPhone.getText().toString());
            customAttributeArray.put(customAttributeObject);

            customerObject.put("id", "" + CommonData.getUserData().getEntityId());
            customerObject.put("email", etEmail.getText().toString());
            customerObject.put("firstname", etFirstName.getText().toString());
            customerObject.put("middlename", " ");
            customerObject.put("lastname", etLastName.getText().toString());
            customerObject.put("website_id", CommonData.getUserData().getWebsiteId());
            customerObject.put("custom_attributes", customAttributeArray);

            jsonObject.put("customer", customerObject);

            Log.d("main json object ", "data :: " + jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void valid() {
        if (TextUtils.isEmpty(etFirstName.getText().toString())) {
            showErrorMessage(R.string.error_firstname);
        } else {
            if (TextUtils.isEmpty(etLastName.getText().toString())) {
                showErrorMessage(R.string.error_lastname);
            } else {
                if (!ValidationUtil.checkEmail(etEmail.getText().toString())) {
                    showErrorMessage(R.string.error_email);
                } else {
                    updateProfileAPI();
                }
            }
        }
    }
}
