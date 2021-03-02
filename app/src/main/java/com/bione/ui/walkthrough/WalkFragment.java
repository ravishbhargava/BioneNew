package com.bione.ui.walkthrough;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.bione.R;
import com.bione.utils.CustomTypefaceSpan;


public class WalkFragment extends Fragment {

    private View rootView;
    private String text = "Hello";
    private AppCompatTextView tvHeading;
    private AppCompatImageView ivHead;

    public WalkFragment(String text) {
        this.text = text;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_walkthrough, container, false);
            tvHeading = rootView.findViewById(R.id.tvHeading);
            ivHead = rootView.findViewById(R.id.ivHead);
            Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-Regular.ttf");
            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-Bold.ttf");
            if (text.equals("1")) {
                tvHeading.setText("Personalised Food & nutrition recommendations, which food groups you’re sensitive to, and your weight regain response.");
                SpannableStringBuilder SS = new SpannableStringBuilder(tvHeading.getText().toString());
                SS.setSpan(new CustomTypefaceSpan("", font), 0, 12, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                SS.setSpan(new CustomTypefaceSpan("", font2), 12, tvHeading.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                tvHeading.setText(SS);
//                ivHead.setImageDrawable(getActivity().getDrawable(R.drawable.image_01));
                ivHead.setBackgroundResource(R.drawable.image_01);
            } else if (text.equals("2")) {
                tvHeading.setText("Genetic-based advices with simple and actionable recommendations.");

                SpannableStringBuilder SS = new SpannableStringBuilder(tvHeading.getText().toString());
                SS.setSpan(new CustomTypefaceSpan("", font), 0, 21, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                SS.setSpan(new CustomTypefaceSpan("", font2), 21, tvHeading.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                tvHeading.setText(SS);
//                ivHead.setImageDrawable(getActivity().getDrawable(R.drawable.group));
                ivHead.setBackgroundResource(R.drawable.group);
            } else if (text.equals("3")) {
                tvHeading.setText("Speak with our team of genomic counsellors to understand your test reports.");

                SpannableStringBuilder SS = new SpannableStringBuilder(tvHeading.getText().toString());
                SS.setSpan(new CustomTypefaceSpan("", font2), 0, 23, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                SS.setSpan(new CustomTypefaceSpan("", font), 23, 43, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                SS.setSpan(new CustomTypefaceSpan("", font2), 43, tvHeading.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                tvHeading.setText(SS);
//                ivHead.setImageDrawable(getActivity().getDrawable(R.drawable.onboarding_03));
                ivHead.setBackgroundResource(R.drawable.onboarding_03);
            }


        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

//    @Override
//    public void onClick(View view) {
//
//    }
}
