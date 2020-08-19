package com.bione.ui.home.dashboard.banner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseFragment;

public class BannerFragment extends BaseFragment {

    private View rootView;
    private String text = "Hello";
    private AppCompatTextView tvHeading;
    private AppCompatImageView ivHead;

    private RelativeLayout root;

    public BannerFragment(String text) {
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
            rootView = inflater.inflate(R.layout.fragment_banner, container, false);
            root = rootView.findViewById(R.id.root);
            tvHeading = rootView.findViewById(R.id.tvHeading);
            ivHead = rootView.findViewById(R.id.ivHead);


            if (text.equals("1")) {
//                tvHeading.setText(R.string.dummy_text);
                ivHead.setImageResource(R.mipmap.banner_1);
            } else if (text.equals("2")) {
//                tvHeading.setText(R.string.dummy_text);
                ivHead.setImageResource(R.mipmap.banner_2);
            } else if (text.equals("3")) {
//                tvHeading.setText(R.string.dummy_text);
                ivHead.setImageResource(R.mipmap.banner_3);
            } else if (text.equals("4")) {
//                tvHeading.setText(R.string.dummy_text);
                ivHead.setImageResource(R.mipmap.banner_4);
            }

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bione.in"));
                    startActivity(browserIntent);
                }
            });

        }
        return rootView;
    }

    private void setView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivHead.setImageDrawable(getResources().getDrawable(R.mipmap.banner_1, getActivity().getTheme()));
        } else {
            ivHead.setImageDrawable(getResources().getDrawable(R.mipmap.banner_1));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {

    }
}
