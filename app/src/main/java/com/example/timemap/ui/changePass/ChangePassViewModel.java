package com.example.timemap.ui.changePass;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChangePassViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    public ChangePassViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("Change Password");
    }
}
