package com.bione.ui.myfoodadvice.subcategory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bione.model.CommonListData;
import com.bione.ui.myfoodadvice.category.MyFoodAdviceRepository;

import java.util.List;

public class SubFoodAdviceViewModel extends ViewModel {

    private MutableLiveData<List<CommonListData>> commonListData;
    private MyFoodAdviceRepository repository;

    public void init() {
        if (commonListData != null) {
            return;
        }
        repository =  MyFoodAdviceRepository.getInstance();
        commonListData = repository.getCommonListData();
    }

    public LiveData<List<CommonListData>> getCommonListData() {
        return commonListData;
    }

}
