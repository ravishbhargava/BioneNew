package com.bione.ui.mymicrobiome;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.bione.R;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.home.dashboard.banner.BannerPagerAdapter;
import com.bione.utils.CustomViewPager;
import com.bione.utils.Log;

import me.relex.circleindicator.CircleIndicator;

public class ComingSoonActivity extends BaseActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 101;
    private String emailId = "support@bione.in";
    private String mobileNumber = "+91 6366 754 050";

    private BannerPagerAdapter bannerPagerAdapter;
    private CustomViewPager viewPager;
    private Context mContext;

    private AppCompatTextView tvBody;
    private AppCompatTextView tvHead;
    private AppCompatTextView tvLink;

    private AppCompatImageView ivBack;

    private String body = "";
    private String head = "";
    private String link = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_soon);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            body = extras.getString("body");
            head = extras.getString("head");
            link = extras.getString("link");
        }


        init();


        if (body != null) {
            tvBody.setText(body);
        }
        if (head != null) {
            tvHead.setText(head);
        }

        initViewPager();

    }

    private void init() {
        ivBack = findViewById(R.id.ivBack);
        tvBody = findViewById(R.id.tvBody);
        tvHead = findViewById(R.id.tvHead);
        tvLink = findViewById(R.id.tvLink);


        ivBack.setOnClickListener(this);
        tvLink.setOnClickListener(this);
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPagingEnabled(true);


        // setting viewPager's pages
        bannerPagerAdapter = new BannerPagerAdapter(getSupportFragmentManager(), 4);
        viewPager.setAdapter(bannerPagerAdapter);
        viewPager.setCurrentItem(0);

        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        // optional
        bannerPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.tvLink:
                openDialog();
                break;
            case R.id.ivBack:
                finish();
                break;

            default:
        }
    }


    private void openDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_contact_us);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        ImageView ivMail = dialog.findViewById(R.id.ivMail);
        ImageView ivPhone = dialog.findViewById(R.id.ivPhone);

        // if button is clicked, close the custom dialog
        ivMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sendMail();
            }
        });

        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                dialog.dismiss();

                callPhoneCHeckPermission();
            }
        });

        dialog.show();
    }

    private void callPhoneCHeckPermission() {

// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
            Log.d("--------", "MY_PERMISSIONS_REQUEST_CALL_PHONE");
            // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {
            //You already have permission
            Log.d("--------", "You already have permission");

            try {
                String number = ("tel:" + mobileNumber);
                Intent mIntent = new Intent(Intent.ACTION_CALL);
                mIntent.setData(Uri.parse(number));
                startActivity(mIntent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{emailId});
        i.putExtra(Intent.EXTRA_SUBJECT, "Query: ");
        i.putExtra(Intent.EXTRA_TEXT, "");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the phone call
                    Log.d("--------", "permission was granted, yay! Do the phone call");
                    String number = ("tel:" + mobileNumber);
                    Intent mIntent = new Intent(Intent.ACTION_CALL);
                    mIntent.setData(Uri.parse(number));
                    startActivity(mIntent);
                } else {
                    Log.d("--------", "permission denied, boo! Disable the");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

}
