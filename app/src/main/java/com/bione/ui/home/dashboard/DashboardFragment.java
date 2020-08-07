package com.bione.ui.home.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.CrouselData;
import com.bione.ui.base.BaseFragment;
import com.bione.ui.home.dashboard.banner.BannerPagerAdapter;
import com.bione.ui.home.dashboard.craousel.CenterZoomLayoutManager;
import com.bione.ui.home.dashboard.craousel.CounsellorAdapter;
import com.bione.utils.CustomViewPager;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;


public class DashboardFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private BannerPagerAdapter bannerPagerAdapter;
    private CustomViewPager viewPager;
    private Context mContext;
    private AppCompatTextView tvCustomerSupport;
    private ArrayList<CrouselData> crouselDataArrayList;

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
            tvCustomerSupport.setOnClickListener(this);
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
            }
        });

        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
