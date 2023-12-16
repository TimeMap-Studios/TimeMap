package com.example.timemap.ui.currentDay;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DayViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public DayViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("INFORMATION");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
