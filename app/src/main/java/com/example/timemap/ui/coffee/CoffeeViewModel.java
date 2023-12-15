package com.example.timemap.ui.coffee;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CoffeeViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    public CoffeeViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("Buy me a coffee");
    }
    public LiveData<String> getText() {
        return mText;
    }
}
