package com.example.hackathon6_app.ui.speakers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hackathon6_app.R;

public class SpeakersFragment extends Fragment {

    private SpeakersViewModel speakersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        speakersViewModel =
                ViewModelProviders.of(this).get(SpeakersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_speakers, container, false);
        return root;
    }
}