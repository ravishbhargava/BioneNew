package com.bione.ui.dashboard.report.adapter;

import android.content.Context;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bione.R;
import com.bione.model.reportMyMicro.tips.Tip;

import java.util.ArrayList;
import java.util.Objects;

public class TipsPagerAdapter extends PagerAdapter {

    // Context object
    private Context context;

    // Array of images
    private ArrayList<Tip> bannerArrays;

    // Layout Inflater
    private LayoutInflater mLayoutInflater;


    public TipsPagerAdapter(Context context, ArrayList<Tip> bannerArrays) {
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
        View itemView = mLayoutInflater.inflate(R.layout.fragment_tips_report, container, false);

        // referencing the image view from the item.xml file
        TextView tvText = (TextView) itemView.findViewById(R.id.tvText);
        TextView tvPos = (TextView) itemView.findViewById(R.id.tvPos);

        // setting the image in the imageView
        tvPos.setText("0" + (position + 1));
        tvText.setText("" + Html.fromHtml(bannerArrays.get(position).getTip().trim()));

        tvText.setMovementMethod(new ScrollingMovementMethod());
        // Adding the View
        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
