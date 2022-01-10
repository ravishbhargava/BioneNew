package com.bione.ui.dashboard.report;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.bione.R;


public class TipsFragment extends Fragment {

    private View rootView;
    private String text = "Hello";
    private String pos = "01";
    private AppCompatTextView tvText;
    private AppCompatTextView tvPos;
//    private AppCompatImageView ivHead;

    public TipsFragment(String text, String pos) {
        this.text = text;
        this.pos = pos;
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
            rootView = inflater.inflate(R.layout.fragment_tips_report, container, false);
            tvText = rootView.findViewById(R.id.tvText);
            tvPos = rootView.findViewById(R.id.tvPos);
//            ivHead = rootView.findViewById(R.id.ivHead);
            tvPos.setText(pos);
            tvText.setText(Html.fromHtml(text));
            tvText.setMovementMethod(new ScrollingMovementMethod());
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
