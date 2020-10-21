package com.bione.ui.mymicrobiome.report;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.customerkit.CustomerKit;
import com.bione.model.customerkit.KitOrder;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.mymicrobiome.report.adapter.ReportAdapter;
import com.bione.utils.Log;

import java.util.ArrayList;
import java.util.List;

import static com.bione.utils.AppConstant.PARAM_CUSTOMER;
import static com.bione.utils.AppConstant.SUCCESS;

public class MyReportActivity extends BaseActivity {

    private AppCompatImageView ivBack;
    private ReportAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<KitOrder> kitOrders = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);


        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        callAPI();

    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ReportAdapter(getApplicationContext(), kitOrders);
        recyclerView.setAdapter(mAdapter);

    }


    private void callAPI() {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_CUSTOMER, "" + CommonData.getUserData().getEntityId())
//                .add(PARAM_CUSTOMER, "36")
                .build();

        RestClient.getApiInterface().kitOrders(commonParams.getMap()).enqueue(new ResponseResolver<List<CustomerKit>>() {
            @Override
            public void onSuccess(List<CustomerKit> customerKits) {

                if (customerKits.get(0).getCode() == SUCCESS) {
                    try {

                        Log.d("customer kit ordered", " size :: " + customerKits.get(0).getKitOrders().size());
                        // specify an adapter (see also next example)
                        ArrayList<KitOrder> newKitorders = new ArrayList<>();
                        newKitorders = (ArrayList<KitOrder>) customerKits.get(0).getKitOrders();
                        for (int i = 0; i < newKitorders.size(); i++) {
                            if (newKitorders.get(i).getSkuCode().equals("MM")) {
                                kitOrders.add(newKitorders.get(i));
                            }
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

    @Override
    public void onClick(View view) {

    }
}
