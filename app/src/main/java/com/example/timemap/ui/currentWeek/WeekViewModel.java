package com.example.timemap.ui.currentWeek;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeekViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public WeekViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("INFORMATION");
    }

    public LiveData<String> getText() {
        return mText;
    }

    protected void onCleared() {
        super.onCleared();
    }
}
