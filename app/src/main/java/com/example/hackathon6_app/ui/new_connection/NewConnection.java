package com.example.hackathon6_app.ui.new_connection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hackathon6_app.R;
import com.example.hackathon6_app.bl.Profile;
import com.example.hackathon6_app.profile.ProfileCard;
import com.example.hackathon6_app.profile.ProfileTracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;



public class NewConnection extends Fragment {

    private ProfileCard NewConnectionProfile = null;
    private final ProfileTracker tracker = ProfileTracker.getInstance();

    public NewConnection(String newConnectionString){
        if(!newConnectionString.isEmpty()) {
            NewConnectionProfile = DeserializeProfile(newConnectionString);
        }
     }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NewConnectionViewModel newConnectionViewModel = ViewModelProviders.of(this).get(NewConnectionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);

        tracker.addConnection(NewConnectionProfile);


        TextView nameTextView = root.findViewById(R.id.nameTextView);
        TextView titleTextView = root.findViewById(R.id.titleValueTextView);
        TextView companyTextView = root.findViewById(R.id.companyValueTextView);

        nameTextView.setText(NewConnectionProfile.firstName + " " + NewConnectionProfile.lastName);
        titleTextView.setText("Hard Coded Title Value");
        companyTextView.setText(NewConnectionProfile.company);

        return root;
    }

    private ProfileCard DeserializeProfile(String jsonProfile){
        if(!jsonProfile.isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectReader objectReader = objectMapper.reader();

            try{
                ProfileCard profile = (ProfileCard) objectReader.readValue(jsonProfile);
                return profile;
            }
            catch(JsonProcessingException e){
                // do some error handling
            }
            catch(IOException e){
                // do some generic error handling
            }

        }

        return null;
    }
}
