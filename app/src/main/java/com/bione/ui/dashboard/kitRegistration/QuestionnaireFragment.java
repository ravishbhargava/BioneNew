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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.CommonResponse;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class QuestionnaireFragment extends BaseFragment implements ParentItemAdapter.OnNoteListener {

    private View rootView;
    private Context mContext;

    private RecyclerView recyclerView;
    private ArrayList optionsViews = new ArrayList();
    private int count = 3;

    private List<Datum> datumArrayList;
    private ParentItemAdapter adapter;

    private AppCompatTextView tvContinue;
    private int listSize = 0;


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

            tvContinue = rootView.findViewById(R.id.tvContinue);
            tvContinue.setOnClickListener(this);
            datumArrayList = new ArrayList<>();
//            createNewOptionEntry();

            setRecyclerView();

            CallAPI();


        }
        return rootView;
    }

    private boolean checkAllAnswers(ArrayList<Datum> list, int starting, int last) {
        int end = 0;
        end = last - starting;
        Log.d("size of list :", "----" + list.size());
        for (int i = 0; i < end; i++) {
            if (list.get(i).getAnswer() != null) {
                if (list.get(i).getAnswer().equals("")) {
//                    Toast.makeText(mContext, "Please select all answers blank : " + i, Toast.LENGTH_SHORT).show();
//                    return false;
                } else {
                    Log.d("answer " + i, "---" + list.get(i).getAnswer());
                }
            } else {

//                Toast.makeText(mContext, "Please select all answers : " + i, Toast.LENGTH_SHORT).show();
//                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvContinue:


                Log.d("listSize", "----" + listSize);
//                if (listSize == 0) {
//                    adapter.setArrayList(datumArrayList.subList(0, 8));
////                    recyclerView.setItemViewCacheSize(datumArrayList.subList(0, 8).size());
//                    listSize++;
//
//                } else
                if (listSize == 1) {
//                    if (checkAllAnswers((ArrayList<Datum>) datumArrayList, 0, 8)) {
                    if (checkAllAnswers((ArrayList<Datum>) adapter.getArrayList(), 0, 13)) {
                        listSize++;
                        recyclerView.removeAllViews();
                        recyclerView.setItemViewCacheSize(datumArrayList.subList(13, 26).size());
                        adapter.setArrayList(datumArrayList.subList(13, 26));
                        adapter.notifyDataSetChanged();
                    }
                } else if (listSize == 2) {
                    if (checkAllAnswers((ArrayList<Datum>) adapter.getArrayList(), 13, 26)) {
                        listSize++;
                        recyclerView.removeAllViews();
                        recyclerView.setItemViewCacheSize(datumArrayList.subList(26, datumArrayList.size()).size());
                        adapter.setArrayList(datumArrayList.subList(26, datumArrayList.size()));
                        adapter.notifyDataSetChanged();

                    }
                } else if (listSize == 3) {
//                    Log.d("createJson", "----" + createJson().toString());
                    if (checkAllAnswers((ArrayList<Datum>) adapter.getArrayList(), 26, datumArrayList.size())) {
                        Toast.makeText(mContext, "Questionnaire completed", Toast.LENGTH_SHORT).show();
                        listSize = 0;
                        recyclerView.removeAllViews();
                        recyclerView.setItemViewCacheSize(datumArrayList.subList(0, 13).size());
                        adapter.setArrayList(datumArrayList.subList(0, 13));
                        adapter.notifyDataSetChanged();
                        listSize++;
                        Log.d("createJson", "----" + createJson().toString());
                    }
                }
                break;
        }
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
        datumArrayList = new ArrayList<>();
        adapter = new ParentItemAdapter(datumArrayList, this::onNoteClick, new ParentItemAdapter.OnEditTextChanged() {
            @Override
            public void onTextChanged(int position, String charSeq) {
//                Log.d("onTextChanged " + position, "----" + charSeq);
                datumArrayList.get(position).setAnswer(charSeq);
            }
        });
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
//                    recyclerView.setItemViewCacheSize(datumArrayList.size());
//                    adapter.setArrayList(datumArrayList);
                    listSize = 0;
                    adapter.setArrayList(datumArrayList.subList(0, 13));
                    recyclerView.setItemViewCacheSize(datumArrayList.subList(0, 13).size());
                    listSize++;

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

    public void answerAPI() {
        showLoading();


        RequestBody body =
                RequestBody.create(MediaType.parse("application/json"), createJson().toString());
        RestClient.getApiInterface().answer(body).enqueue(new ResponseResolver<CommonResponse>() {
            @Override
            public void onSuccess(CommonResponse commonResponse) {

                Log.d("onSuccess -----  ", "--");
                Log.d("commonResponse -----  ", commonResponse.toString());

                if (commonResponse.getStatusCode().equals("200")) {

                } else {
                    Toast.makeText(mContext, "Error.", Toast.LENGTH_SHORT).show();
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
    public void onNoteClick(int position) {
        Log.d("onNoteClick", "------" + position);
    }

    private JSONObject createJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            for (int i = 0; i < datumArrayList.size(); i++) {
                JSONObject jsonObject1 = new JSONObject();

                jsonObject1.put("que_id", datumArrayList.get(i).getId());

                if (datumArrayList.get(i).getAnswer() != null) {
                    jsonObject1.put("ans", datumArrayList.get(i).getAnswer());
                } else {
                    jsonObject1.put("ans", "");
                }

                if (datumArrayList.get(i).getOptions() != null && datumArrayList.get(i).getOptions().size() != 0) {
                    Log.d("datumArrayList", "----" + datumArrayList.get(i).getQuestion());
                    jsonObject1.put("option_id", datumArrayList.get(i).getOptions().get(0).getValue());
                } else {
                    jsonObject1.put("option_id", "");
                }
                jsonArray.put(jsonObject1);
            }

            jsonObject.put("data", jsonArray);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
