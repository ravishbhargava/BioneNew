package com.bione.ui.dashboard.bottomFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.BannerArray;
import com.bione.model.CrouselData;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseFragment;
import com.bione.ui.dashboard.bottomFragments.adapters.KitDetailsAdapter;
import com.bione.ui.dashboard.bottomFragments.adapters.ViewPagerAdapter;
import com.bione.utils.CustomTypefaceSpan;
import com.bione.utils.CustomViewPager;
import com.bione.utils.Log;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment {

    private View rootView;
    private String text = "Hello";
    private AppCompatTextView tvHeading;
    private AppCompatTextView tvHead;
    private AppCompatTextView tvViewAll;
    private AppCompatTextView tvBoldText;
    private AppCompatImageView ivHead;
    private AppCompatImageView ivCard;

    private ArrayList<BannerArray> bannerArray;
    private CustomViewPager viewPager;

    private ArrayList<CrouselData> crouselDataArrayList;
    private RecyclerView recyclerViewCarousel;
    private Context mContext;

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
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            tvHeading = rootView.findViewById(R.id.tvHeading);
            ivHead = rootView.findViewById(R.id.ivHead);
            tvHead = rootView.findViewById(R.id.tvHead);
            ivCard = rootView.findViewById(R.id.ivCard);
            tvViewAll = rootView.findViewById(R.id.tvViewAll);
            tvBoldText = rootView.findViewById(R.id.tvBoldText);

            viewPager = rootView.findViewById(R.id.viewpager);

            viewPager.setVisibility(View.GONE);
            ivCard.setVisibility(View.VISIBLE);

            tvViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext, OurProductsActivity.class));
                }
            });

            setHeadText();
            setArrayList();
            onSetRecyclerView();


        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View view) {

    }

    private void setHeadText() {
        String name = "";
        if (CommonData.getUserData() != null) {
            name = CommonData.getUserData().getFirstname();
        }
        String first = "Hi " + name + " \n";
        String second = "We understand how \npriceless your health is!";
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-Regular.ttf");
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-Bold.ttf");
        tvHead.setText(first + second);
        SpannableStringBuilder SS = new SpannableStringBuilder(tvHead.getText().toString());
        SS.setSpan(new CustomTypefaceSpan("", font), 0, tvHead.getText().toString().length() - 27, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        SS.setSpan(new CustomTypefaceSpan("", font2), tvHead.getText().toString().length() - 27, tvHead.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvHead.setText(SS);
    }

    private void onSetRecyclerView() {
        recyclerViewCarousel = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCarousel.setLayoutManager(linearLayoutManager);

        recyclerViewCarousel.setAdapter(new KitDetailsAdapter(mContext, crouselDataArrayList, 0));
        // Scroll to the position we want to snap to
//        linearLayoutManager.scrollToPosition(1);
        // Wait until the RecyclerView is laid out.
//        recyclerViewCarousel.post(new Runnable() {
//            @Override
//            public void run() {
//                // Shift the view to snap  near the center of the screen.
//                // This does not have to be precise.
////                int dx = (recyclerViewCarousel.getWidth() - recyclerViewCarousel.getChildAt(0).getWidth()) / 2;
////                recyclerViewCarousel.scrollBy(-dx, 0);
//                // Assign the LinearSnapHelper that will initially snap the near-center view.
//                LinearSnapHelper snapHelper = new LinearSnapHelper();
//                snapHelper.attachToRecyclerView(recyclerViewCarousel);
//            }
//        });
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
        data1.setDrawable(R.drawable.lp);
//        data1.setDrawableTest(R.drawable.bitmap);
        data1.setHeadingTest("Longevity Plus\nTest");
        data1.setHeading("Longevity Plus Test");
        data1.setText("World's most comprehensive high-throughput DNA test - The best investment to know how your genes " + "affect various health aspects for " + "timely management");
        data1.setChecked(false);
        data1.setNameCounsellor("Genetic Counsellor");
        data1.setTypeCounsellor("Longevity Plus Test");
        data1.setUrl("https://www.bionegenetics.com/longevity-plus-test");
//        data1.setUrl("https://www.bione.in/longevity-plus-test");

        CrouselData data2 = new CrouselData();
        data2.setDrawable(R.drawable.mmb);
//        data2.setDrawableTest(R.drawable.bitmap);
        data2.setHeadingTest("MyMicrobiome\nTest");
        data2.setHeading("MyMicrobiome Test");
        data2.setText("Discover & understand your gastrointestinal microbiota and best " + "suited personalised diet for a " + "healthy & happy life.");
        data2.setChecked(false);
        data2.setNameCounsellor("Diet & Nutrition Counsellor");
        data2.setTypeCounsellor("MyMicrobiome Test");
//        data2.setUrl("https://www.bione.in/mymicrobiome-test");
        data2.setUrl("https://www.bionegenetics.com/mymicrobiome-test");

        CrouselData data3 = new CrouselData();
        data3.setDrawable(R.drawable.lf);
//        data3.setDrawableTest(R.drawable.bitmap);
        data3.setHeadingTest("LongiFit\nTest");
        data3.setHeading("LongiFit");
        data3.setText("Get deep insight into DNA. Understand how your body " + "responds to sports, dietary needs, food reactions, skin health & " + "overall fitness.");
        data3.setChecked(false);
        data3.setNameCounsellor("Genetic Counsellor");
        data3.setTypeCounsellor("LongiFit Test");
//        data3.setUrl("https://www.bione.in/longifit-test");
        data3.setUrl("https://www.bionegenetics.com/longifit-test");

        CrouselData data4 = new CrouselData();
        data4.setDrawable(R.drawable.imge_gene_chek);
//        data4.setDrawableTest(R.drawable.bitmap);
        data4.setHeadingTest("Gene-Check\nTest");
        data4.setHeading("Bione Gene-Check");
        data4.setText("Discover & understand how your " + "genes can be responsible for the susceptibility to viral infections like " + "SARS and Influenza.");
        data4.setChecked(false);
        data4.setNameCounsellor("Genetic Counsellor");
        data4.setTypeCounsellor("Gene-Check Test");
        data4.setUrl("https://www.bionegenetics.com/gene-chec-test");
//        data4.setUrl("https://www.bione.in/gene-chec-test");

        CrouselData data5 = new CrouselData();
        data5.setDrawable(R.drawable.image_clinical);
//        data5.setDrawableTest(R.drawable.bitmap);
        data5.setHeadingTest("Genetic\nTest");
        data5.setHeading("Clinical \nGenetics Tests ");
        data5.setText("The genesis of elite\n" + "genetic testing");
        data5.setChecked(false);
        data5.setNameCounsellor("Genetic Counsellor");
        data5.setTypeCounsellor("Genetic Test");
//        data5.setUrl("https://www.bione.in/genetics");
        data5.setUrl("https://www.bionegenetics.com/genetics");

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
//        crouselDataArrayList.add(data1);   //Longevity Plus
//        crouselDataArrayList.add(data4);   //Gene-Check
//        crouselDataArrayList.add(data5);   //Clinical Genetic test
//        crouselDataArrayList.add(data6);   //empty
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CommonData.getGuest()) {
            viewPager.setVisibility(View.GONE);
            ivCard.setVisibility(View.VISIBLE);
        } else {
            bannerArray = new ArrayList<>();
            BannerAPI();
            BannerAPILF();
        }
    }

    private void initViewPager() {
        viewPager = rootView.findViewById(R.id.viewpager);
        viewPager.setVisibility(View.VISIBLE);
        ivCard.setVisibility(View.GONE);

        viewPager.setOffscreenPageLimit(2);
        viewPager.setPagingEnabled(true);


        // setting viewPager's pages
        final ViewPagerAdapter adapter = new ViewPagerAdapter(mContext, bannerArray);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                setPosition();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void BannerAPI() {

        final CommonParams commonParams = new CommonParams.Builder()
                .add("id", "" + CommonData.getUserData().getEntityId()) //1260 585
//                .add("id", "1260") //1260 585
                .build();

        Log.d("code ", "map :: " + commonParams.getMap());

        RestClient.getApiInterface4("https://mymicrobiome.bione.in/").bannerAPI(commonParams.getMap()).enqueue(new ResponseResolver<List<BannerArray>>() {

            @Override
            public void onSuccess(List<BannerArray> commonResponses) {

//                bannerArray = new ArrayList<>();
                Log.d("BannerArray ", " :: " + commonResponses.size());
                for (int i = 0; i < commonResponses.size(); i++) {
                    bannerArray.add(commonResponses.get(i));
//                    bannerArray.add(commonResponses.get(i));
//                    bannerArray.add(commonResponses.get(i));
                }

                Log.d("banner array ", "------ " + bannerArray.size());

                if (bannerArray.size() > 0) {
                    initViewPager();
                }
            }

            @Override
            public void onError(ApiError error) {
                Log.d("onError", "code" + error.getStatusCode());
                Log.d("onError", "message" + error.getMessage());
//                showErrorMessage(error.getMessage());

            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                showErrorMessage(throwable.getMessage());
            }
        });
    }
    private void BannerAPILF() {

        final CommonParams commonParams = new CommonParams.Builder()
                .add("id", "" + CommonData.getUserData().getEntityId()) //1260 585
//                .add("id", "1260") //1260 585
                .build();

        Log.d("code ", "map :: " + commonParams.getMap());

        RestClient.getApiInterface5("https://longifit.bione.in/").bannerAPILF(commonParams.getMap()).enqueue(new ResponseResolver<List<BannerArray>>() {

            @Override
            public void onSuccess(List<BannerArray> commonResponses) {

//                bannerArray = new ArrayList<>();
                Log.d("BannerArray ", " :: " + commonResponses.size());
                for (int i = 0; i < commonResponses.size(); i++) {
                    bannerArray.add(commonResponses.get(i));
//                    bannerArray.add(commonResponses.get(i));
//                    bannerArray.add(commonResponses.get(i));
                }

                Log.d("banner array ", "------ " + bannerArray.size());

                if (bannerArray.size() > 0) {
                    initViewPager();
                }
            }

            @Override
            public void onError(ApiError error) {
                Log.d("onError", "code" + error.getStatusCode());
                Log.d("onError", "message" + error.getMessage());
//                showErrorMessage(error.getMessage());

            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                showErrorMessage(throwable.getMessage());
            }
        });
    }
}

