package com.example.hackathon6_app.ui.speakers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SpeakersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SpeakersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is speakers fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}