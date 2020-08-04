package com.bione.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.bione.R;
import com.bione.ui.base.BaseFragment;
import com.bione.ui.home.banner.BannerPagerAdapter;
import com.bione.ui.home.banner.CenterZoomLayoutManager;
import com.bione.ui.home.banner.CounsellorAdapter;
import com.bione.utils.CustomViewPager;
//import com.yarolegovich.discretescrollview.DiscreteScrollView;
//import com.yarolegovich.discretescrollview.transform.Pivot;
//import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

public class DashboardFragment extends BaseFragment {

    private View rootView;
    //    private String text = "Hello";
//    private AppCompatTextView tvHeading;
//    private AppCompatImageView ivHead;
    private BannerPagerAdapter bannerPagerAdapter;
    private CustomViewPager viewPager;
    private Context mContext;

    public DashboardFragment(String text) {
//        this.text = text;
    }


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

// vertical and cycle layout
//            final CarouselLayoutManager layoutManagernager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);
//            layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
//
//            final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setAdapter(new CounsellorAdapter(mContext, "1"));
//            recyclerView.addOnScrollListener(new CenterScrollListener());

            onSetRecyclerView();

//            RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
//            // use this setting to improve performance if you know that changes
//            // in content do not change the layout size of the RecyclerView
//            recyclerView.setHasFixedSize(true);
//            // use a linear layout manager
//            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
////            recyclerView.setLayoutManager(layoutManager);
//            // specify an adapter (see also next example)
//            CounsellorAdapter mAdapter = new CounsellorAdapter(mContext, "1");
//            recyclerView.setAdapter(mAdapter);


            initViewPager(rootView);

        }
        return rootView;
    }

//    private void onSetRecyclerView() {
//        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(new CounsellorAdapter(mContext, "1"));
//        // Scroll to the position we want to snap to
//        layoutManager.scrollToPosition(1);
//        // Wait until the RecyclerView is laid out.
//        recyclerView.post(new Runnable() {
//            @Override
//            public void run() {
//                // Shift the view to snap  near the center of the screen.
//                // This does not have to be precise.
//                int dx = (recyclerView.getWidth() - recyclerView.getChildAt(0).getWidth()) / 2;
//                recyclerView.scrollBy(-dx, 0);
//                // Assign the LinearSnapHelper that will initially snap the near-center view.
//                LinearSnapHelper snapHelper = new LinearSnapHelper();
//                snapHelper.attachToRecyclerView(recyclerView);
//            }
//        });
//    }

    private void onSetRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        CenterZoomLayoutManager layoutManager =
                new CenterZoomLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CounsellorAdapter(mContext, "1"));
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

    }

    private void initViewPager(View view) {
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPagingEnabled(true);


        // setting viewPager's pages
        bannerPagerAdapter = new BannerPagerAdapter(getChildFragmentManager(), 3);
        viewPager.setAdapter(bannerPagerAdapter);
        viewPager.setCurrentItem(0);
    }
}
