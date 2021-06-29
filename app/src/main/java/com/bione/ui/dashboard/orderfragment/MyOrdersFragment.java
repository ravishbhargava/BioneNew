package com.bione.ui.dashboard.orderfragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.counsellors.ListItem;
import com.bione.model.customerOrders.CustomerOrder;

import com.bione.model.customerOrders.KitOrder;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseFragment;
import com.bione.ui.dashboard.orderfragment.adapter.KitListAdapter;
import com.bione.ui.dashboard.orderfragment.adapter.MyOrderPagerAdapter;
import com.bione.utils.CustomTypefaceSpan;
import com.bione.utils.CustomViewPager;
import com.bione.utils.Log;

import java.util.ArrayList;
import java.util.List;

import static com.bione.utils.AppConstant.PARAM_CUSTOMER;
import static com.bione.utils.AppConstant.SUCCESS;

public class MyOrdersFragment extends BaseFragment {

    private Context mContext;
    private View rootView;
    private AppCompatTextView tvHead;
    private AppCompatImageView noItemImage;

    private CustomViewPager viewPager;
    private TextView tvUpcoming;
    private TextView tvPast;
    private ArrayList<ListItem> counsellorsList = new ArrayList<>();

    private KitListAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<KitOrder> kitOrders = new ArrayList<>();


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
            rootView = inflater.inflate(R.layout.fragment_my_orders, container, false);

            tvHead = rootView.findViewById(R.id.tvHead);
            noItemImage = rootView.findViewById(R.id.noItemImage);

            tvUpcoming = rootView.findViewById(R.id.tvUpcoming);
            tvPast = rootView.findViewById(R.id.tvPast);

            initViewPager();
            callAPI();

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
        }
        return rootView;
    }

    @Override
    public void onClick(View view) {

    }

    private void initViewPager() {
        viewPager = rootView.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPagingEnabled(false);


        // setting viewPager's pages
        final MyOrderPagerAdapter adapter = new MyOrderPagerAdapter(getChildFragmentManager(), 2, new getCounsellingListListener() {
            @Override
            public void onItemClick(String text) {

            }
        });
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

    private void setHeadText(Boolean haveItems) {
        String name = "";
        if (CommonData.getUserData() != null) {
            name = CommonData.getUserData().getFirstname();
        }
        String first = "Hi " + name + " \n";
        String second = "";
        if (haveItems) {
            second = "Kit Register items!";
            noItemImage.setVisibility(View.GONE);
        } else {
            second = "You have placed no \norders";
            noItemImage.setVisibility(View.VISIBLE);
        }

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-Regular.ttf");
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-Bold.ttf");
        tvHead.setText(first + second);
        SpannableStringBuilder SS = new SpannableStringBuilder(tvHead.getText().toString());
        SS.setSpan(new CustomTypefaceSpan("", font), 0, tvHead.getText().toString().length() - second.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        SS.setSpan(new CustomTypefaceSpan("", font2), tvHead.getText().toString().length() - second.length(), tvHead.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvHead.setText(SS);
    }

    private void initRecycler() {
        recyclerView = rootView.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new KitListAdapter(mContext, kitOrders);
        recyclerView.setAdapter(mAdapter);

    }

    private void callAPI() {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
//                .add(PARAM_CUSTOMER, "" + CommonData.getUserData().getEntityId())
                .add(PARAM_CUSTOMER, "585")
                .build();

        RestClient.getApiInterface().kitOrders(commonParams.getMap()).enqueue(new ResponseResolver<List<CustomerOrder>>() {
            @Override
            public void onSuccess(List<CustomerOrder> customerKits) {

                if (customerKits.get(0).getCode() == SUCCESS) {
                    try {

                        Log.d("customer kit ordered", " size :: " + customerKits.get(0).getKitOrders().size());
                        // specify an adapter (see also next example)
                        ArrayList<KitOrder> newKitorders = new ArrayList<>();
                        newKitorders = (ArrayList<KitOrder>) customerKits.get(0).getKitOrders();
                        for (int i = 0; i < newKitorders.size(); i++) {
//                            if (newKitorders.get(i).getSkuCode().equals("MM")) {
                                kitOrders.add(newKitorders.get(i));
//                            }
                        }
                        Log.d("newKitorders", "---" + newKitorders.size());
                        Log.d("kitOrders", "----" + kitOrders.size());

                        if (kitOrders.size() > 0) {
                            setHeadText(true);
                        } else {
                            setHeadText(false);
                        }

                        initRecycler();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (customerKits.get(0).getCode() == 404) {
//                    showErrorMessage("No Reports");
                    setHeadText(false);
                } else {
                    showErrorMessage(customerKits.get(0).getMessage());
                    setHeadText(false);
                }
            }

            @Override
            public void onError(ApiError error) {
                Log.d("onError", "" + error);
                showErrorMessage(error.getMessage());
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                showErrorMessage(throwable.getMessage());
            }
        });
    }

    public interface getCounsellingListListener {
        void onItemClick(final String text);
    }
}
