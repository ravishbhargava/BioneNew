package com.bione.ui.Counselling;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;

import com.bione.R;
import com.bione.model.counsellors.ListItem;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.CommonUtil;

public class DetailCounsellorActivity extends BaseActivity {

    private AppCompatImageView ivBack;
    private ListItem counsellor;

    private TextView tvName;
    private TextView tvStatus;
    private TextView tvType;
    private TextView tvDate;
    private TextView tvDays;
    private TextView tvTimeSlot;
    private TextView tvSummary;
    private TextView tvCounsellorName;
    private TextView tvCustomerFeedback;
//    private View bottomView;
    private AppCompatRatingBar ratingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_schedule);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            counsellor = extras.getParcelable("counsellor");
            // and get whatever type user account id is
        }

        init();

        setData();


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void init() {
        tvSummary = findViewById(R.id.tvSummary);
        tvName = findViewById(R.id.tvName);
        tvDays = findViewById(R.id.tvDays);
        tvDate = findViewById(R.id.tvDate);
        tvType = findViewById(R.id.tvType);
        ivBack = findViewById(R.id.ivBack);
        tvStatus = findViewById(R.id.tvStatus);
        ratingBar = findViewById(R.id.ratingBar);
//        bottomView = findViewById(R.id.bottomView);
        tvTimeSlot = findViewById(R.id.tvTimeSlot);
        tvCustomerFeedback = findViewById(R.id.tvCustomerFeedback);
        tvCounsellorName = findViewById(R.id.tvCounsellorName);
    }

    private void setData() {
        tvName.setText(counsellor.getCustomerName());
        tvDate.setText(CommonUtil.getDayMonth(counsellor.getDate()));
        tvType.setText(counsellor.getGeneticType());
        tvSummary.setText(counsellor.getCounsellorSummary());
        tvCounsellorName.setText(counsellor.getCounsellorName());
        tvTimeSlot.setText(counsellor.getTimeSlot());

        if (counsellor.getFeedback() != null) {
            tvCustomerFeedback.setText(counsellor.getFeedback());
        }

        if (counsellor.getStarsRatings() == null) {
            ratingBar.setVisibility(View.GONE);
        } else {
            String rating = counsellor.getStarsRatings();
            ratingBar.setVisibility(View.VISIBLE);
            ratingBar.setRating(Float.parseFloat(rating));
        }

        if (counsellor.getStatus().equals("0")) {
            tvStatus.setText("Pending");
        } else {
            tvStatus.setText("Completed");
        }
    }

    @Override
    public void onClick(View view) {

    }
}
