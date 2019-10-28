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
import androidx.lifecycle.ViewModelProviders;

import com.example.hackathon6_app.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel= ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        final TextView profileTitleLabel = root.findViewById(R.id.profileTitleLabel);
        profileTitleLabel.setText("Title");
        final TextView profileTitle = root.findViewById(R.id.profileTitle);
        profileTitle.setText(profileViewModel.getTitle().getValue());

        final TextView profileCompanyLabel = root.findViewById(R.id.profileCompanyLabel);
        profileCompanyLabel.setText("Company");
        final TextView companyName = root.findViewById(R.id.companyName);
        companyName.setText(profileViewModel.getCompany().getValue());

        final TextView profileLocationLabel = root.findViewById(R.id.profileLocationLabel);
        profileLocationLabel.setText("Location");
        final TextView profileLocation = root.findViewById(R.id.profileLocation);
        profileLocation.setText(profileViewModel.getLocation().getValue());

        final TextView profileEmailLabel = root.findViewById(R.id.profileEmailLabel);
        profileEmailLabel.setText("Email");
        final TextView profileEmail = root.findViewById(R.id.profileEmail);
        profileEmail.setText(profileViewModel.getEmail().getValue());

        final TextView profileSocialLinksLabel = root.findViewById(R.id.socialLinksLabel);
        profileSocialLinksLabel.setText("SocialLinks");
        ListView socialLinks = root.findViewById(R.id.socialLinks);
        ArrayAdapter<String> socialAdapter = new ArrayAdapter(getContext(), R.layout.social_link_item, profileViewModel.getSocialLinks());
        socialLinks.setAdapter(socialAdapter);

        final TextView profileAboutMeLabel = root.findViewById(R.id.profileAboutLabel);
        profileAboutMeLabel.setText("About Me");
        final TextView profileAbout = root.findViewById(R.id.profileAbout);
        profileAbout.setText(profileViewModel.getAboutMe().getValue());

        final TextView influenceHeaderLabel = root.findViewById(R.id.influenceHeaderLabel);
        influenceHeaderLabel.setText("Influence");

        final TextView activityHeaderLabel = root.findViewById(R.id.activityHeaderLabel);
        activityHeaderLabel.setText("Activity");

        ListView activities = root.findViewById(R.id.profileActivityPane);
        ArrayAdapter<String> activityAdapter = new ArrayAdapter(getContext(), R.layout.profile_activity_item, profileViewModel.getActivities().getValue());
        activities.setAdapter(activityAdapter);

        final ImageView badgeImage = root.findViewById(R.id.influenceBadgeImage);
        final Integer points = profileViewModel.getPoints().getValue();
        badgeImage.setImageResource(profileViewModel.getBadgeImage(profileViewModel.getPoints().getValue()));

        final TextView badgeName = root.findViewById(R.id.influenceBadgeName);
        badgeName.setText(profileViewModel.getBadgeName(profileViewModel.getPoints().getValue()));

        final TextView badgePoints = root.findViewById(R.id.influenceBadgePoints);
        badgePoints.setText("(" +
                profileViewModel.getPoints().getValue() +
                " points)"
        );

        final TextView connectionCount = root.findViewById(R.id.influenceConnectionsCount);
        connectionCount.setText(String.valueOf(profileViewModel.getConnectionCount()));

        final TextView eventCount = root.findViewById(R.id.influenceEventsCount);
        eventCount.setText(String.valueOf(profileViewModel.getEventCount()));

        final TextView engagementCount = root.findViewById(R.id.influenceEngagementsCount);
        engagementCount.setText(String.valueOf(profileViewModel.getEngagementCount()));

        //      listViewAdapater.notifyDataSetChanged();

        return root;
    }
}