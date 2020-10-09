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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.CrouselData;
import com.bione.model.customerkit.CustomerKit;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseFragment;
import com.bione.ui.home.dashboard.banner.BannerPagerAdapter;
import com.bione.ui.home.dashboard.craousel.CounsellorAdapter;
import com.bione.ui.schedulecall.CategorySelect;
import com.bione.utils.CenterZoomLayoutManager;
import com.bione.utils.CustomViewPager;
import com.bione.utils.Log;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

import static com.bione.utils.AppConstant.PARAM_CUSTOMER;
import static com.bione.utils.AppConstant.SUCCESS;


public class DashboardFragment extends BaseFragment implements View.OnClickListener {


    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 101;
    private String emailId = "support@bione.in";
    private String mobileNumber = "+91 6366 754 050";

    private View rootView;
    private BannerPagerAdapter bannerPagerAdapter;
    private CustomViewPager viewPager;
    private Context mContext;
    private AppCompatTextView tvCustomerSupport;
    private AppCompatTextView tvBookCounselling;
    private ArrayList<CrouselData> crouselDataArrayList;

    private AppCompatImageView ivFacebook;
    private AppCompatImageView ivInsta;
    private AppCompatImageView ivTwitter;
    private AppCompatImageView ivYoutube;
    private AppCompatImageView ivLinkedIn;

    private int kitOrderSize = 0;

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

            init(rootView);
            setListeners();

