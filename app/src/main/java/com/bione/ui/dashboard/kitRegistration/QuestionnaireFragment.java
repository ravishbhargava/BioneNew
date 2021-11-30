package com.bione.ui.dashboard.kitRegistration;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.questionnaire.Datum;
import com.bione.model.questionnaire.Questionnaire;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseFragment;
import com.bione.utils.Log;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class QuestionnaireFragment extends BaseFragment {

    private View rootView;
    private Context mContext;

    private RecyclerView recyclerView;
    private ArrayList optionsViews = new ArrayList();
    private int count = 3;

    private ArrayList<Datum> datumArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_questionnaire, container, false);

            datumArrayList = new ArrayList<>();
//            createNewOptionEntry();

//            setRecyclerView();
            CallAPI();

        }
        return rootView;
    }

    @Override
    public void onClick(View view) {

    }

    private void setEditText() {
        //Defining 5 views
    }

    private void createNewOptionEntry() {

        View parent = (View) rootView.findViewById(R.id.root);

        LinearLayout.LayoutParams fLayout = (LinearLayout.LayoutParams) parent.getLayoutParams();

        TextInputLayout textInputLayout = new TextInputLayout(mContext);
        textInputLayout.setLayoutParams(fLayout);
        textInputLayout.setHint(count++ + ". Option");

        LinearLayout.LayoutParams lLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final TextInputEditText editText = new TextInputEditText(mContext);
        editText.setLayoutParams(lLayout);
        int id = View.generateViewId();
        optionsViews.add(id);
        editText.setId(id);

        textInputLayout.addView(editText);

//        return textInputLayout;
    }

    private void setRadioButton(final int buttons) {
        //Defining 5 buttons.

        AppCompatRadioButton[] rb = new AppCompatRadioButton[buttons];

        RadioGroup rgp = (RadioGroup) rootView.findViewById(R.id.radio_group);
        rgp.setOrientation(LinearLayout.VERTICAL);

        for (int i = 1; i <= buttons; i++) {
            RadioButton rbn = new RadioButton(mContext);
            rbn.setId(View.generateViewId());
            rbn.setText("RadioButton" + i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            rbn.setLayoutParams(params);
            rgp.addView(rbn);

        }
        Log.d("radio selection ", "----" + rgp.getCheckedRadioButtonId());
        rgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) rootView.findViewById(checkedId);
                Toast.makeText(mContext, radioButton.getText(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setRecyclerView() {

        ParentItemAdapter adapter = new ParentItemAdapter(datumArrayList);
//        ParentRecyclerViewAdapter adapter = new ParentRecyclerViewAdapter(mContext, datumArrayList);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize(datumArrayList.size());
        recyclerView.setAdapter(adapter);

    }


    private void CallAPI() {

        final CommonParams commonParams = new CommonParams.Builder()
                .build();

        RestClient.getApiInterface().quest(commonParams.getMap()).enqueue(new ResponseResolver<Questionnaire>() {
            @Override
            public void onSuccess(Questionnaire commonResponses) {

                if (commonResponses.getCode().equals("200")) {
                    Log.d("onSuccess", "-----" + commonResponses.getCode());
                    Log.d("list size", "-----" + commonResponses.getData().size());
                    datumArrayList = (ArrayList) commonResponses.getData();
                    Log.d("datumArrayList", "-----" + datumArrayList.size());
                    setRecyclerView();
                } else {
                    showErrorMessage(commonResponses.getMessage());
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
}
