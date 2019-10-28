package com.example.hackathon6_app.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hackathon6_app.R;
import com.example.hackathon6_app.profile.ProfileCard;

import java.util.Arrays;
import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {



    private MutableLiveData<String> mName;
    private MutableLiveData<String> mTitle;
    private MutableLiveData<String> mCompany;
    private MutableLiveData<String> mLocation;
    private MutableLiveData<String> mEmail;
    private MutableLiveData<String[]> mSocialLinks;
    private MutableLiveData<String> mAboutMe;
    private MutableLiveData<Integer> mPointCount;
    private MutableLiveData<ArrayList<String>> mActivities;
    private MutableLiveData<ArrayList<ProfileCard>> mConnections = new MutableLiveData<ArrayList<ProfileCard>>();
    private Integer events;
    private Integer engagements;

    private final Integer pointsPerConnection = 20;
    private final Integer pointsPerEvent = 10;
    private final Integer pointsPerEngagement = 5;

    private String[] socials = {
            "linkedin.com/in/rebecca.jackson313",
            "twitter.com/jinglebex",
            "ncino.com/ncino--iq"
    };


    public ProfileViewModel() {

        mName = new MutableLiveData<>();
        mName.setValue("Becka Jackson");

        mTitle = new MutableLiveData<>();
        mTitle.setValue("Technical Writer");

        mCompany = new MutableLiveData<>();
        mCompany.setValue("nCino");

        mLocation = new MutableLiveData<>();
        mLocation.setValue("Wilmington, NC");

        mEmail = new MutableLiveData<>();
        mEmail.setValue("becka.jackson@ncino.com");

//        mSocialLinks = new MutableLiveData<>();
//        mSocialLinks.setValue(socials);

        mAboutMe = new MutableLiveData<>();
        mAboutMe.setValue("I write for the nCino IQ team. I love dogs and my favorite season is fall.");

        mPointCount = new MutableLiveData<>();
        mActivities = new MutableLiveData<ArrayList<String>>();



        events = 0;
        engagements = 0;
        // dummy up connections, events, engagements
        ProfileCard dummyConnection = (new ProfileCard(
            "Byron",
            "Motchell",
            "nCino, Inc.",
            "Developer",
            "byron.motchell@ncino.com",
            "Wilmington, NC",
            "blah blah blah",
            new ArrayList(Arrays.asList(
                    "linkedin/in/byron.mitchell.999",
                    "twitter.com/byronmitchell999"))
        ));
        addConnection(dummyConnection);
        addEvent();
        addEngagement();
    }



    public LiveData<String> getTitle() {
        return mTitle;
    }
    public LiveData<String> getCompany() {
        return mCompany;
    }
    public LiveData<String> getLocation() {
        return mLocation;
    }
    public LiveData<String> getEmail() {
        return mEmail;
    }
    public String[] getSocialLinks() { return socials; }
    public LiveData<String> getAboutMe() { return mAboutMe; }

    public LiveData<ArrayList<String>> getActivities() {
        return mActivities;
    }


    public void calculatePoints() {
        Integer points = 0;
        points = (mConnections.getValue().size() * pointsPerConnection) +
                (events * pointsPerEvent) +
                (engagements * pointsPerEngagement);
        mPointCount.setValue(points);
    }

    public LiveData<Integer> getPoints() {
        return mPointCount;
    }

    public int getBadgeImage(Integer points) {
        if (points > 500) {
            return R.mipmap.badge_super_hero;
        } else if (points > 400) {
            return R.mipmap.badge_trophy;
        } else if (points > 300) {
            return R.mipmap.badge_handshake;
        } else if (points > 200) {
            return R.mipmap.badge_navigator;
        } else if (points > 100) {
            return R.mipmap.badge_idea_notebook;
        } else {
            return R.mipmap.badge_fun;
        }
    }

    public String getBadgeName(Integer points) {
        if (points > 500) {
            return "Super Hero";
        } else if (points > 400) {
            return "Champion";
        } else if (points > 300) {
            return "Influencer";
        } else if (points > 200) {
            return "Visionary";
        } else if (points > 100) {
            return "Inventor";
        } else {
            return "Fun";
        }
    }

    public Integer getConnectionCount() {
        return mConnections.getValue()
                .size();
    }

    public Integer getEventCount() {
        return events;
    }

    public Integer getEngagementCount() {
        return engagements;
    }


    public void addConnection(ProfileCard connection) {
        ProfileCard newConnection = new ProfileCard(
                connection.getFirstName(),
                connection.getLastName(),
                connection.getCompany(),
                connection.getTitle(),
                connection.getLocation(),
                connection.getEmail(),
                connection.getAboutMe(),
                connection.getSocialLinks());
        ArrayList<ProfileCard> connections = mConnections.getValue();
        if (connections == null) {
            connections = new ArrayList<ProfileCard>();
        }
        connections.add(newConnection);
        mConnections.setValue(connections);
        addActivity("ProfileCard", connection.getFullName());
    }

    public void addEvent() {
        events++;
        addActivity("Event", "Art of the (Im)Possible keynote address");
    }

    public void addEngagement() {
        engagements++;
        addActivity("Engagement", "some kind of poll");
    }

    public void addActivity(String type, String activityName) {
        ArrayList<String> activities = mActivities.getValue();
        if (activities == null) {
            activities = new ArrayList<String>();
        }
        switch (type) {
            case "ProfileCard":
               activities.add("Connected with " + activityName);
                break;
            case "Event":
                activities.add("Attended "  + activityName);
                break;
            case "Engagement":
                activities.add("Participated in " + activityName);
                break;
            default:
                activities.add(activityName);
        }
        mActivities.setValue(activities);
        calculatePoints();
    }

}