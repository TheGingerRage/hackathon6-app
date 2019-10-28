package com.example.hackathon6_app.ui.QR;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class QRFragmentViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public QRFragmentViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}
