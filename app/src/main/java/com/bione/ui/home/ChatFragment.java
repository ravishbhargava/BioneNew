package com.bione.ui.home;

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

public class ChatFragment extends BaseFragment {

    private View rootView;
    private String text = "Hello";
    private AppCompatTextView tvHeading;
    private AppCompatImageView ivHead;

    public ChatFragment(String text) {
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
                tvHeading.setText(R.string.dummy_text);
                ivHead.setImageDrawable(getActivity().getDrawable(R.mipmap.walk1));
            } else if (text.equals("2")) {
                tvHeading.setText(R.string.dummy_text);
                ivHead.setImageDrawable(getActivity().getDrawable(R.mipmap.walk2));
            } else if (text.equals("3")) {
                tvHeading.setText(R.string.dummy_text);
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
