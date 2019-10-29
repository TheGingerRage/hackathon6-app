package com.example.hackathon6_app.ui.profile;

import androidx.lifecycle.ViewModel;

import com.example.hackathon6_app.profile.ProfileCard;
import com.example.hackathon6_app.profile.ProfileTracker;
import com.example.hackathon6_app.profile.UserProfile;


import java.util.Arrays;
import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {

    public String profileFirstName;
    public String profileLastName;
    public String profileTitle;
    public String profileCompany;
    public String profileLocation;
    public String profileEmail;
    public String profileAbout;
    public ArrayList<String> profileSocialLinks;

    public ProfileViewModel() {
//        if (profileFirstName == null) {
//            final UserProfile currentUser = UserProfile.getInstance();
//            loadUserProfileData(currentUser);
//
//            loadDummyData();
//        }

     }


//    private void loadDummyData() {
//        // dummy up connections, events, engagements
//        ProfileCard dummyConnection = (new ProfileCard(
//                "Byron",
//                "Motchell",
//                "nCino, Inc.",
//                "Game Developer",
//                "byron.motchell@ncino.com",
//                "Wilmington, NC",
//                "I love Dad jokes",
//                new ArrayList(Arrays.asList(
//                        "linkedin/in/byron.mitchell.999",
//                        "twitter.com/byronmitchell999"))
//        ));
//        ProfileTracker tracker = ProfileTracker.getInstance();
//        tracker.addConnection(dummyConnection);
//        tracker.addEvent();
//        tracker.addEngagement();
//
//    }
//    private void loadUserProfileData(UserProfile currentUser) {
//        this.profileFirstName = currentUser.firstName;
//        this.profileLastName = currentUser.lastName;
//        this.profileCompany = currentUser.company;
//        this.profileLocation = currentUser.location;
//        this.profileTitle = currentUser.title;
//        this.profileEmail = currentUser.email;
//        this.profileAbout =  currentUser.about;
//        this.profileSocialLinks = currentUser.socialLinks;
//    }

}