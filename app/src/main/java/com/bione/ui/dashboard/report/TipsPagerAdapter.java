package com.bione.ui.dashboard.report;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bione.model.reportMyMicro.tips.Tips;


public class TipsPagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs = 0;
    private String text = "hello";
    private Tips tips;

    public TipsPagerAdapter(@NonNull FragmentManager fm, final int numOfTabs, Tips tips) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.tips = tips;
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
                return new TipsFragment(tips.getTip1(), "01");
            case 1:
                return new TipsFragment(tips.getTip2(), "02");
            case 2:
                return new TipsFragment(tips.getTip3(), "03");
            case 3:
                return new TipsFragment(tips.getTip4(), "04");
            case 4:
                return new TipsFragment(tips.getTip5(), "05");
            case 5:
                return new TipsFragment(tips.getTip6(), "06");
            default:
                return null;
        }
    }

}
