package com.bione.ui.dashboard.bottomFragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.CrouselData;
import com.bione.ui.base.BaseActivity;

import com.bione.ui.dashboard.bottomFragments.adapters.KitViewAllAdapter;

import java.util.ArrayList;

public class OurProductsActivity extends BaseActivity {

    private ArrayList<CrouselData> crouselDataArrayList;
    private RecyclerView recyclerViewCarousel;
    private AppCompatImageView ivBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewall);


        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setArrayList();
        onSetRecyclerView();
    }

    @Override
    public void onClick(View view) {

    }

    private void onSetRecyclerView() {
        recyclerViewCarousel = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCarousel.setLayoutManager(linearLayoutManager);

        recyclerViewCarousel.setAdapter(new KitViewAllAdapter(this, crouselDataArrayList, 0));
        // Scroll to the position we want to snap to
//        linearLayoutManager.scrollToPosition(1);
        // Wait until the RecyclerView is laid out.
        recyclerViewCarousel.post(new Runnable() {
            @Override
            public void run() {
                // Shift the view to snap  near the center of the screen.
                // This does not have to be precise.
                int dx = (recyclerViewCarousel.getWidth() - recyclerViewCarousel.getChildAt(0).getWidth()) / 2;
                recyclerViewCarousel.scrollBy(-dx, 0);
                // Assign the LinearSnapHelper that will initially snap the near-center view.
                LinearSnapHelper snapHelper = new LinearSnapHelper();
                snapHelper.attachToRecyclerView(recyclerViewCarousel);
            }
        });
    }

    private void setArrayList() {
        crouselDataArrayList = new ArrayList<>();

        CrouselData data = new CrouselData();
        data.setDrawable(0);
        data.setDrawableTest(0);
        data.setHeadingTest("");
        data.setHeading("");
        data.setText("");
        data.setChecked(false);
        data.setNameCounsellor("");
        data.setTypeCounsellor("");
        data.setUrl("");

        CrouselData data1 = new CrouselData();
        data1.setDrawable(R.drawable.image_longevity);
//        data1.setDrawableTest(R.drawable.bitmap);
        data1.setHeadingTest("Longevity Plus\nTest");
        data1.setHeading("Longevity Plus Test");
        data1.setText("World's most comprehensive high-throughput DNA test - The best investment to know how your genes " + "affect various health aspects for " + "timely management");
        data1.setChecked(false);
        data1.setNameCounsellor("Genetic Counsellor");
        data1.setTypeCounsellor("Longevity Plus Test");
        data1.setUrl("https://www.bione.in/longevity-plus-test");

        CrouselData data2 = new CrouselData();
        data2.setDrawable(R.drawable.image_microbiome);
//        data2.setDrawableTest(R.drawable.bitmap);
        data2.setHeadingTest("MyMicrobiome\nTest");
        data2.setHeading("MyMicrobiome Test");
        data2.setText("Discover & understand your gastrointestinal microbiota and best " + "suited personalised diet for a " + "healthy & happy life.");
        data2.setChecked(false);
        data2.setNameCounsellor("Diet & Nutrition Counsellor");
        data2.setTypeCounsellor("MyMicrobiome Test");
        data2.setUrl("https://www.bione.in/mymicrobiome-test");

        CrouselData data3 = new CrouselData();
        data3.setDrawable(R.drawable.image_longifit);
//        data3.setDrawableTest(R.drawable.bitmap);
        data3.setHeadingTest("LongiFit\nTest");
        data3.setHeading("LongiFit");
        data3.setText("Get deep insight into DNA. Understand how your body " + "responds to sports, dietary needs, food reactions, skin health & " + "overall fitness.");
        data3.setChecked(false);
        data3.setNameCounsellor("Genetic Counsellor");
        data3.setTypeCounsellor("LongiFit Test");
        data3.setUrl("https://www.bione.in/longifit-test");

        CrouselData data4 = new CrouselData();
        data4.setDrawable(R.drawable.imge_gene_chek);
//        data4.setDrawableTest(R.drawable.bitmap);
        data4.setHeadingTest("Gene-Check\nTest");
        data4.setHeading("Bione Gene-Check");
        data4.setText("Discover & understand how your " + "genes can be responsible for the susceptibility to viral infections like " + "SARS and Influenza.");
        data4.setChecked(false);
        data4.setNameCounsellor("Genetic Counsellor");
        data4.setTypeCounsellor("Gene-Check Test");
        data4.setUrl("https://www.bione.in/gene-chec-test");

        CrouselData data5 = new CrouselData();
        data5.setDrawable(R.drawable.image_clinical);
//        data5.setDrawableTest(R.drawable.bitmap);
        data5.setHeadingTest("Genetic\nTest");
        data5.setHeading("Clinical \nGenetics Tests ");
        data5.setText("The genesis of elite\n" + "genetic testing");
        data5.setChecked(false);
        data5.setNameCounsellor("Genetic Counsellor");
        data5.setTypeCounsellor("Genetic Test");
        data5.setUrl("https://www.bione.in/genetics");

        CrouselData data6 = new CrouselData();
        data6.setDrawable(0);
        data6.setDrawableTest(0);
        data6.setHeadingTest("");
        data6.setHeading("");
        data6.setText("");
        data6.setChecked(false);
        data6.setNameCounsellor("");
        data6.setTypeCounsellor("");
        data6.setUrl("");

//        crouselDataArrayList.add(data);   //empty
        crouselDataArrayList.add(data2);   //My Micro Biome
        crouselDataArrayList.add(data3);   //LongiFit
        crouselDataArrayList.add(data4);   //Gene-Check
        crouselDataArrayList.add(data1);   //Longevity Plus
        crouselDataArrayList.add(data5);   //Clinical Genetic test
//        crouselDataArrayList.add(data6);   //empty
    }
}
