package com.example.hackathon6_app.profile;

import com.example.hackathon6_app.bl.StaticResources;

import java.util.ArrayList;
import java.util.Arrays;

public class UserProfile extends ProfileCard {

    private static UserProfile currentUser = null;

    private UserProfile() {
        loadDummyProfile();
    }

    public static UserProfile getInstance()
    {
        if (currentUser == null)
            currentUser = new UserProfile();

        return currentUser;
    }

    private void loadDummyProfile() {
        firstName = StaticResources.Main_Profile.firstName;
        lastName = StaticResources.Main_Profile.lastName;
        company = StaticResources.Main_Profile.company;
        location =StaticResources.Main_Profile.location;
        title = StaticResources.Main_Profile.title;
        email = StaticResources.Main_Profile.email;
        about =  StaticResources.Main_Profile.about;
        socialLinks = StaticResources.Main_Profile.socialLinks;
    }

}
