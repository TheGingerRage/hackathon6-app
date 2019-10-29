package com.example.hackathon6_app.profile;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hackathon6_app.R;

import java.util.ArrayList;

public class ProfileTracker {

    private static MutableLiveData<ArrayList<String>> mActivities;
    private static MutableLiveData<ArrayList<ProfileCard>> mConnections = new MutableLiveData<ArrayList<ProfileCard>>();
    private static MutableLiveData<Integer> mPointCount;
    private static Integer totalEvents;
    private static Integer totalEngagements;

    private final static Integer pointsPerConnection = 20;
    private final static Integer pointsPerEvent = 10;
    private final static Integer pointsPerEngagement = 5;

    private static ProfileTracker activityTracker = null;

    private ProfileTracker() {
        mActivities = new MutableLiveData<ArrayList<String>>();
        mConnections = new MutableLiveData<ArrayList<ProfileCard>>();
        mPointCount = new MutableLiveData<Integer>();
        totalEvents = 0;
        totalEngagements = 0;

        totalEngagements++;
        addActivity("General", "Signed in to nSight 2020 app");
    }

    public static ProfileTracker getInstance()
    {
        if (activityTracker == null)
            activityTracker = new ProfileTracker();

        return activityTracker;
    }

    public static LiveData<ArrayList<String>> getActivities() {
        return mActivities;
    }

    public static void calculatePoints() {
        Integer points = 0;
        Integer connectionCount = 0;
        ArrayList<ProfileCard> connections = mConnections.getValue();
        if (connections != null) {
            connectionCount = connections.size();
        }
        points = (connectionCount * pointsPerConnection) +
                (totalEvents * pointsPerEvent) +
                (totalEngagements * pointsPerEngagement);
        mPointCount.setValue(points);
    }

    public static LiveData<Integer> getPoints() {
        return mPointCount;
    }

    public static int getBadgeImage(Integer points) {
        if (points > 500) {
            return R.mipmap.badge_super_hero;
        } else if (points > 300) {
            return R.mipmap.badge_trophy;
        } else if (points > 100) {
            return R.mipmap.badge_idea_notebook;
        } else if (points > 50) {
            return R.mipmap.badge_craftsman;
        } else if (points > 25) {
            return R.mipmap.badge_artisan;
        } else {
            return R.mipmap.badge_fun;
        }
    }

    public static String getBadgeName(Integer points) {
        if (points > 500) {
            return "Super Hero";
        } else if (points > 300) {
            return "Champion";
        } else if (points > 100) {
            return "Inventor";
        } else if (points > 50) {
            return "Craftsman";
        } else if (points > 25) {
            return "Artisan";
        } else {
            return "Fun";
        }
    }

    public static Integer getConnectionCount() {
        ArrayList<ProfileCard> connections =  mConnections.getValue();
        if (connections == null) return 0;

        return connections.size();
    }

    public static Integer getEventCount() {
        return totalEvents;
    }

    public static Integer getEngagementCount() {
        return totalEngagements;
    }

    public static void addConnection(ProfileCard connection) {
        ProfileCard newConnection = new ProfileCard(
                connection.firstName,
                connection.lastName,
                connection.company,
                connection.title,
                connection.location,
                connection.email,
                connection.about,
                connection.socialLinks);

        ArrayList<ProfileCard> connections = mConnections.getValue();
        if (connections == null) {
            connections = new ArrayList<ProfileCard>();
        }
        connections.add(newConnection);
        mConnections.setValue(connections);
        addActivity("ProfileCard", connection.firstName + ' ' + connection.lastName);
    }

    public static void addEvent() {
        totalEvents++;
        addActivity("Event", "Art of the (Im)Possible keynote address");
    }

    public static void addEngagement() {
        totalEngagements++;
        addActivity("Engagement", "the 'How Cool Is This?' poll");
    }

    public static void addActivity(String type, String activityName) {
        ArrayList<String> activities = mActivities.getValue();
        if (activities == null) {
            activities = new ArrayList<String>();
        }
        switch (type) {
            case "ProfileCard":
                activities.add(0,"Connected with " + activityName);
                break;
            case "Event":
                activities.add(0,"Attended "  + activityName);
                break;
            case "Engagement":
                activities.add(0,"Participated in " + activityName);
                break;
            default:
                activities.add(0,activityName);
        }
        mActivities.setValue(activities);
        calculatePoints();
    }

}

