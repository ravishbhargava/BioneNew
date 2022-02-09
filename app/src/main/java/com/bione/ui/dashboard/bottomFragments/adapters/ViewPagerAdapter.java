package com.bione.ui.dashboard.bottomFragments.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bione.R;
import com.bione.model.BannerArray;
import com.bione.ui.dashboard.report.ReportFrontPageActivity;

import java.util.ArrayList;
import java.util.Objects;

public class ViewPagerAdapter extends PagerAdapter {

    // Context object
    private Context context;

    // Array of images
    private ArrayList<BannerArray> bannerArrays;

    // Layout Inflater
    private LayoutInflater mLayoutInflater;


    public ViewPagerAdapter(Context context, ArrayList<BannerArray> bannerArrays) {
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

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReportFrontPageActivity.class);
                context.startActivity(intent);
            }
        });
        // referencing the image view from the item.xml file
        TextView tvBoldText = (TextView) itemView.findViewById(R.id.tvBoldText);

        // setting the image in the imageView
        tvBoldText.setText("---" + position);
        setData(position, tvBoldText);

        // Adding the View
        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((LinearLayout) object);
    }

    private void setData(final int position, final TextView tvBoldText) {
        String kitName = "";
        String stageName = "";
        String status = "";
//        boolean stop = false;

        BannerArray banner = bannerArrays.get(position);

        kitName = banner.getKitName();

        if (banner.getActivationStatus().equals("0")) {
            stageName = "Activation";
            status = "pending";
//            stop = true;
        } else {
//            stop = false;
            stageName = "Activation";
            status = "completed";

            if (banner.getSampleRegistrationDate() != null && !banner.getSampleRegistrationDate().equals("")) {
                stageName = "registration";
                status = "completed";
                if (banner.getKitShippedDate() != null && !banner.getKitShippedDate().equals("")) {
                    stageName = "shipping";
                    status = "completed";
                    if (banner.getDateOfSampleReceipt() != null && !banner.getDateOfSampleReceipt().equals("")) {
                        stageName = "sample receipt";
                        status = "completed";
                        if (banner.getSampleProcessingDate() != null && !banner.getSampleProcessingDate().equals("")) {
                            stageName = "processing";
                            status = "completed";
                            if (banner.getAnalysis().equals("Approved")) {
                                stageName = "analysis";
                                status = "completed";
                                if (banner.getFoodRecommendation().equals("Approved")) {
                                    stageName = "food recommendation";
                                    status = "completed";
                                    if (banner.getTips().equals("Approved")) {
                                        stageName = "report tips";
                                        status = "completed";
                                        if (banner.getFoodSupplements().equals("Approved")) {
                                            stageName = "food supplement";
                                            status = "completed";
                                            if (banner.getGutrestoration().equals("Approved")) {
                                                stageName = "gutrestoration diet chart";
                                                status = "completed";
                                                if (banner.getGutmaintenance().equals("Approved")) {
                                                    stageName = "gutmaintenance diet chart";
                                                    status = "completed";
                                                    if (banner.getReportStatus().equals("Approved")) {
                                                        stageName = "report status";
                                                        status = "completed";
                                                        if (banner.getReleasedDateOfReport() != null && !banner.getReleasedDateOfReport().equals("")) {
                                                            stageName = "report";
                                                            status = "released";
                                                        } else {
                                                            //return;
                                                        }
                                                    } else {
                                                        //return;
                                                    }
                                                } else {
                                                    //return;
                                                }
                                            } else {
                                                //return;
                                            }
                                        } else {
                                            //return;
                                        }
                                    } else {
                                        //return;
                                    }
                                } else {
                                    //return;
                                }
                            }
                        }
                    }
                }
            }
        }

        tvBoldText.setText("Your " + kitName + " " + stageName + " is " + status);
    }

//    private void setText() {
//        String first = "Hi " + name + " \n";
//        String second = "We understand how \npriceless your health is!";
//        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Regular.ttf");
//        Typeface font2 = Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Bold.ttf");
//        tvHead.setText(first + second);
//        SpannableStringBuilder SS = new SpannableStringBuilder(tvHead.getText().toString());
//        SS.setSpan(new CustomTypefaceSpan("", font), 0, tvHead.getText().toString().length() - 27, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        SS.setSpan(new CustomTypefaceSpan("", font2), tvHead.getText().toString().length() - 27, tvHead.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        tvHead.setText(SS);
//    }
}
