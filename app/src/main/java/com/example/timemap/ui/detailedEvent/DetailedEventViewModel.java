package com.example.timemap.ui.detailedEvent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel for managing detailed event data.
 */
public class DetailedEventViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    /**
     * Constructor initializing the ViewModel.
     */
    public DetailedEventViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Event Details");
    }

    /**
     * Retrieves LiveData containing event details.
     *
     * @return LiveData containing event details.
     */
    public LiveData<String> getText() {
        return mText;
    }

    /**
     * Cleans up resources when ViewModel is no longer in use.
     */
    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