            setArrayList();
            callAPI();
//            onSetRecyclerView();
            initViewPager(rootView);

        }
        return rootView;
    }

    private void onSetRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        CenterZoomLayoutManager layoutManager =
                new CenterZoomLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new CounsellorAdapter(mContext, crouselDataArrayList, kitOrderSize));
        // Scroll to the position we want to snap to
        layoutManager.scrollToPosition(1);
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

    private void init(View view) {
        ivFacebook = view.findViewById(R.id.ivFacebook);
        ivInsta = view.findViewById(R.id.ivInsta);
        ivTwitter = view.findViewById(R.id.ivTwitter);
        ivYoutube = view.findViewById(R.id.ivYoutube);
        ivLinkedIn = view.findViewById(R.id.ivLinkedIn);
    }

    private void setListeners() {
        ivFacebook.setOnClickListener(this);
        ivInsta.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
        ivYoutube.setOnClickListener(this);
        ivLinkedIn.setOnClickListener(this);
    }

    private void openLink(final String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tvCustomerSupport:
                openDialog();
                break;

            case R.id.tvBookCounselling:
                Intent intent = new Intent(mContext, CategorySelect.class);
                intent.putExtra("position", 1);
                intent.putParcelableArrayListExtra("array", crouselDataArrayList);
                startActivity(intent);
                break;

            case R.id.ivFacebook:
                openLink(getResources().getString(R.string.facebook_link));
                break;

            case R.id.ivTwitter:
                openLink(getResources().getString(R.string.twitter_link));
                break;

            case R.id.ivInsta:
                openLink(getResources().getString(R.string.instagram_link));
                break;

            case R.id.ivLinkedIn:
                openLink(getResources().getString(R.string.linkedin_link));
                break;

            case R.id.ivYoutube:
                openLink(getResources().getString(R.string.youtube_link));
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
        data.setDrawableTest(0);
        data.setHeadingTest("");
        data.setHeading("");
        data.setText("");
        data.setIschecked(false);
        data.setNameCounsellor("");
        data.setTypeCounsellor("");

        CrouselData data1 = new CrouselData();
        data1.setDrawable(R.mipmap.image_longevity);
        data1.setDrawableTest(R.drawable.ic_test_longevity);
        data1.setHeadingTest("Longevity Plus\nTest");
        data1.setHeading("Longevity Plus Test");
        data1.setText("World's most comprehensive high-throughput DNA test - The best investment to know how your genes " + "affect various health aspects for " + "timely management");
        data1.setIschecked(false);
        data1.setNameCounsellor("Adrija Mishra");
        data1.setTypeCounsellor("Longevity Plus Test");

        CrouselData data2 = new CrouselData();
        data2.setDrawable(R.mipmap.image_microbiome);
        data2.setDrawableTest(R.mipmap.ic_test_micro);
        data2.setHeadingTest("MyMicrobiome\nTest");
        data2.setHeading("MyMicrobiome Test");
        data2.setText("Discover & understand your gastrointestinal microbiota and best " + "suited personalised diet for a " + "healthy & happy life.");
        data2.setIschecked(false);
        data2.setNameCounsellor("Tanya");
        data2.setTypeCounsellor("MyMicrobiome Test");

        CrouselData data3 = new CrouselData();
        data3.setDrawable(R.mipmap.image_longifit);
        data3.setDrawableTest(R.drawable.ic_test_longifit);
        data3.setHeadingTest("LongiFit\nTest");
        data3.setHeading("LongiFit");
        data3.setText("Get deep insight into DNA. Understand how your body " + "responds to sports, dietary needs, food reactions, skin health & " + "overall fitness.");
        data3.setIschecked(false);
        data3.setNameCounsellor("Adrija Mishra");
        data3.setTypeCounsellor("LongiFit Test");

        CrouselData data4 = new CrouselData();
        data4.setDrawable(R.mipmap.imge_gene_chek);
        data4.setDrawableTest(R.drawable.ic_test_genecheck);
        data4.setHeadingTest("Gene-Check\nTest");
        data4.setHeading("Bione Gene-Check");
        data4.setText("Discover & understand how your " + "genes can be responsible for the susceptibility to viral infections like " + "SARS and Influenza.");
        data4.setIschecked(false);
        data4.setNameCounsellor("Adrija Mishra");
        data4.setTypeCounsellor("Gene-Check Test");

        CrouselData data5 = new CrouselData();
        data5.setDrawable(R.mipmap.image_clinical);
        data5.setDrawableTest(R.drawable.ic_test_genetic);
        data5.setHeadingTest("Genetic\nTest");
        data5.setHeading("Clinical \nGenetics Tests ");
        data5.setText("The genesis of elite\n" + "genetic testing");
        data5.setIschecked(false);
        data5.setNameCounsellor("Adrija Mishra");
        data5.setTypeCounsellor("Genetic Test");

        CrouselData data6 = new CrouselData();
        data6.setDrawable(0);
        data6.setDrawableTest(0);
        data6.setHeadingTest("");
        data6.setHeading("");
        data6.setText("");
        data6.setIschecked(false);
        data6.setNameCounsellor("");
        data6.setTypeCounsellor("");

        crouselDataArrayList.add(data);
        crouselDataArrayList.add(data2);
        crouselDataArrayList.add(data1);
        crouselDataArrayList.add(data3);
        crouselDataArrayList.add(data4);
        crouselDataArrayList.add(data5);
        crouselDataArrayList.add(data6);
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



    private void callAPI() {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_CUSTOMER, "" + CommonData.getUserData().getEntityId())
//                .add(PARAM_CUSTOMER, "36")
                .build();

        RestClient.getApiInterface().kitOrders(commonParams.getMap()).enqueue(new ResponseResolver<List<CustomerKit>>() {
            @Override
            public void onSuccess(List<CustomerKit> customerKits) {

                if (customerKits.get(0).getCode() == SUCCESS) {
                    try {

                        Log.d("customer kit ordered", " size :: " + customerKits.get(0).getKitOrders().size());
                        kitOrderSize = customerKits.get(0).getKitOrders().size();
                        onSetRecyclerView();
                        // specify an adapter (see also next example)
//                        kitOrders = (ArrayList<KitOrder>) customerKits.get(0).getKitOrders();
//                        initRecycler();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (customerKits.get(0).getCode() == 404) {
//                    showErrorMessage("No Reports");
                    kitOrderSize = 0;
                    onSetRecyclerView();
                } else {
                    showErrorMessage(customerKits.get(0).getMessage());
                    kitOrderSize = 0;
                    onSetRecyclerView();
                }
            }

            @Override
            public void onError(ApiError error) {
                Log.d("onError", "" + error);
                showErrorMessage(error.getMessage());
                kitOrderSize = 0;
                onSetRecyclerView();
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                showErrorMessage(throwable.getMessage());
                kitOrderSize = 0;
                onSetRecyclerView();
            }
        });
    }
}
