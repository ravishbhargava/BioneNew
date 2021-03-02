package com.bione.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bione.R;
import com.bione.ui.base.BaseFragment;
import com.bione.ui.dashboard.bottomFragments.HomeFragment;
import com.bione.ui.dashboard.bottomFragments.MyReportFragment;
import com.bione.ui.dashboard.bottomFragments.SessionsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class DashBoardFragment extends BaseFragment {

    private View rootView;
    private String text = "Hello";
    private AppCompatTextView tvHeading;
    private AppCompatTextView tvChat;
    private AppCompatImageView ivHead;
    private BottomNavigationView bottomNavigationView;

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
            rootView = inflater.inflate(R.layout.fragment_dash, container, false);
            tvHeading = rootView.findViewById(R.id.tvHeading);
            ivHead = rootView.findViewById(R.id.ivHead);

            openFragment(new HomeFragment());

            bottomNavigationView = rootView.findViewById(R.id.bottomNavigationView);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {

                        case R.id.page_1:
                            openFragment(new HomeFragment());
                            break;

                        case R.id.page_2:
                            openFragment(new MyReportFragment());
                            break;

                        case R.id.page_3:
                            openFragment(new SessionsFragment());
                            break;
                    }


                    return true;
                }
            });

        }
        return rootView;
    }

    private void openFragment(Fragment fragment) {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View view) {

    }
}
