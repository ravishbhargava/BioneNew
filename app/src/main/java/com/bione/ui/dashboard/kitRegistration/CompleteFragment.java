package com.bione.ui.dashboard.kitRegistration;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseFragment;

public class CompleteFragment extends BaseFragment {

    private KitRegisterActivity.OnButtonClicked listener;
    private Context mContext;
    private View rootView;
    private AppCompatTextView tvOk;

    public CompleteFragment(KitRegisterActivity.OnButtonClicked mOnButtonClicked) {
        super();
        listener = mOnButtonClicked;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            rootView = inflater.inflate(R.layout.fragment_complete, container, false);

            init();


        }
        return rootView;
    }

    private void init() {
        tvOk = rootView.findViewById(R.id.tvOk);
        tvOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvOk:
                listener.submit(4, "");
                break;
        }
    }
}
