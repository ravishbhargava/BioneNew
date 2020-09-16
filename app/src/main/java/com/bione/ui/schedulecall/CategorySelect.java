package com.bione.ui.schedulecall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.CounsellorsData;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.schedulecall.adapter.CounsellorsAdapter;
import com.bione.utils.Log;

import java.util.ArrayList;

public class CategorySelect extends BaseActivity {

    private AppCompatImageView ivBack;


    private AppCompatTextView tvSelectedSlot;
    private AppCompatTextView tvScheduleNow;

    private RecyclerView recyclerView;
    private CounsellorsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CounsellorsData> counsellorsDataArrayList = new ArrayList<>();

    private String geneticType = "Genetic";
    private String counsellorName = "counsellorName";

    private LinearLayoutCompat llGenetics;
    private LinearLayoutCompat llMicroBiome;
    private LinearLayoutCompat llLongevity;
    private ImageView ivGenetic;
    private ImageView ivMicrobiome;
    private ImageView ivLongevity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);

        Log.d("customrer id", "------" + CommonData.getUserData().getEntityId());

        ivBack = findViewById(R.id.ivBack);
        tvSelectedSlot = findViewById(R.id.tvSelectedSlot);
        tvScheduleNow = findViewById(R.id.tvScheduleNow);

        llGenetics = findViewById(R.id.llGenetics);
        llMicroBiome = findViewById(R.id.llMicroBiome);
        llLongevity = findViewById(R.id.llLongevity);

        ivGenetic = findViewById(R.id.ivGenetic);
        ivMicrobiome = findViewById(R.id.ivMicrobiome);
        ivLongevity = findViewById(R.id.ivLongevity);

        tvScheduleNow.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        llGenetics.setOnClickListener(this);
        llMicroBiome.setOnClickListener(this);
        llLongevity.setOnClickListener(this);

        initRecycler();

    }


    private void selectType(final ImageView selected, final ImageView unselected1, final ImageView unselected2) {

        selected.setImageResource(R.drawable.ic_selected_circle);
        unselected1.setImageResource(R.drawable.ic_unselected_circle);
        unselected2.setImageResource(R.drawable.ic_unselected_circle);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tvScheduleNow:
                geneticType = counsellorsDataArrayList.get(0).getType();
                counsellorName = counsellorsDataArrayList.get(0).getName();

                Log.d("geneticType", "---" + geneticType);
                Log.d("counsellorName", "---" + counsellorName);
                Intent intent = new Intent(CategorySelect.this, ScheduleNow.class);
                intent.putExtra("geneticType", geneticType);
                intent.putExtra("counsellorName", counsellorName);
                startActivity(intent);
                break;

            case R.id.ivBack:
                finish();
                break;
            case R.id.llGenetics:
                createList("Adrija Mishra", "Genetic");
                selectType(ivGenetic, ivMicrobiome, ivLongevity);
                break;
            case R.id.llMicroBiome:
                createList("Tanya", "MyMicroBiome");
                selectType(ivMicrobiome, ivGenetic, ivLongevity);
                break;
            case R.id.llLongevity:
                createList("Adrija Mishra", "Longevity");
                selectType(ivLongevity, ivMicrobiome, ivGenetic);
                break;

            default:
                break;
        }
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // specify an adapter (see also next example)
        mAdapter = new CounsellorsAdapter(counsellorsDataArrayList, new CategorySelect.OnItemClickListener() {
            @Override
            public void onItemClick(String text) {

            }
        });
        recyclerView.setAdapter(mAdapter);
        createList("Adrija Mishra", "Genetic");
    }

    private void createList(String name, String type) {
        counsellorsDataArrayList = new ArrayList<>();

        CounsellorsData counsellorsData = new CounsellorsData();
        counsellorsData.setName(name);
        counsellorsData.setType(type);

        counsellorsDataArrayList.add(counsellorsData);
        mAdapter.refreshEvents(counsellorsDataArrayList);
//        recyclerView.setAdapter(mAdapter);
    }

    public interface OnItemClickListener {
        void onItemClick(final String text);
    }

}
