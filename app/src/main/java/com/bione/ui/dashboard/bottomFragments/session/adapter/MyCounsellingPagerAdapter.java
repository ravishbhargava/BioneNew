package com.bione.ui.dashboard.bottomFragments.session.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bione.model.counsellors.ListItem;
import com.bione.ui.dashboard.bottomFragments.session.MyCounsellingFragment;
import com.bione.ui.dashboard.bottomFragments.session.PastFragment;
import com.bione.ui.dashboard.bottomFragments.session.UpcomingFragment;

import java.util.ArrayList;

public class MyCounsellingPagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs = 0;
    private MyCounsellingFragment.getCounsellingListListener listener;
    private ArrayList<ListItem> counsellorsList;

    public MyCounsellingPagerAdapter(@NonNull FragmentManager fm, final int numOfTabs, final MyCounsellingFragment.getCounsellingListListener listener, final ArrayList<ListItem> counsellorsList) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.listener = listener;
        this.counsellorsList = counsellorsList;
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
                return new UpcomingFragment(listener, counsellorsList);
            case 1:
                return new PastFragment(listener, counsellorsList);

            default:
                return null;
        }
    }

}
