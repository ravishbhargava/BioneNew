package com.bione.ui.myfoodadvice.subcategory;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.CommonListData;
import com.bione.model.CommonListDataParent;
import com.bione.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class SubFoodAdviceActivity extends BaseActivity {


    private AppCompatImageView ivBack;
//    private SubFoodAdviceAdapter mAdapter;
    private ExpandableRecyclerView mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<CommonListData> commonListData;
    private SubFoodAdviceViewModel subFoodAdviceViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_food_advice);


        subFoodAdviceViewModel = ViewModelProviders.of(this).get(SubFoodAdviceViewModel.class);
        subFoodAdviceViewModel.init();
        subFoodAdviceViewModel.getCommonListData().observe(this, commonListData -> mAdapter.notifyDataSetChanged());

        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(view -> finish());

        initRecycler();

    }

    private void initRecycler() {

        recyclerView = findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        mAdapter = new ExpandableRecyclerView(getApplicationContext(), subFoodAdviceViewModel.getCommonListData().getValue());
        List<CommonListData> arrayList = subFoodAdviceViewModel.getCommonListData().getValue();

        List<CommonListDataParent> parents = new ArrayList<>();
        parents.add(new CommonListDataParent("head11",arrayList,"12"));
        parents.add(new CommonListDataParent("head22",arrayList,"22"));
        parents.add(new CommonListDataParent("head33",arrayList,"13"));
        parents.add(new CommonListDataParent("head44",arrayList,"14"));

        mAdapter = new ExpandableRecyclerView(parents);
        recyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onClick(View view) {

    }
}
