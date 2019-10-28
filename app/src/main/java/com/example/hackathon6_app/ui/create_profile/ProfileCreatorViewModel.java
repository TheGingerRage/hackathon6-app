package com.example.hackathon6_app.ui.create_profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileCreatorViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ProfileCreatorViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}
