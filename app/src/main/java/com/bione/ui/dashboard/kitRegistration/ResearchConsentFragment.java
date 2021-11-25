package com.bione.ui.dashboard.kitRegistration;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseFragment;

import static com.bione.utils.CommonUtil.makeTextViewResizable;

public class ResearchConsentFragment extends BaseFragment {

    private Context mContext;
    private View rootView;

    private LinearLayout firstLayout;
    private LinearLayout secondLayout;
    private AppCompatTextView tvLongText;
    private AppCompatTextView tvContinue;

    private boolean isClicked = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_research_consent);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_research_consent, container, false);

            init();
            setListeners();

            makeTextViewResizable(tvLongText, 5, "See More", true);
        }
        return rootView;
    }

    private void init() {
        firstLayout = rootView.findViewById(R.id.firstLayout);
        secondLayout = rootView.findViewById(R.id.secondLayout);
        tvLongText = rootView.findViewById(R.id.tvLongText);
        tvContinue = rootView.findViewById(R.id.tvContinue);
    }

    private void setListeners() {
        tvContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvContinue:
                if (isClicked) {

                    secondLayout.setVisibility(View.VISIBLE);
                    firstLayout.setVisibility(View.GONE);
                    isClicked = false;
                } else {
                    secondLayout.setVisibility(View.GONE);
                    firstLayout.setVisibility(View.VISIBLE);
                    isClicked = true;
                }
                break;
        }
    }
}
