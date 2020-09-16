package com.bione.ui.Counselling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.model.CommonResponse;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.Log;

import java.util.List;

import static com.bione.utils.AppConstant.PARAM_ENTITY_ID;
import static com.bione.utils.AppConstant.PARAM_FEEDBACK;
import static com.bione.utils.AppConstant.PARAM_RATINGS;

public class RateScreenActivity extends BaseActivity {

    private AppCompatImageView ivBack;
    private AppCompatEditText etFeedback;
    private AppCompatTextView tvSubmit;
    private AppCompatRatingBar ratingBar;

    private String bookingID = "";
    private String feedback = "";
    private String stars = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            bookingID = extras.getString("bookingID");
            feedback = extras.getString("feedback");
            stars = extras.getString("stars");
            // and get whatever type user account id is
        }

        ivBack = findViewById(R.id.ivBack);
        etFeedback = findViewById(R.id.etFeedback);
        ratingBar = findViewById(R.id.ratingBar);
        tvSubmit = findViewById(R.id.tvSubmit);

        if (!stars.equals(""))
            ratingBar.setRating(Float.parseFloat(stars));

        if (!feedback.equals(""))
            etFeedback.setText(feedback);

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void call() {

        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_ENTITY_ID, bookingID)
                .add(PARAM_FEEDBACK, etFeedback.getText().toString())
                .add(PARAM_RATINGS, ratingBar.getRating())

                .build();

        RestClient.getApiInterface().updateFeedback(commonParams.getMap()).enqueue(new ResponseResolver<List<CommonResponse>>() {
            @Override
            public void onSuccess(List<CommonResponse> commonResponses) {

                if (commonResponses.get(0).getStatusCode().equals("200")) {
                    Intent intent = new Intent();
                    intent.putExtra("feedback", etFeedback.getText().toString());
                    intent.putExtra("stars", String.valueOf(ratingBar.getRating()));
                    setResult(RESULT_OK, intent);
                    finish();

                } else {
                    showErrorMessage(commonResponses.get(0).getMessage());
                }
            }

            @Override
            public void onError(ApiError error) {
                Log.d("onError", "" + error);
                showErrorMessage(error.getMessage());
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                showErrorMessage(throwable.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
