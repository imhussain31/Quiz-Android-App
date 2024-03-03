package com.mhndroid.quizapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mhndroid.quizapp.Service.Model.CategoriesModel;
import com.mhndroid.quizapp.Service.Repository.CategoriesRepository;

import java.util.List;

public class CategoriesViewModel extends ViewModel implements CategoriesRepository.onFireStoreTaskComplete {

    // MutableLiveData to store and observe the list of CategoriesModel.
    private MutableLiveData<List<CategoriesModel>> mutableLiveData = new MutableLiveData<>();

    // Instance of CategoriesRepository to fetch categories data.
    private CategoriesRepository categoriesRepository = new CategoriesRepository(this);


    // Getter method to access the MutableLiveData from outside the class.
    public MutableLiveData<List<CategoriesModel>> getMutableLiveData() {
        return mutableLiveData;
    }


    public CategoriesViewModel(){
             categoriesRepository.getCategoriesData();
         }


    // Callback method when categories data is successfully loaded.
    @Override
    public void categoriesDataLoad(List<CategoriesModel> getData) {
            mutableLiveData.setValue(getData);
    }

    // Callback method to handle errors during the data loading process.
    @Override
    public void onError(Exception e) {
        Log.d("Error", "onError" + e.getMessage());
    }
}
