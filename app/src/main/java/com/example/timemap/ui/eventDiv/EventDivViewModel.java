package com.example.timemap.ui.eventDiv;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventDivViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public EventDivViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Event list");
    }

    public LiveData<String> getText() {
        return mText;
    }

    protected void onCleared() {
        super.onCleared();
    }
}
