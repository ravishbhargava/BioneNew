package com.bione.ui.myfoodadvice.category;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.CommonListData;
import com.bione.ui.base.BaseActivity;

import java.util.ArrayList;

public class MyFoodAdviceActivity extends BaseActivity {


    private AppCompatImageView ivBack;
    private MyFoodAdviceAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<CommonListData> commonListData;
    private MyFoodAdviceViewModel myFoodAdviceViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_food_advice);


        myFoodAdviceViewModel = ViewModelProviders.of(this).get(MyFoodAdviceViewModel.class);
        myFoodAdviceViewModel.init();
        myFoodAdviceViewModel.getCommonListData().observe(this, commonListData -> mAdapter.notifyDataSetChanged());

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
        mAdapter = new MyFoodAdviceAdapter(getApplicationContext(), myFoodAdviceViewModel.getCommonListData().getValue());
        recyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onClick(View view) {

    }
}
