package com.bione.ui.Counselling;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.counsellors.Counselling;
import com.bione.model.counsellors.ListItem;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.Counselling.adapter.CounsellorAdapter;
import com.bione.ui.base.BaseFragment;
import com.bione.utils.Log;

import java.util.ArrayList;
import java.util.List;

import static com.bione.utils.AppConstant.PARAM_CUSTOMER;
import static com.bione.utils.AppConstant.SUCCESS;

public class UpcomingFragment extends BaseFragment {

    private View rootView;
    private CounsellorAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Context mContext;
    private String type = "Upcoming";

    private ArrayList<ListItem> counsellorsList = new ArrayList<>();
    private MyCounsellingFragment.getCounsellingListListener listener;


    public UpcomingFragment(final MyCounsellingFragment.getCounsellingListListener listener, final ArrayList<ListItem> counsellorsList) {
        this.listener = listener;
        this.counsellorsList = counsellorsList;
    }


    @Override
    public void onAttach(@NonNull Context context) {
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
            rootView = inflater.inflate(R.layout.fragment_upcoming, container, false);
            initRecycler(rootView);
//            getCounsellingsAPI();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initRecycler(final View view) {
        counsellorsList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);


        // specify an adapter (see also next example)
        mAdapter = new CounsellorAdapter(mContext, type, counsellorsList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCounsellingsAPI();
    }

    private void getCounsellingsAPI() {

        counsellorsList = new ArrayList<>();

        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_CUSTOMER, CommonData.getUserData().getEntityId())
                .build();

        RestClient.getApiInterface().getCounsellings(commonParams.getMap()).enqueue(new ResponseResolver<List<Counselling>>() {
            @Override
            public void onSuccess(List<Counselling> counsellings) {

                if (counsellings.get(0).getCode() == SUCCESS) {
                    try {

                        Log.d("customer counsellors", " size :: " + counsellings.get(0).getListItems().size());

                        ArrayList<ListItem> beforeFilterList = (ArrayList<ListItem>) counsellings.get(0).getListItems();

                        for (int i = 0; i < beforeFilterList.size(); i++) {
                            if (beforeFilterList.get(i).getStatus().equals("0")) {
                                counsellorsList.add(beforeFilterList.get(i));
                            }
                        }

                        // specify an adapter (see also next example)
                        mAdapter = new CounsellorAdapter(mContext, type, counsellorsList);
                        recyclerView.setAdapter(mAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showErrorMessage(counsellings.get(0).getMessage());
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


    @Override
    public void onClick(View view) {

    }
}
