package com.example.timemap.ui.allEvents;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllEventsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public AllEventsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("All events");
    }

    public LiveData<String> getText() {
        return mText;
    }

    protected void onCleared() {
        super.onCleared();
    }
}