package com.bione.ui.dashboard.bottomFragments.report;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.customerOrders.CustomerOrder;
import com.bione.model.customerOrders.KitOrder;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseFragment;
import com.bione.ui.dashboard.bottomFragments.report.adapter.ReportAdapter;
import com.bione.utils.Log;

import java.util.ArrayList;
import java.util.List;

import static com.bione.utils.AppConstant.PARAM_CUSTOMER;
import static com.bione.utils.AppConstant.SUCCESS;

//import com.bione.model.customerkit.KitOrder;


public class MyReportFragment extends BaseFragment {

    private View rootView;
//    private String text = "Hello";
////    private AppCompatTextView tvHeading;
//    private AppCompatTextView tvChat;
//    private AppCompatImageView ivHead;

    private Context mContext;

    private ReportAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<KitOrder> kitOrders = new ArrayList<>();

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
            rootView = inflater.inflate(R.layout.fragment_reports, container, false);
//            tvHeading = rootView.findViewById(R.id.tvHeading);
//            ivHead = rootView.findViewById(R.id.ivHead);
//            tvChat = rootView.findViewById(R.id.tvChat);
//
//            tvChat.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    ZohoSalesIQ.Chat.show();
//
//                }
//            });
//            initRecycler();
callAPI();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View view) {

    }

    private void initRecycler() {
        recyclerView = rootView.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ReportAdapter(mContext, kitOrders);
        recyclerView.setAdapter(mAdapter);

    }

    private void callAPI() {


        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_CUSTOMER, "" + CommonData.getUserData().getEntityId())
//                .add(PARAM_CUSTOMER, "585")
                .build();

        RestClient.getApiInterface().kitOrders(commonParams.getMap()).enqueue(new ResponseResolver<List<CustomerOrder>>() {
            @Override
            public void onSuccess(List<CustomerOrder> customerKits) {

                if (customerKits.get(0).getCode() == SUCCESS) {
                    try {

                        Log.d("customer kit ordered", " size :: " + customerKits.get(0).getKitOrders().size());
                        // specify an adapter (see also next example)
                        ArrayList<KitOrder> newKitorders = new ArrayList<>();
                        newKitorders = (ArrayList<KitOrder>) customerKits.get(0).getKitOrders();
                        for (int i = 0; i < newKitorders.size(); i++) {
//                            if (newKitorders.get(i).getSkuCode().equals("MM")) {
                                kitOrders.add(newKitorders.get(i));
//                            }
                        }
                        Log.d("newKitorders", "---" + newKitorders.size());
                        Log.d("kitOrders", "----" + kitOrders.size());

                        initRecycler();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (customerKits.get(0).getCode() == 404) {
                    showErrorMessage("No Reports");
                } else {
                    showErrorMessage(customerKits.get(0).getMessage());
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
}
