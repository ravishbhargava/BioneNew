package com.bione.ui.dashboard.kitRegistration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bione.R;
import com.bione.ui.base.BaseFragment;

public class OrangeFragment extends BaseFragment {

    public OrangeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_barcode, container, false);
    }

    @Override
    public void onClick(View view) {

    }
}