package com.bione.ui.dashboard.paymentreceipt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;

import com.bione.model.paymentreceiptlist.Receipt;
import com.bione.model.paymentreceiptlist.ReceiptList;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseFragment;
import com.bione.ui.dashboard.paymentreceipt.adapter.ReceiptListAdapter;
import com.bione.utils.Log;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static com.bione.utils.AppConstant.PARAM_EMAIL;

public class PaymentReceiptFragment extends BaseFragment {

    private Context mContext;
    private View rootView;
    private RecyclerView recyclerView;
    private ArrayList<Receipt> receiptArrayList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_payment_receipt_list, container, false);


            receiptList();
            FloatingActionButton fab = rootView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext, PaymentReceiptActivity.class));
//                    Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();

                }
            });

        }
        return rootView;
    }


    public void receiptList() {
        final CommonParams commonParams = new CommonParams.Builder()
//                .add(PARAM_EMAIL, CommonData.getUserData().getEmail())
                .add(PARAM_EMAIL, "murugesan@bione.in")
                .build();

        RestClient.getApiInterface2().paymentReceiptList(commonParams.getMap()).enqueue(new ResponseResolver<ReceiptList>() {
            @Override
            public void onSuccess(ReceiptList commonResponse) {
                Log.d("onSuccess", "" + commonResponse);
                if (commonResponse.getCode() == 200) {

                    receiptArrayList = (ArrayList<Receipt>) commonResponse.getReceipt();
                    onSetRecyclerView();

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


    private void onSetRecyclerView() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(new ReceiptListAdapter(mContext, receiptArrayList));

        // Scroll to the position we want to snap to
//        linearLayoutManager.scrollToPosition(1);
        // Wait until the RecyclerView is laid out.
//        recyclerView.post(new Runnable() {
//            @Override
//            public void run() {
//                // Shift the view to snap  near the center of the screen.
//                // This does not have to be precise.
//                int dx = (recyclerViewCarousel.getWidth() - recyclerViewCarousel.getChildAt(0).getWidth()) / 2;
//                recyclerViewCarousel.scrollBy(-dx, 0);
//                // Assign the LinearSnapHelper that will initially snap the near-center view.
//                LinearSnapHelper snapHelper = new LinearSnapHelper();
//                snapHelper.attachToRecyclerView(recyclerViewCarousel);
//            }
//        });
    }

    @Override
    public void onClick(View view) {

    }
}
