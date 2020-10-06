package com.bione.ui.mymicrobiome.report;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.CommonListData;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.mymicrobiome.report.adapter.ReportTypeAdapter;

import java.util.ArrayList;

public class MyReportTypeActivity extends BaseActivity {

    private AppCompatImageView ivBack;
    private ReportTypeAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<CommonListData> reportType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_type);


        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        createList();
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
        mAdapter = new ReportTypeAdapter(getApplicationContext(), reportType);
        recyclerView.setAdapter(mAdapter);

    }

    private void createList() {
        reportType = new ArrayList<>();

        CommonListData data = new CommonListData();
        data.setChecked(false);
        data.setHeading("Report In PDF");
        data.setText("Report In PDF");
        data.setDrawable(R.drawable.ic_report_pdf);

        CommonListData data1 = new CommonListData();
        data1.setChecked(false);
        data1.setHeading("Report In Detail");
        data1.setText("Report In Detail");
        data1.setDrawable(R.drawable.ic_report_detail);

        reportType.add(data);
        reportType.add(data1);
    }


    @Override
    public void onClick(View view) {

    }
}
