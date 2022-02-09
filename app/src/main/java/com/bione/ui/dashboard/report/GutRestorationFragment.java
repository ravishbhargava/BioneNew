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
import com.bione.ui.base.BaseFragment;
import com.bione.ui.dashboard.report.adapter.GutRestorationAdapter;

import java.util.ArrayList;

public class GutRestorationFragment extends BaseFragment {

    private Context mContext;
    private View rootView;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> arrayList;

    public GutRestorationFragment(ArrayList<String> arrayList) {
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
            rootView = inflater.inflate(R.layout.fragment_restoration, container, false);

            initRecycler();

        }
        return rootView;
    }

    @Override
    public void onClick(View view) {

    }


    private void initRecycler() {
        recyclerView = rootView.findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        GutRestorationAdapter adapter = new GutRestorationAdapter(arrayList);
        recyclerView.setAdapter(adapter);

    }


}
