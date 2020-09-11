package com.bione.ui.Counselling;

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

    private String entityID = "1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        ivBack = findViewById(R.id.ivBack);
        etFeedback = findViewById(R.id.etFeedback);
        ratingBar = findViewById(R.id.ratingBar);
        tvSubmit = findViewById(R.id.tvSubmit);


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
                .add(PARAM_ENTITY_ID, entityID)
                .add(PARAM_FEEDBACK, etFeedback.getText().toString())
                .add(PARAM_RATINGS, ratingBar.getRating())

                .build();

        RestClient.getApiInterface().updateFeedback(commonParams.getMap()).enqueue(new ResponseResolver<List<CommonResponse>>() {
            @Override
            public void onSuccess(List<CommonResponse> commonResponses) {

                if (commonResponses.get(0).getStatusCode().equals("200")) {
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
