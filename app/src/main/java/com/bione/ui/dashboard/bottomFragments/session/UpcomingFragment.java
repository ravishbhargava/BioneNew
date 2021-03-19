package com.bione.ui.dashboard.bottomFragments.session;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.CommonResponse;
import com.bione.model.counsellors.Counselling;
import com.bione.model.counsellors.ListItem;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseFragment;
import com.bione.ui.dashboard.bottomFragments.session.adapter.CounsellorAdapter;
import com.bione.utils.CommonUtil;
import com.bione.utils.Log;

import java.util.ArrayList;
import java.util.List;

import static com.bione.utils.AppConstant.PARAM_CANCELATION;
import static com.bione.utils.AppConstant.PARAM_CUSTOMER;
import static com.bione.utils.AppConstant.PARAM_ENTITY_ID;
import static com.bione.utils.AppConstant.PARAM_STATUS;
import static com.bione.utils.AppConstant.SUCCESS;

public class UpcomingFragment extends BaseFragment {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 101;
    private View rootView;
    private CounsellorAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Context mContext;
    private String type = "Upcoming";

    private ArrayList<ListItem> counsellorsList = new ArrayList<>();
    private com.bione.ui.dashboard.bottomFragments.session.MyCounsellingFragment.getCounsellingListListener listener;


    public UpcomingFragment(final com.bione.ui.dashboard.bottomFragments.session.MyCounsellingFragment.getCounsellingListListener listener,
                            final ArrayList<ListItem> counsellorsList) {
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
        mAdapter = new CounsellorAdapter(mContext, type, counsellorsList, new com.bione.ui.dashboard.bottomFragments.session.OnclickItemCounsellor() {
            @Override
            public void onItemClick(final int position, final String actionType) {
                if (actionType.equals("call")) {
                    callPhoneCHeckPermission();
                } else if (actionType.equals("cancel")) {
                    openDialog(position);
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCounsellingsAPI();
    }

    private void getCounsellingsAPI() {
        showLoading();
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
                        mAdapter.refreshEvents(counsellorsList);

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

    private void callPhoneCHeckPermission() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
            Log.d("--------", "MY_PERMISSIONS_REQUEST_CALL_PHONE");
            // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {
            //You already have permission
            Log.d("--------", "You already have permission");
            CommonUtil.makeCall(mContext);
        }
    }

    private void call(int position, String cancelReason) {

        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_ENTITY_ID, counsellorsList.get(position).getMobilecounsellingId())
                .add(PARAM_STATUS, 2)
                .add(PARAM_CANCELATION, cancelReason)

                .build();

        RestClient.getApiInterface().cancelBooking(commonParams.getMap()).enqueue(new ResponseResolver<List<CommonResponse>>() {
            @Override
            public void onSuccess(List<CommonResponse> commonResponses) {

                if (commonResponses.get(0).getStatusCode().equals("200")) {
                    getCounsellingsAPI();
//                    showErrorMessage(commonResponses.get(0).getMessage());
                } else {
                    showErrorMessage(commonResponses.get(0).getMessage());
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

    private void openDialog(int position) {
        // custom dialog
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_cancel_reason);
//        dialog.getWindow().setBackgroundDrawable(null);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
//        AppCompatEditText etOtp = dialog.findViewById(R.id.etOtp);
        AppCompatTextView tvOk = dialog.findViewById(R.id.tvOk);

        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
        final RadioButton[] radioButton = new RadioButton[1];


        // if button is clicked, close the custom dialog
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(mContext, "Please select Reason", Toast.LENGTH_SHORT).show();
                } else {
                    // get selected radio button from radioGroup
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    radioButton[0] = (RadioButton) dialog.findViewById(selectedId);
                    dialog.dismiss();
                    call(position, radioButton[0].getText().toString());
                }

            }
        });

        dialog.show();
    }
}
