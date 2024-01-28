package com.example.timemap.ui.detailedEvent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetailedEventViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public DetailedEventViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Event Details");
    }

    public LiveData<String> getText() {
        return mText;
    }

    protected void onCleared() {
        super.onCleared();
    }
}

