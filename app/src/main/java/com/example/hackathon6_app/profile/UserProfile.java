package com.example.hackathon6_app.profile;

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
        firstName = "Tracy";
        lastName = "Bragdon";
        company = "nCino";
        location = "Wilmington, NC";
        title = "FunLeSS developer";
        email = "tracy.bragdon@ncino.com";
        about =  "I love ice cream and sweep rowing";
        socialLinks = new ArrayList(Arrays.asList("https://www.linkedin.com/in/tracybragdon/"));
    }

}
