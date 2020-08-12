package com.bione.ui.schedulecall;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;

public class CounsellingConfirm extends BaseActivity {

    private AppCompatTextView tvConfirm;
    private AppCompatImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cousel_confirm);


        tvConfirm = findViewById(R.id.tvConfirm);
        ivBack = findViewById(R.id.ivBack);
        tvConfirm.setOnClickListener(this);
        ivBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tvConfirm:
                openDialog();
                break;

            case R.id.ivBack:
                finish();
                break;

            default:
                break;
        }
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
            }
        });

        dialog.show();
    }

}
