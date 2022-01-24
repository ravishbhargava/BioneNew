package com.bione.ui.dashboard.report;

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
import com.bione.model.customerOrders.KitOrder;
import com.bione.ui.base.BaseFragment;
import com.bione.ui.dashboard.orderfragment.adapter.KitListAdapter;
import com.bione.ui.dashboard.report.adapter.GutRestorationAdapter;

import java.util.ArrayList;

public class GutMaintenanceFragment extends BaseFragment {

    private Context mContext;
    private View rootView;
//    private AppCompatTextView tvHead;
//    private AppCompatImageView noItemImage;


    private KitListAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<KitOrder> kitOrders = new ArrayList<>();
    private ArrayList<String> arrayList;

    public GutMaintenanceFragment(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }


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
            rootView = inflater.inflate(R.layout.fragment_my_kit_list, container, false);

//            tvHead = rootView.findViewById(R.id.tvHead);
//            noItemImage = rootView.findViewById(R.id.noItemImage);
            initRecycler();

        }
        return rootView;
    }

    @Override
    public void onClick(View view) {

    }


    private void initRecycler() {
        recyclerView = rootView.findViewById(R.id.my_recycler_view);

//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        GutRestorationAdapter adapter = new GutRestorationAdapter(arrayList);
//        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
//        mAdapter = new KitListAdapter(mContext, kitOrders);
//        recyclerView.setAdapter(mAdapter);
    }


}
