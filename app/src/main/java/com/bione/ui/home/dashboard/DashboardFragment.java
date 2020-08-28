package com.bione.ui.home.dashboard;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.CrouselData;
import com.bione.ui.base.BaseFragment;
import com.bione.ui.home.dashboard.banner.BannerPagerAdapter;
import com.bione.ui.home.dashboard.craousel.CenterZoomLayoutManager;
import com.bione.ui.home.dashboard.craousel.CounsellorAdapter;
import com.bione.ui.schedulecall.ScheduleNow;
import com.bione.utils.CustomViewPager;
import com.bione.utils.Log;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;


public class DashboardFragment extends BaseFragment implements View.OnClickListener {


    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 101;

    private View rootView;
    private BannerPagerAdapter bannerPagerAdapter;
    private CustomViewPager viewPager;
    private Context mContext;
    private AppCompatTextView tvCustomerSupport;
    private AppCompatTextView tvBookCounselling;
    private ArrayList<CrouselData> crouselDataArrayList;

    private String emailId = "support@bione.in";
    private String mobileNumber = "+91 6366 754 050";

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
            rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

            tvCustomerSupport = rootView.findViewById(R.id.tvCustomerSupport);
            tvBookCounselling = rootView.findViewById(R.id.tvBookCounselling);
            tvCustomerSupport.setOnClickListener(this);
            tvBookCounselling.setOnClickListener(this);
            setArrayList();
            onSetRecyclerView();
            initViewPager(rootView);

        }
        return rootView;
    }


    private void onSetRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        CenterZoomLayoutManager layoutManager =
                new CenterZoomLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new CounsellorAdapter(mContext, crouselDataArrayList));
        // Scroll to the position we want to snap to
        layoutManager.scrollToPosition(0);
        // Wait until the RecyclerView is laid out.
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                // Shift the view to snap  near the center of the screen.
                // This does not have to be precise.
                int dx = (recyclerView.getWidth() - recyclerView.getChildAt(0).getWidth()) / 2;
                recyclerView.scrollBy(-dx, 0);
                // Assign the LinearSnapHelper that will initially snap the near-center view.
                LinearSnapHelper snapHelper = new LinearSnapHelper();
                snapHelper.attachToRecyclerView(recyclerView);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tvCustomerSupport:
                openDialog();
                break;

            case R.id.tvBookCounselling:
                Intent intent = new Intent(mContext, ScheduleNow.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    private void initViewPager(View view) {
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPagingEnabled(true);


        // setting viewPager's pages
        bannerPagerAdapter = new BannerPagerAdapter(getChildFragmentManager(), 4);
        viewPager.setAdapter(bannerPagerAdapter);
        viewPager.setCurrentItem(0);

        CircleIndicator indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        // optional
        bannerPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());
    }

    private void setArrayList() {
        crouselDataArrayList = new ArrayList<>();

        CrouselData data = new CrouselData();
        data.setDrawable(0);
        data.setHeading("");
        data.setText("");

        CrouselData data1 = new CrouselData();
        data1.setDrawable(R.mipmap.image_longevity);
        data1.setHeading("Longevity Plus Test");
        data1.setText("World's most comprehensive high-throughput DNA test - The best investment to know how your genes " +
                "affect various health aspects for " +
                "timely management");

        CrouselData data2 = new CrouselData();
        data2.setDrawable(R.mipmap.image_microbiome);
        data2.setHeading("MyMicrobiome Test");
        data2.setText("Discover & understand your gastrointestinal microbiota and best " +
                "suited personalised diet for a " +
                "healthy & happy life.");

        CrouselData data3 = new CrouselData();
        data3.setDrawable(R.mipmap.image_genetic);
        data3.setHeading("Genetic \n" +
                "Susceptibility Test");
        data3.setText("Discover & understand how your " +
                "genes can be responsible for the susceptibility to viral infections like " +
                "SARS and Influenza.");

        CrouselData data4 = new CrouselData();
        data4.setDrawable(R.mipmap.image_clinical);
        data4.setHeading("Clinical \nGenetics Tests ");
        data4.setText("The genesis of elite\n" +
                "genetic testing");

        CrouselData data5 = new CrouselData();
        data5.setDrawable(0);
        data5.setHeading("");
        data5.setText("");

        crouselDataArrayList.add(data);
        crouselDataArrayList.add(data1);
        crouselDataArrayList.add(data2);
        crouselDataArrayList.add(data3);
        crouselDataArrayList.add(data4);
        crouselDataArrayList.add(data5);
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

            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
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

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            getActivity().finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {

            Toast.makeText(mContext, "There is no email client installed.", Toast.LENGTH_SHORT).show();
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
}
