package com.bione.ui.onboarding.walkthrough;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseFragment;

public class WalkFragment extends BaseFragment {

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
            rootView = inflater.inflate(R.layout.fragment_walk, container, false);
            tvHeading = rootView.findViewById(R.id.tvHeading);
            ivHead = rootView.findViewById(R.id.ivHead);

            if (text.equals("1")) {
                tvHeading.setText("Personalised Food & nutrition recommendations, which food groups you’re sensitive to, and your weight regain response.");
                ivHead.setImageDrawable(getActivity().getDrawable(R.mipmap.walk1));
            } else if (text.equals("2")) {
                tvHeading.setText("Genetic-based advices with simple and actionable recommendations.");
                ivHead.setImageDrawable(getActivity().getDrawable(R.mipmap.walk2));
            } else if (text.equals("3")) {
                tvHeading.setText("Speak with our team of genomic counsellors to understand your test reports.");
                ivHead.setImageDrawable(getActivity().getDrawable(R.mipmap.walk3));
            }


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
}
