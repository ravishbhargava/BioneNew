package com.bione.ui.onboarding.walkthrough;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.onboarding.Login;
import com.bione.utils.CustomViewPager;

import me.relex.circleindicator.CircleIndicator;

public class Walk extends BaseActivity {

    private CustomViewPager viewPager;
    private AppCompatTextView tvGetStarted;
    private AppCompatImageView ivFacebook;
    private AppCompatImageView ivInsta;
    private AppCompatImageView ivTwitter;
    private AppCompatImageView ivYoutube;
    private AppCompatImageView ivLinkedIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);


        init();
        setListeners();

        initViewPager();
    }

    private void init() {
        ivFacebook = findViewById(R.id.ivFacebook);
        ivInsta = findViewById(R.id.ivInsta);
        ivTwitter = findViewById(R.id.ivTwitter);
        ivYoutube = findViewById(R.id.ivYoutube);
        ivLinkedIn = findViewById(R.id.ivLinkedIn);
        tvGetStarted = findViewById(R.id.tvContinue);
    }

    private void setListeners() {
        ivFacebook.setOnClickListener(this);
        ivInsta.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
        ivYoutube.setOnClickListener(this);
        ivLinkedIn.setOnClickListener(this);
        tvGetStarted.setOnClickListener(this);
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPagingEnabled(true);


        // setting viewPager's pages
        final WalkPagerAdapter adapter = new WalkPagerAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
    }


    private void openLink(final String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvContinue:
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                break;

            case R.id.ivFacebook:
                openLink("https://www.facebook.com/bioneventures/");
                break;

            case R.id.ivTwitter:
                openLink("https://twitter.com/Bioneventures");
                break;

            case R.id.ivInsta:
                openLink("https://www.instagram.com/bione.ventures/");
                break;

            case R.id.ivLinkedIn:
                openLink("https://www.linkedin.com/company/bioneindia/");
                break;

            case R.id.ivYoutube:
                openLink("https://www.bione.in/");
                break;

        }
    }
}
