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
import com.bione.model.Data;
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

//            createNewOptionEntry();
            setRecyclerView();

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

    private void setRadioButton() {
        //Defining 5 buttons.
        int buttons = 5;
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
        ArrayList<Data> dataList = new ArrayList<>();
        dataList.add(new Data(1, "1. Hi! I am in View 1"));
        dataList.add(new Data(2, "2. Hi! I am in View 2"));
        dataList.add(new Data(1, "3. Hi! I am in View 3"));
        dataList.add(new Data(2, "4. Hi! I am in View 4"));
        dataList.add(new Data(1, "1. Hi! I am in View 1"));
        dataList.add(new Data(2, "2. Hi! I am in View 2"));
        dataList.add(new Data(1, "3. Hi! I am in View 3"));
        dataList.add(new Data(2, "4. Hi! I am in View 4"));
        dataList.add(new Data(1, "1. Hi! I am in View 1"));
        dataList.add(new Data(2, "2. Hi! I am in View 2"));
        dataList.add(new Data(1, "3. Hi! I am in View 3"));
        dataList.add(new Data(2, "4. Hi! I am in View 4"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mContext, dataList);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
    }
}
