package com.example.timemap.ui.information;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InfoViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public InfoViewModel() {
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
