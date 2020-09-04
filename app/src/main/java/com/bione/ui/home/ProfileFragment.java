package com.bione.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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
import com.bione.utils.ImageChooser;
import com.bione.utils.Log;
import com.bione.utils.ValidationUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ProfileFragment extends BaseFragment implements ImageChooser.OnImageSelectListener {

    private View rootView;
    private String text = "Hello";
    private AppCompatTextView tvHeading;
    private AppCompatTextView tvUpdate;

    private AppCompatEditText tvName;
    private AppCompatEditText tvContactNumber;
    private AppCompatEditText tvEmail;
    private AppCompatTextView tvGender;
    private AppCompatTextView tvPassword;
    private View vPassword;
    private View vEmail;
    private View vName;

    private AppCompatImageView ivHead;
    private Context mContext;

    private ImageChooser imageChooser;
    private File photoFile;
    private Uri uriImage;
    private String imageLink;
    private CircleImageView cvProfilePic;
    private AppCompatImageView ivAddProfile;

    private JSONObject jsonObject = new JSONObject();
    private JSONObject customerObject = new JSONObject();
    private JSONArray customAttributeArray = new JSONArray();
    private JSONObject customAttributeObject = new JSONObject();

    private String currentPassword = "";
    private String newPassword = "";

    private RelativeLayout relPassword;

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
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void init() {
        tvHeading = rootView.findViewById(R.id.tvHeading);
        tvUpdate = rootView.findViewById(R.id.tvUpdate);
        ivHead = rootView.findViewById(R.id.ivHead);

        tvEmail = rootView.findViewById(R.id.tvEmail);
        tvName = rootView.findViewById(R.id.tvName);
        tvGender = rootView.findViewById(R.id.tvGender);
        tvContactNumber = rootView.findViewById(R.id.tvContactNumber);
        tvPassword = rootView.findViewById(R.id.tvPassword);

        vPassword = rootView.findViewById(R.id.vPassword);
        vEmail = rootView.findViewById(R.id.vEmail);
        vName = rootView.findViewById(R.id.vName);

        relPassword = rootView.findViewById(R.id.relPassword);

        cvProfilePic = rootView.findViewById(R.id.cvProfilePic);
        ivAddProfile = rootView.findViewById(R.id.ivAddProfile);

        ivAddProfile.setOnClickListener(this);
        tvUpdate.setOnClickListener(this);
        vName.setOnClickListener(this);
        vEmail.setOnClickListener(this);
        vPassword.setOnClickListener(this);

    }

    private void setData() {
        tvEmail.setEnabled(false);
        tvName.setEnabled(false);

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


        tvName.setText(fullname.trim());
        tvEmail.setText(customer.getEmail());
        tvContactNumber.setText(customer.getMobilenumber());

        if (customer.getGender() != null) {
            if (customer.getGender().equals("0")) {
                tvGender.setText("Male");
            } else {
                tvGender.setText("Female");
            }
        }

        if (CommonData.getCustomerToken().equals("")) {
            relPassword.setVisibility(View.GONE);
        } else {
            relPassword.setVisibility(View.VISIBLE);
        }

        Log.d("getCustomerToken", " ------------------ " + CommonData.getCustomerToken());

        if (CommonData.getUserPhoto() != null) {
            photoFile = new File(CommonData.getUserPhoto().get(0).getThumbnailPath());

            Glide.with(mContext)
                    .load(photoFile)
                    .apply(RequestOptions.circleCropTransform())
                    .into(cvProfilePic);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAddProfile:
                imagePicker();
                break;

            case R.id.tvUpdate:
//                openDialog();
                if (TextUtils.isEmpty(tvName.getText().toString())) {
                    showErrorMessage(R.string.error_name);
                } else {
                    if (!ValidationUtil.checkEmail(tvEmail.getText().toString())) {
                        showErrorMessage(R.string.error_email);
                    } else {
//                            callAPI();
                        updateProfileAPI();
                    }
                }
                break;

            case R.id.vName:
                if (tvName.isEnabled()) {
                    tvName.setEnabled(false);
                } else {
                    tvName.setEnabled(true);
                }

                break;

            case R.id.vEmail:
                if (tvEmail.isEnabled()) {
                    tvEmail.setEnabled(false);
                } else {
                    tvEmail.setEnabled(true);
                }
                break;

            case R.id.vPassword:
                openDialog();
                break;

            default:
                break;
        }
    }

    /**
     * Image picker
     */
    private void imagePicker() {
        imageChooser = new ImageChooser.Builder(this).setCropEnabled(false).build();
        imageChooser.selectImage(new ImageChooser.OnImageSelectListener() {
            @Override
            public void loadImage(final List<ChosenImage> list) {
                CommonData.updateUserPhoto(list);
                photoFile = new File(list.get(0).getThumbnailPath());
                uriImage = Uri.parse(list.get(0).getQueryUri());
                if (uriImage != null) {
                    Glide.with(mContext)
                            .load(uriImage)
                            .apply(RequestOptions.circleCropTransform())
                            .into(cvProfilePic);
                }
            }

            @Override
            public void croppedImage(final File mCroppedImage) {
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        imageChooser.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent data) {
        if (imageChooser != null) {
            imageChooser.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void loadImage(List<ChosenImage> list) {
        CommonData.updateUserPhoto(list);

        photoFile = new File(list.get(0).getThumbnailPath());

        Glide.with(mContext)
                .load(photoFile)
                .apply(RequestOptions.circleCropTransform())
                .into(cvProfilePic);

    }

    @Override
    public void croppedImage(File mCroppedImage) {

    }

    public void savefile(String name, String path) {
        File direct = new File(Environment.getExternalStorageDirectory() + "/MyAppFolder/MyApp/");
        File file = new File(Environment.getExternalStorageDirectory() + "/MyAppFolder/MyApp/" + name + ".png");

        if (!direct.exists()) {
            direct.mkdir();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
                FileChannel src = new FileInputStream(path).getChannel();
                FileChannel dst = new FileOutputStream(file).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/DirName");

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/DirName/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File("/sdcard/DirName/", fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveImageToExternal(String imgName, Bitmap bm) throws IOException {
        //Create Path to save Image
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "bionenew"); //Creates app specific folder
        path.mkdirs();
        File imageFile = new File(path, imgName + ".png"); // Imagename.png
        FileOutputStream out = new FileOutputStream(imageFile);
        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
            out.flush();
            out.close();

            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(mContext, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
                }
            });
        } catch (Exception e) {
            throw new IOException();
        }
    }


    private void openDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_password);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        AppCompatEditText etCurrentPassword = dialog.findViewById(R.id.etCurrentPassword);
        AppCompatEditText etNewPassword = dialog.findViewById(R.id.etNewPassword);
        AppCompatEditText etConfirmPassword = dialog.findViewById(R.id.etConfirmPassword);

        AppCompatTextView tvOk = dialog.findViewById(R.id.tvOk);


        // if button is clicked, close the custom dialog
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etCurrentPassword.getText().toString().equals("")) {
                    showErrorMessage("Please enter current password");
                } else {
                    if (etNewPassword.getText().toString().equals("")) {
                        showErrorMessage("Please enter new password");
                    } else {
                        if (etConfirmPassword.getText().toString().equals("")) {
                            showErrorMessage("Please enter confirm password");
                        } else {
                            if (etConfirmPassword.getText().toString().equals(etNewPassword.getText().toString())) {
//                                updatePasswordAPI();
                                currentPassword = etCurrentPassword.getText().toString();
                                newPassword = etNewPassword.getText().toString();
                                updatePasswordAPI();
                                dialog.dismiss();

                            } else {
                                showErrorMessage("Password does not match");
                            }
                        }
                    }
                }


            }
        });

        dialog.show();
    }

    private void updatePasswordAPI() {
        showLoading();
        createPasswordObject();

        final CommonParams commonParams = new CommonParams.Builder()
                .add("Authorization", "Bearer " + CommonData.getCustomerToken())
                .add("Content-Type", "application/json")
                .build();

        final CommonParams commonParams2 = new CommonParams.Builder()
                .add("customerId", CommonData.getUserData().getEntityId())
                .build();

        Log.d("headers", " data :: " + commonParams.getMap().toString());
        Log.d("params", " data :: " + commonParams2.getMap().toString());

        RequestBody body =
                RequestBody.create(MediaType.parse("text/plain"), jsonObject.toString());

        RestClient.getApiInterface().changePassword(
                commonParams.getMap(),
                commonParams.getMap(),
                body)
                .enqueue(new ResponseResolver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isChanged) {

                        try {
                            if (isChanged)
                                showErrorMessage("Password changed successfully");
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


    private void createPasswordObject() {
//        {
//            "currentPassword": "Mmmmm@1234",
//                "newPassword": "Mmmmm@123"
//        }
        jsonObject = new JSONObject();

        try {
            jsonObject.put("currentPassword", currentPassword);
            jsonObject.put("newPassword", newPassword);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("jsonObject", "data :: " + jsonObject.toString());

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

        String firstname = " ";
        String middlename = " ";
        String lastname = " ";

        String str = tvName.getText().toString().trim();
        String[] splitStr = str.split("\\s+");

        if (splitStr.length == 1) {
            firstname = splitStr[0];
        } else if (splitStr.length == 2) {
            firstname = splitStr[0];
            lastname = splitStr[1];
        } else if (splitStr.length == 3) {
            firstname = splitStr[0];
            middlename = splitStr[1];
            lastname = splitStr[2];
        }

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
            customAttributeObject.put("value", tvContactNumber.getText().toString());
            customAttributeArray.put(customAttributeObject);

            customerObject.put("id", "" + CommonData.getUserData().getEntityId());
            customerObject.put("email", tvEmail.getText().toString());
            customerObject.put("firstname", firstname);
            customerObject.put("middlename", middlename);
            customerObject.put("lastname", lastname);
            customerObject.put("website_id", CommonData.getUserData().getWebsiteId());
            customerObject.put("custom_attributes", customAttributeArray);

            jsonObject.put("customer", customerObject);

            Log.d("main json object ", "data :: " + jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
