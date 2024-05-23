package com.example.sivercare.ui.adminmain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdminMainViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AdminMainViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}