package com.bione.ui.dashboard.report.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bione.ui.dashboard.report.GutMaintenanceFragment;
import com.bione.ui.dashboard.report.GutRestorationFragment;

import java.util.ArrayList;

public class MyGutPagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs = 0;
    private ArrayList<String> arrayList;


    public MyGutPagerAdapter(@NonNull FragmentManager fm, final int numOfTabs, final ArrayList<String> arrayList) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.arrayList = arrayList;

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new GutRestorationFragment(arrayList);
            case 1:
                return new GutMaintenanceFragment(arrayList);

            default:
                return null;
        }
    }

}
