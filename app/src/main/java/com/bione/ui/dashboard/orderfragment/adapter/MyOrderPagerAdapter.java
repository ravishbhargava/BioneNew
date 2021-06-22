package com.bione.ui.dashboard.orderfragment.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bione.ui.dashboard.orderfragment.MyKitListFragment;
import com.bione.ui.dashboard.orderfragment.MyOrderListFragment;
import com.bione.ui.dashboard.orderfragment.MyOrdersFragment;

public class MyOrderPagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs = 0;
    private MyOrdersFragment.getCounsellingListListener listener;


    public MyOrderPagerAdapter(@NonNull FragmentManager fm, final int numOfTabs, final MyOrdersFragment.getCounsellingListListener listener) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.listener = listener;

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
                return new MyKitListFragment(listener);
            case 1:
                return new MyOrderListFragment(listener);

            default:
                return null;
        }
    }

}
