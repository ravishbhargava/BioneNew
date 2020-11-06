package com.bione.ui.myfoodadvice.subcategory;

import androidx.lifecycle.MutableLiveData;

import com.bione.model.CommonListData;

import java.util.ArrayList;
import java.util.List;

public class SubFoodAdviceRepository {

    private static SubFoodAdviceRepository instance;

    private ArrayList<CommonListData> dataSet = new ArrayList<>();

    public static SubFoodAdviceRepository getInstance() {
        if (instance == null) {
            instance = new SubFoodAdviceRepository();
        }
        return instance;
    }

    // Pretend to get data from webservices
    public MutableLiveData<List<CommonListData>> getCommonListData() {
        setListData();

        MutableLiveData<List<CommonListData>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setListData() {
        dataSet.add(new CommonListData(0, "Vegetable", "Veggies", false));
        dataSet.add(new CommonListData(0, "Fruits", "Fruits", false));
        dataSet.add(new CommonListData(0, "Cereals", "Cereals", false));
        dataSet.add(new CommonListData(0, "Ice Cream", "Ice Creams", false));
        dataSet.add(new CommonListData(0, "Cakes", "Desserts", false));
        dataSet.add(new CommonListData(0, "Sweets", "Sweets", false));
    }
}
