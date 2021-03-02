package com.bione.ui.dashboard;

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


public class FaqFragment extends BaseFragment {

    private View rootView;
    private String text = "Hello";
    private AppCompatTextView tvHeading;
    private AppCompatTextView tvChat;
    private AppCompatImageView ivHead;

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
            rootView = inflater.inflate(R.layout.fragment_faq, container, false);
            tvHeading = rootView.findViewById(R.id.tvHeading);
            ivHead = rootView.findViewById(R.id.ivHead);
            tvChat = rootView.findViewById(R.id.tvChat);

            tvChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ZohoSalesIQ.Chat.show();

                }
            });

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
