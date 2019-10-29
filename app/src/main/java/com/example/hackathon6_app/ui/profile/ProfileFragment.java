package com.example.hackathon6_app.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import com.example.hackathon6_app.R;
import com.example.hackathon6_app.profile.ProfileCard;
import com.example.hackathon6_app.profile.ProfileTracker;
import com.example.hackathon6_app.profile.UserProfile;


public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private ArrayAdapter<String> activityAdapter;
    private final ProfileTracker tracker = ProfileTracker.getInstance();
    private final UserProfile currentUser = UserProfile.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_profile, container, false);

        final TextView profileTitleLabel = root.findViewById(R.id.profileTitleLabel);
        profileTitleLabel.setText("Title");
        final TextView profileTitle = root.findViewById(R.id.profileTitle);
        profileTitle.setText(currentUser.title);

        final TextView profileCompanyLabel = root.findViewById(R.id.profileCompanyLabel);
        profileCompanyLabel.setText("Company");
        final TextView companyName = root.findViewById(R.id.companyName);
        companyName.setText(currentUser.company);

        final TextView profileLocationLabel = root.findViewById(R.id.profileLocationLabel);
        profileLocationLabel.setText("Location");
        final TextView profileLocation = root.findViewById(R.id.profileLocation);
        profileLocation.setText(currentUser.location);

        final TextView profileEmailLabel = root.findViewById(R.id.profileEmailLabel);
        profileEmailLabel.setText("Email");
        final TextView profileEmail = root.findViewById(R.id.profileEmail);
        profileEmail.setText(currentUser.email);

        final TextView profileSocialLinksLabel = root.findViewById(R.id.socialLinksLabel);
        profileSocialLinksLabel.setText("SocialLinks");
        ListView socialLinks = root.findViewById(R.id.socialLinks);
        ArrayAdapter<String> socialAdapter = new ArrayAdapter(getContext(), R.layout.social_link_item, currentUser.socialLinks);
        socialLinks.setAdapter(socialAdapter);

        final TextView profileAboutMeLabel = root.findViewById(R.id.profileAboutLabel);
        profileAboutMeLabel.setText("About Me");
        final TextView profileAbout = root.findViewById(R.id.profileAbout);
        profileAbout.setText(currentUser.about);

        final TextView influenceHeaderLabel = root.findViewById(R.id.influenceHeaderLabel);
        influenceHeaderLabel.setText("Influence");

        final TextView activityHeaderLabel = root.findViewById(R.id.activityHeaderLabel);
        activityHeaderLabel.setText("Activity");

        ListView activities = root.findViewById(R.id.profileActivityPane);
        activityAdapter = new ArrayAdapter(getContext(), R.layout.profile_activity_item, tracker.getActivities().getValue());
        ArrayList<String> temp = tracker.getActivities().getValue();
        activities.setAdapter(activityAdapter);

        updateBadgeViews(root);
        updateConnectionCountView(root);
        updateEventCountView(root);
        updateEngagementCountView(root);

        tracker.mConnections.observe(this, new Observer<ArrayList<ProfileCard>>() {
            @Override
            public void onChanged(ArrayList<ProfileCard> connections) {
                if (connections.isEmpty()) {
                    return;
                }

 //               activityAdapter.notifyDataSetChanged();

                updateConnectionCountView(root);
                updateBadgeViews(root);

            }
        });


        return root;
    }

    private void updateBadgeViews(View root) {
        final TextView badgePoints = root.findViewById(R.id.influenceBadgePoints);
        badgePoints.setText("(" +
                tracker.getPoints().getValue() +
                " points)"
        );

        final ImageView badgeImage = root.findViewById(R.id.influenceBadgeImage);
        final Integer points = tracker.getPoints().getValue();
        badgeImage.setImageResource(tracker.getBadgeImage(tracker.getPoints().getValue()));

        final TextView badgeName = root.findViewById(R.id.influenceBadgeName);
        badgeName.setText(tracker.getBadgeName(tracker.getPoints().getValue()));
    }

    private void updateConnectionCountView(View root) {
        final TextView connectionCount = root.findViewById(R.id.influenceConnectionsCount);
        connectionCount.setText(String.valueOf(tracker.getConnectionCount()));
    }

    private void updateEventCountView(View root) {
        final TextView eventCount = root.findViewById(R.id.influenceEventsCount);
        eventCount.setText(String.valueOf(tracker.getEventCount()));
    }

    private void updateEngagementCountView(View root) {
        final TextView engagementCount = root.findViewById(R.id.influenceEngagementsCount);
        engagementCount.setText(String.valueOf(tracker.getEngagementCount()));
    }

}