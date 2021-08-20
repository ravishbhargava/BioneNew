package com.bione.ui.dashboard.bottomFragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bione.R;
import com.bione.model.BannerArray;

import java.util.ArrayList;
import java.util.Objects;

public class ViewPagerAdapter extends PagerAdapter {

    // Context object
    private Context context;

    // Array of images
    private ArrayList<BannerArray> bannerArrays;

    // Layout Inflater
    private LayoutInflater mLayoutInflater;


    public ViewPagerAdapter (Context context, ArrayList<BannerArray> bannerArrays){
        this.bannerArrays = bannerArrays;
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        // return the number of images
        return bannerArrays.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflating the item.xml
        View itemView = mLayoutInflater.inflate(R.layout.card_home, container, false);

        // referencing the image view from the item.xml file
        TextView tvBoldText = (TextView) itemView.findViewById(R.id.tvBoldText);

        // setting the image in the imageView
        tvBoldText.setText("---"+position);

        // Adding the View
        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }
}
