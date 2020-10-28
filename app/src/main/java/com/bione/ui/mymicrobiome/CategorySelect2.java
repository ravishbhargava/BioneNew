package com.bione.ui.mymicrobiome;

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
import com.bione.model.CounsellorsData;
import com.bione.model.CrouselData;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.schedulecall.ScheduleNow;
import com.bione.ui.mymicrobiome.adapter.CounsellorsMicroBiomeAdapter;
import com.bione.utils.Log;

import java.util.ArrayList;

public class CategorySelect2 extends BaseActivity {

    private AppCompatImageView ivBack;


    private AppCompatTextView tvSelectedSlot;
    private AppCompatTextView tvScheduleNow;

    private RecyclerView recyclerView;
    private CounsellorsMicroBiomeAdapter mAdapter;
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

    private ImageView ivGeneticInner;
    private ImageView ivMicrobiomeInner;
    private ImageView ivLongevityInner;

    private AppCompatTextView tvGenetics;
    private AppCompatTextView tvFitness;
    private AppCompatTextView tvFood;

    private String fromFlow = "";

    private ArrayList<CrouselData> crouselDataArrayList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_microbiome);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            geneticType = extras.getString("geneticType");
            fromFlow = extras.getString("fromFlow");
        }


        initViews();
        setListeners();
        flowDecide();
        initRecycler();
        geneticTypeSelected();
    }

    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        tvSelectedSlot = findViewById(R.id.tvSelectedSlot);
        tvScheduleNow = findViewById(R.id.tvScheduleNow);

        tvGenetics = findViewById(R.id.tvGenetics);
        tvFitness = findViewById(R.id.tvFitness);
        tvFood = findViewById(R.id.tvFood);

        llGenetics = findViewById(R.id.llGenetics);
        llMicroBiome = findViewById(R.id.llMicroBiome);
        llLongevity = findViewById(R.id.llLongevity);

        ivGenetic = findViewById(R.id.ivGenetic);
        ivMicrobiome = findViewById(R.id.ivMicrobiome);
        ivLongevity = findViewById(R.id.ivLongevity);

        ivGeneticInner = findViewById(R.id.ivGeneticInner);
        ivMicrobiomeInner = findViewById(R.id.ivMicrobiomeInner);
        ivLongevityInner = findViewById(R.id.ivLongevityInner);
    }

    private void setListeners() {
        tvScheduleNow.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        llGenetics.setOnClickListener(this);
        llMicroBiome.setOnClickListener(this);
        llLongevity.setOnClickListener(this);
    }

    private void flowDecide() {
        // from mymicrobiome this will show
//        if (fromFlow != null && fromFlow.equals("MyMicroBiome")) {

        tvGenetics.setText("MyReport");
        tvFitness.setText("MySmart Diet");
        tvFood.setText("MyFood Advice");

        ivGeneticInner.setImageDrawable(getResources().getDrawable(R.drawable.ic_result));
        ivMicrobiomeInner.setImageDrawable(getResources().getDrawable(R.drawable.ic_nutrition));
        ivLongevityInner.setImageDrawable(getResources().getDrawable(R.drawable.ic_teacher));

        geneticType = "MyReport";
//            createList("Tanya", "MyReport");

//        } else {// normal scenario this will show
//            tvGenetics.setText("My Genetic\nTesting");
//            tvFitness.setText("MyMicrobiome\nTesting");
//            tvFood.setText("Longevity Plus\nTesting");
//
//            ivGeneticInner.setImageDrawable(getResources().getDrawable(R.drawable.ic_group));
//            ivMicrobiomeInner.setImageDrawable(getResources().getDrawable(R.mipmap.ic_bacteria));
//            ivLongevityInner.setImageDrawable(getResources().getDrawable(R.drawable.ic_nutri));
//        }
    }

    private void geneticTypeSelected() {
        if (geneticType.equals("Genetic") || geneticType.equals("MyReport")) {
            selectType(ivGenetic, ivMicrobiome, ivLongevity);
        } else if (geneticType.equals("MyMicroBiome") || geneticType.equals("MySmart Diet")) {
            selectType(ivMicrobiome, ivGenetic, ivLongevity);
        } else if (geneticType.equals("Longevity") || geneticType.equals("MyFood Advice")) {
            selectType(ivLongevity, ivMicrobiome, ivGenetic);
        }
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
                Intent intent = new Intent(CategorySelect2.this, ScheduleNow.class);
                intent.putExtra("geneticType", geneticType);
                intent.putExtra("counsellorName", counsellorName);
                intent.putExtra("category","category2");
                startActivityForResult(intent,101);
                break;

            case R.id.ivBack:
                finish();
                break;
            case R.id.llGenetics:
//                if (fromFlow != null && fromFlow.equals("MyMicroBiome")) {
                geneticType = "MyReport";
                createList("Diet & Nutrition", "MyReport");
//                } else {
//                    createList("Adrija Mishra", "Genetic");
//                }
                geneticTypeSelected();
                break;
            case R.id.llMicroBiome:
//                if (fromFlow != null && fromFlow.equals("MyMicroBiome")) {
                geneticType = "MySmart Diet";
                createList("Diet & Nutrition", "MySmart Diet");
//                } else {
//                    createList("Tanya", "MyMicroBiome");
//                }
                geneticTypeSelected();
                break;
            case R.id.llLongevity:

                geneticType = "MyFood Advice";
                createList("Diet & Nutrition", "MyFood Advice");
                geneticTypeSelected();
//                if (fromFlow != null && fromFlow.equals("MyMicroBiome")) {
//                    Intent intent1 = new Intent(CategorySelect2.this, SessionActivity.class);
//                    startActivity(intent1);
//                } else {
//                    createList("Adrija Mishra", "Longevity");
//                    geneticTypeSelected();
//                }
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


//        // specify an adapter (see also next example)
        mAdapter = new CounsellorsMicroBiomeAdapter(counsellorsDataArrayList, new CategorySelect2.OnItemClickListener() {
            @Override
            public void onItemClick(String text) {

            }
        });
        recyclerView.setAdapter(mAdapter);
//        if (fromFlow != null && fromFlow.equals("MyMicroBiome")) {
        createList("Diet & Nutrition", "MyReport");
//        } else {
//            createList("Adrija Mishra", "Genetic");
//        }
    }

    private void createList(String name, String type) {
        counsellorsDataArrayList = new ArrayList<>();

        CounsellorsData counsellorsData = new CounsellorsData();
        counsellorsData.setName(name);
        counsellorsData.setType(type);

        counsellorsDataArrayList.add(counsellorsData);
        mAdapter.refreshEvents(counsellorsDataArrayList);

        geneticType = counsellorsDataArrayList.get(0).getType();
//        recyclerView.setAdapter(mAdapter);
    }

    public interface OnItemClickListener {
        void onItemClick(final String text);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            Log.d(""+ getClass().getSimpleName(), "se aaya");
            if (data != null) {
                if (data.getStringExtra("status").equals("Done")) {
                    Intent intent = new Intent();
                    intent.putExtra("status", "Done");
                    setResult(101, intent);
                    finish();
                }
            }
        }
    }

}
