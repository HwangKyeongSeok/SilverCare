package com.example.sivercare.ui.more;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StaffMoreViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public StaffMoreViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}