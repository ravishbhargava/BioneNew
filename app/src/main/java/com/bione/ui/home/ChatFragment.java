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
    private String appKey = "XZvO8GN1MbRGsWM1JGgeg4Pu6viEjyr1MCtqCpybpWoTgmKUziZvfHQSEz%2FDTDscwpIPqxSDE%2B5oxJgLStg6MxPtdsHPHvQX_in";
    private String accessKey = "AS1%2FBz1totGY%2FTeDIGNx2oF8S4XGBoYBnudWIif9tjWJLZpHs%2FqILAvw02b73G9CxwP5tH5b9E4wWhWL%2FLoOnxkrmqJXPpesVs4b2A0TNs%2BJL95PkeITbcK6NF0LTRbG4wdX6vX3qryyPUjYzuRFgg%3D%3D";

    private Context mContext;

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
//        ZohoSalesIQ.Chat.show();
//        ZohoSalesIQ.init((Application) MyApplication.getAppContext(), appKey, accessKey);
//        ZohoSalesIQ.init((Application) getappl, appKey, accessKey);
    }

    @Override
    public void onClick(View view) {

    }
}
