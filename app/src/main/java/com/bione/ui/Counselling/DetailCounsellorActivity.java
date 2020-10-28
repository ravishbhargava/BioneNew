package com.bione.ui.Counselling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.model.counsellors.ListItem;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.CommonUtil;

public class DetailCounsellorActivity extends BaseActivity {

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 101;
    private AppCompatImageView ivBack;
    private ListItem counsellor;
    private RelativeLayout bottomRel;
    private RelativeLayout bottomRel2;
    private TextView tvName;
    private TextView tvStatus;
    private TextView tvType;
    private TextView tvDate;
    private TextView tvReason;
    private TextView tvTimeSlot;
    private TextView tvSummary;
    private TextView tvCounsellorName;
    private TextView tvCustomerFeedback;
    private AppCompatTextView tvSubmit;
    //    private View bottomView;
    private AppCompatRatingBar ratingBar;

    private String feedback = "";
    private String stars = "";

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

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailCounsellorActivity.this, RateScreenActivity.class);
                intent.putExtra("bookingID", counsellor.getMobilecounsellingId());
                intent.putExtra("feedback", feedback);
                intent.putExtra("stars", stars);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private void init() {
        tvSummary = findViewById(R.id.tvSummary);
        tvName = findViewById(R.id.tvName);
        tvReason = findViewById(R.id.tvReason);
        tvDate = findViewById(R.id.tvDate);
        tvType = findViewById(R.id.tvType);
        ivBack = findViewById(R.id.ivBack);
        tvStatus = findViewById(R.id.tvStatus);
        ratingBar = findViewById(R.id.ratingBar);
        bottomRel = findViewById(R.id.bottomRel);
        bottomRel2 = findViewById(R.id.bottomRel2);
        tvSubmit = findViewById(R.id.tvSubmit);
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

        bottomRel2.setVisibility(View.GONE);

        if (counsellor.getFeedback() != null) {
            feedback = counsellor.getFeedback();
            tvCustomerFeedback.setText(feedback);
        }

        if (counsellor.getStarsRatings() == null) {
            ratingBar.setVisibility(View.GONE);
            bottomRel.setVisibility(View.GONE);
        } else {
            String rating = counsellor.getStarsRatings();
            stars = rating;
            ratingBar.setVisibility(View.VISIBLE);
            bottomRel.setVisibility(View.VISIBLE);
            ratingBar.setRating(Float.parseFloat(stars));
        }

        if (counsellor.getStatus().equals("0")) {
            tvStatus.setText("Pending");
        } else if (counsellor.getStatus().equals("1")) {
            tvStatus.setText("Completed");
        } else {
            tvStatus.setText("Cancelled");
            tvReason.setText(counsellor.getReasonCancelation());
            tvReason.setVisibility(View.VISIBLE);
        }
//        ratingBar.setEnabled(false);
        ratingBar.setIsIndicator(true);
    }

    @Override
    public void onClick(View view) {

    }

    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                counsellor.setFeedback(data.getStringExtra("feedback"));
                counsellor.setStarsRatings(data.getStringExtra("stars"));

                setData();
                // get String data from Intent
//                String returnString = data.getStringExtra(Intent.EXTRA_TEXT);
//                // set text view with string
//                TextView textView = (TextView) findViewById(R.id.textView);
//                textView.setText(returnString);
            }
        }
    }
}
