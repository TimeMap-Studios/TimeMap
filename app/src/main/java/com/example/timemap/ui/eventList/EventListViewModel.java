package com.example.timemap.ui.eventList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventListViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public EventListViewModel() {
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
