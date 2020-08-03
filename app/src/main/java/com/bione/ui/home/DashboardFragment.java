package com.bione.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.bione.R;
import com.bione.ui.base.BaseFragment;
import com.bione.ui.home.banner.BannerPagerAdapter;
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


//            // vertical and cycle layout
//            final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true);
//            layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
//
//            final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setAdapter(new CounsellorAdapter("1"));
//            recyclerView.addOnScrollListener(new CenterScrollListener());

//            final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
//            layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

            RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            recyclerView.setHasFixedSize(true);
            // use a linear layout manager
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
//            recyclerView.setLayoutManager(layoutManager);
            // specify an adapter (see also next example)
            CounsellorAdapter mAdapter = new CounsellorAdapter(mContext, "1");
            recyclerView.setAdapter(mAdapter);


//            final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
//
//            final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setLayoutManager(layoutManager);
//
//            recyclerView.setAdapter(new GalleryAdapter());
//            recyclerView.addOnScrollListener(new CenterScrollListener());
//            layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());


//            DiscreteScrollView scrollView = rootView.findViewById(R.id.picker);
//            scrollView.setAdapter(new GalleryAdapter());
//
////            scrollView.setOrientation(DSVOrientation o); //Sets an orientation of the view
//            scrollView.setOffscreenItems(3); //Reserve extra space equal to (childSize * count) on each side of the view
//
//            scrollView.getCurrentItem(); //returns adapter position of the currently selected item or -1 if adapter is empty.
//            scrollView.scrollToPosition(scrollView.getCurrentItem()); //position becomes selected
//            scrollView.smoothScrollToPosition(scrollView.getCurrentItem()); //position becomes selected with animated scroll
//            scrollView.setItemTransitionTimeMillis(200);
//
//            scrollView.setItemTransformer(new ScaleTransformer.Builder()
//                    .setMaxScale(1.05f)
//                    .setMinScale(0.8f)
//                    .setPivotX(Pivot.X.CENTER) // CENTER is a default one
//                    .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
//                    .build());

            initViewPager(rootView);

        }
        return rootView;
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
