package com.bione.ui.walkthrough;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class WalkPagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs = 0;
    private String text = "hello";

    public WalkPagerAdapter(@NonNull FragmentManager fm, final int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;

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
                text = "1";
                return new WalkFragment(text);
            case 1:
                text = "2";
                return new WalkFragment(text);
            case 2:
                text = "3";
                return new WalkFragment(text);

            default:
                return null;
        }
    }

}
