package com.example.hackathon6_app.ui.create_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hackathon6_app.R;
import com.example.hackathon6_app.bl.StaticResources;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileCreator extends Fragment {
    private ProfileCreatorViewModel profileCreatorViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileCreatorViewModel =
                ViewModelProviders.of(this).get(ProfileCreatorViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_profile_creator, container, false);

        Button saveButton = root.findViewById(R.id.profileSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText firstName = root.findViewById(R.id.profileFirstName);
                EditText lastName = root.findViewById(R.id.profileLastName);
                EditText company = root.findViewById(R.id.profileCompany);
                EditText title = root.findViewById(R.id.profileTitle);
                EditText email = root.findViewById(R.id.profileEmail);
                EditText location = root.findViewById(R.id.profileLocation);
                EditText socialLinks = root.findViewById(R.id.profileSocialLinks);
                
                StaticResources.Main_Profile.firstName = firstName.getText().toString();
                StaticResources.Main_Profile.lastName = lastName.getText().toString();
                StaticResources.Main_Profile.company = company.getText().toString();
                StaticResources.Main_Profile.title = title.getText().toString();
                StaticResources.Main_Profile.email = email.getText().toString();
                StaticResources.Main_Profile.location = location.getText().toString();
                StaticResources.Main_Profile.socialLinks = ParseSocialLinks(socialLinks.getText().toString());
            }
        });


        return root;
    }

    private ArrayList<String> ParseSocialLinks(String text){
        String[] socialLinks = text.split(";");

        ArrayList<String> parsedSocialLinks = new ArrayList<String>();
        parsedSocialLinks.addAll(Arrays.asList(socialLinks));

        return parsedSocialLinks;
    }
}
