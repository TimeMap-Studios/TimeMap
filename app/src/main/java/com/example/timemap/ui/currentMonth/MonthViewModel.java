package com.example.timemap.ui.currentMonth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MonthViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public MonthViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("INFORMATION");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
