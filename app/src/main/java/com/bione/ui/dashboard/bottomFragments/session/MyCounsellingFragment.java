package com.bione.ui.dashboard.bottomFragments.session;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bione.R;
import com.bione.model.counsellors.ListItem;
import com.bione.ui.base.BaseFragment;
import com.bione.ui.dashboard.bottomFragments.schedule.ScheduleCallActivity;
import com.bione.ui.dashboard.bottomFragments.session.adapter.MyCounsellingPagerAdapter;
import com.bione.utils.CustomViewPager;

import java.util.ArrayList;


public class MyCounsellingFragment extends BaseFragment {

    private View rootView;
    private Context mContext;
    private CustomViewPager viewPager;
    private TextView tvUpcoming;
    private TextView tvPast;
    private TextView tvSchedule;

//    private AppCompatImageView ivBack;
    private ArrayList<ListItem> counsellorsList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_session, container, false);

//            ivBack = rootView.findViewById(R.id.ivBack);
            tvUpcoming = rootView.findViewById(R.id.tvUpcoming);
            tvPast = rootView.findViewById(R.id.tvPast);
            tvSchedule = rootView.findViewById(R.id.tvSchedule);

            upcomingSelected(tvUpcoming, tvPast);
//        getCounsellingsAPI();
            initViewPager();
//            ivBack.setVisibility(View.GONE);
//            ivBack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });


            tvUpcoming.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(0);
                    upcomingSelected(tvUpcoming, tvPast);
                }
            });


            tvPast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(1);
                    pastSelected(tvPast, tvUpcoming);
                }
            });


            tvSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ScheduleCallActivity.class);
                    startActivity(intent);
                }
            });
        }
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mycounseling);

    }

    private void initViewPager() {
        viewPager = rootView.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPagingEnabled(false);


        // setting viewPager's pages
        final MyCounsellingPagerAdapter adapter = new MyCounsellingPagerAdapter(getChildFragmentManager(), 2, new getCounsellingListListener() {
            @Override
            public void onItemClick(String text) {

            }
        }, counsellorsList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    private void upcomingSelected(final TextView tvPressed, final TextView tvUnPressed) {
        tvPressed.setBackgroundResource(R.drawable.drawable_left_border_selected);
        tvPressed.setTextColor(getResources().getColor(R.color.white));

        tvUnPressed.setBackgroundResource(R.drawable.drawable_right_border_unselected);
        tvUnPressed.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    private void pastSelected(final TextView tvPressed, final TextView tvUnPressed) {
        tvPressed.setBackgroundResource(R.drawable.drawable_right_border_selected);
        tvPressed.setTextColor(getResources().getColor(R.color.white));

        tvUnPressed.setBackgroundResource(R.drawable.drawable_left_border_unselected);
        tvUnPressed.setTextColor(getResources().getColor(R.color.colorPrimary));
    }


    public ArrayList<ListItem> getList() {
        return counsellorsList;
    }

//
//    private void getCounsellingsAPI() {
//
//        counsellorsList = new ArrayList<>();
//
//        final CommonParams commonParams = new CommonParams.Builder()
//                .add(PARAM_CUSTOMER, CommonData.getUserData().getEntityId())
//                .build();
//
//        RestClient.getApiInterface().getCounsellings(commonParams.getMap()).enqueue(new ResponseResolver<List<Counselling>>() {
//            @Override
//            public void onSuccess(List<Counselling> counsellings) {
//
//                if (counsellings.get(0).getCode() == SUCCESS) {
//                    try {
//
//                        Log.d("customer counsellors", " size :: " + counsellings.get(0).getListItems().size());
//
//                        counsellorsList = (ArrayList<ListItem>) counsellings.get(0).getListItems();
//
//
//                        initViewPager();
//
////                        ArrayList<ListItem> beforeFilterList = (ArrayList<ListItem>) counsellings.get(0).getListItems();
////
////                        for (int i = 0; i < beforeFilterList.size(); i++) {
////                            if (beforeFilterList.get(i).getStatus().equals("0")) {
////                                counsellorsList.add(beforeFilterList.get(i));
////                            }
////                        }
//
//                        // specify an adapter (see also next example)
////                        mAdapter = new CounsellorAdapter(mContext, type, counsellorsList);
////                        recyclerView.setAdapter(mAdapter);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    showErrorMessage(counsellings.get(0).getMessage());
//                }
//            }
//
//            @Override
//            public void onError(ApiError error) {
//                Log.d("onError", "" + error);
//                showErrorMessage(error.getMessage());
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                throwable.printStackTrace();
//                showErrorMessage(throwable.getMessage());
//            }
//        });
//    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {

    }

    public interface getCounsellingListListener {
        void onItemClick(final String text);
    }
}
