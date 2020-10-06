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
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.CounsellorsData;
import com.bione.model.CrouselData;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.mymicrobiome.SessionActivity;
import com.bione.ui.schedulecall.adapter.CounsellorAdapter2;
import com.bione.ui.schedulecall.adapter.CounsellorsAdapter;
import com.bione.utils.CenterZoomLayoutManager;
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
        setContentView(R.layout.activity_category_select);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            geneticType = extras.getString("geneticType");
            fromFlow = extras.getString("fromFlow");
        }


        initViews();
        setListeners();
        flowDecide();

        setArrayList();
        onSetRecyclerView();

        geneticTypeSelected();
        initRecycler();
    }


    private void onSetRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        CenterZoomLayoutManager layoutManager =
                new CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new CounsellorAdapter2(this, crouselDataArrayList));
        // Scroll to the position we want to snap to
        layoutManager.scrollToPosition(0);
        // Wait until the RecyclerView is laid out.
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                // Shift the view to snap  near the center of the screen.
                // This does not have to be precise.
                int dx = (recyclerView.getWidth() - recyclerView.getChildAt(0).getWidth()) / 2;
                recyclerView.scrollBy(-dx, 0);
                // Assign the LinearSnapHelper that will initially snap the near-center view.
                LinearSnapHelper snapHelper = new LinearSnapHelper();
                snapHelper.attachToRecyclerView(recyclerView);
            }
        });
    }

    private void setArrayList() {
        crouselDataArrayList = new ArrayList<>();

        CrouselData data = new CrouselData();
        data.setDrawable(0);
        data.setHeading("");
        data.setText("");

        CrouselData data1 = new CrouselData();
        data1.setDrawable(R.mipmap.image_longevity);
        data1.setHeading("Longevity Plus Test");
        data1.setText("World's most comprehensive high-throughput DNA test - The best investment to know how your genes " +
                "affect various health aspects for " +
                "timely management");

        CrouselData data2 = new CrouselData();
        data2.setDrawable(R.mipmap.image_microbiome);
        data2.setHeading("MyMicrobiome Test");
        data2.setText("Discover & understand your gastrointestinal microbiota and best " +
                "suited personalised diet for a " +
                "healthy & happy life.");

        CrouselData data3 = new CrouselData();
        data3.setDrawable(R.mipmap.image_genetic);
        data3.setHeading("Bione Gene-Check\n");
        data3.setText("Discover & understand how your " +
                "genes can be responsible for the susceptibility to viral infections like " +
                "SARS and Influenza.");

        CrouselData data4 = new CrouselData();
        data4.setDrawable(R.mipmap.image_clinical);
        data4.setHeading("Clinical \nGenetics Tests ");
        data4.setText("The genesis of elite\n" +
                "genetic testing");

        CrouselData data5 = new CrouselData();
        data5.setDrawable(0);
        data5.setHeading("");
        data5.setText("");

        crouselDataArrayList.add(data);
        crouselDataArrayList.add(data2);
        crouselDataArrayList.add(data1);
        crouselDataArrayList.add(data3);
        crouselDataArrayList.add(data4);
        crouselDataArrayList.add(data5);
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
        if (fromFlow != null && fromFlow.equals("MyMicroBiome")) {

            tvGenetics.setText("MyReport");
            tvFitness.setText("Diet & Nutrition");
            tvFood.setText("MySessions");

            ivGeneticInner.setImageDrawable(getResources().getDrawable(R.drawable.ic_result));
            ivMicrobiomeInner.setImageDrawable(getResources().getDrawable(R.drawable.ic_nutrition));
            ivLongevityInner.setImageDrawable(getResources().getDrawable(R.drawable.ic_teacher));

            geneticType = "MyReport";
//            createList("Tanya", "MyReport");

        } else {// normal scenario this will show
            tvGenetics.setText("My Genetic\nTesting");
            tvFitness.setText("MyMicrobiome\nTesting");
            tvFood.setText("Longevity Plus\nTesting");

            ivGeneticInner.setImageDrawable(getResources().getDrawable(R.drawable.ic_group));
            ivMicrobiomeInner.setImageDrawable(getResources().getDrawable(R.mipmap.ic_bacteria));
            ivLongevityInner.setImageDrawable(getResources().getDrawable(R.drawable.ic_nutri));
        }
    }

    private void geneticTypeSelected() {
        if (geneticType.equals("Genetic") || geneticType.equals("MyReport")) {
            selectType(ivGenetic, ivMicrobiome, ivLongevity);
        } else if (geneticType.equals("MyMicroBiome") || geneticType.equals("Diet & Nutrition")) {
            selectType(ivMicrobiome, ivGenetic, ivLongevity);
        } else if (geneticType.equals("Longevity")) {
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
                Intent intent = new Intent(CategorySelect.this, ScheduleNow.class);
                intent.putExtra("geneticType", geneticType);
                intent.putExtra("counsellorName", counsellorName);
                startActivity(intent);
                break;

            case R.id.ivBack:
                finish();
                break;
            case R.id.llGenetics:
                if (fromFlow != null && fromFlow.equals("MyMicroBiome")) {
                    geneticType = "MyReport";
                    createList("Tanya", "MyReport");
                } else {
                    createList("Adrija Mishra", "Genetic");
                }
                geneticTypeSelected();
                break;
            case R.id.llMicroBiome:
                if (fromFlow != null && fromFlow.equals("MyMicroBiome")) {
                    geneticType = "Diet & Nutrition";
                    createList("Tanya", "Diet & Nutrition");
                } else {
                    createList("Tanya", "MyMicroBiome");
                }
                geneticTypeSelected();
                break;
            case R.id.llLongevity:
                if (fromFlow != null && fromFlow.equals("MyMicroBiome")) {
                    Intent intent1 = new Intent(CategorySelect.this, SessionActivity.class);
                    startActivity(intent1);
                } else {
                    createList("Adrija Mishra", "Longevity");
                    geneticTypeSelected();
                }
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
        if (fromFlow != null && fromFlow.equals("MyMicroBiome")) {
            createList("Tanya", "MyReport");
        } else {
            createList("Adrija Mishra", "Genetic");
        }
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

}
