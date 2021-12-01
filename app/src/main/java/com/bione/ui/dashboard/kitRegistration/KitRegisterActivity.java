package com.bione.ui.dashboard.kitRegistration;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.anton46.stepsview.StepsView;
import com.bione.R;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.CustomViewPager;

import java.util.ArrayList;
import java.util.List;


public class KitRegisterActivity extends BaseActivity {

    //    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private StepsView mStepsView;
    String[] steps = {"Barcode", "Consents", "Questionnaire", "Finish"};

    private OnButtonClicked mOnButtonClicked;


    public static String kitBarcode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kit_register);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
//            newString= null;
        } else {
            kitBarcode = extras.getString("barcode");
        }


        mStepsView = findViewById(R.id.stepsView);
        mStepsView.setLabels(steps)
                .setBarColorIndicator(getResources().getColor(R.color.black))
                .setProgressColorIndicator(getResources().getColor(R.color.colorAccent))
                .setLabelColorIndicator(getResources().getColor(R.color.colorAccent))
                .setCompletedPosition(0)
                .drawView();

        viewPager = findViewById(R.id.viewpager);

        mOnButtonClicked = new OnButtonClicked() {
            @Override
            public void submit(int position, String name) {
                if(position==1){
                    String tag = "android:switcher:" + R.id.viewpager + ":" + 1;
                    ResearchConsentFragment f = (ResearchConsentFragment) getSupportFragmentManager().findFragmentByTag(tag);
                    f.setFirstViewData(name);
                }else if(position==2){
                    String tag = "android:switcher:" + R.id.viewpager + ":" + 1;
                    QuestionnaireFragment f = (QuestionnaireFragment) getSupportFragmentManager().findFragmentByTag(tag);
//                    f.setFirstViewData(name);
                }
                viewPager.setCurrentItem(position);
                mStepsView.setCompletedPosition(position).drawView();
            }
        };

        addTabs(viewPager);

    }


    private void addTabs(CustomViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BarcodeFragment(mOnButtonClicked), "BARCODE");
        adapter.addFrag(new ResearchConsentFragment(mOnButtonClicked), "RESEARCH");
        adapter.addFrag(new QuestionnaireFragment(), "QUEST");

        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(true);
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mStepsView.setCompletedPosition(position).drawView();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public interface OnButtonClicked {
        void submit(final int position, final String name);
    }


}