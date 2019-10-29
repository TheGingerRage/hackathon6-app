package com.example.hackathon6_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.hackathon6_app.profile.ProfileCard;
import com.example.hackathon6_app.profile.ProfileTracker;
import com.example.hackathon6_app.ui.PlaceholderFragment;
import com.example.hackathon6_app.ui.QR.QRFragment;
import com.example.hackathon6_app.ui.poll.PollFragment;
import com.example.hackathon6_app.ui.profile.ProfileFragment;
import com.example.hackathon6_app.ui.questions.QuestionsFragment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

public class ProfileActivity extends NavigationActivity {

    private static final int NUM_ITEMS = 4;

    private PageAdapter mAdapter;
    private ViewPager mPager;

    private final ProfileTracker tracker = ProfileTracker.getInstance();

    @Override
    public int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new PageAdapter(getSupportFragmentManager());

        mPager = findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        TabLayout tabs = findViewById(R.id.pager_header);
        tabs.setupWithViewPager(mPager, false);
    }

    public static class PageAdapter extends FragmentPagerAdapter {
        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PollFragment();
                case 1:
                    return new QuestionsFragment();
                case 2:
                    return new ProfileFragment();
                case 3:
                    return new QRFragment();
                case 4:
                default:
                    return new PlaceholderFragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Poll Sample";
                case 1:
                    return "Questions Sample";
                case 2:
                    return "Profile";
                case 3:
                    return "My Code";
                case 4:
                default:
                    return "Replace Me!";
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.e("Scan*******", "Cancelled scan");

            } else {
                Log.e("Scan", "Scanned: " + result.getContents());


                TextView tview = findViewById(R.id.nameTextView);
                tview.setText("hello, world!");

                ProfileCard profile = DeserializeProfile(result.getContents());

                if(profile != null){
                    ProcessNewConnection(profile);
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void ProcessNewConnection(ProfileCard newConnection){
        LinearLayout scanLayout = findViewById(R.id.scanResult);

        tracker.addConnection(newConnection);

        scanLayout.setVisibility(View.VISIBLE);
        TextView congratsText = findViewById(R.id.congratsText);
        TextView nameText = findViewById(R.id.nameTextView);
        TextView title = findViewById(R.id.titleLabelTextView);
        TextView titleText = findViewById(R.id.titleValueTextView);
        TextView company = findViewById(R.id.companyLabelTextView);
        TextView companyText = findViewById(R.id.companyValueTextView);

        congratsText.setVisibility(View.VISIBLE);

        title.setVisibility(View.VISIBLE);
        company.setVisibility(View.VISIBLE);

        titleText.setVisibility(View.VISIBLE);
        companyText.setVisibility(View.VISIBLE);

        nameText.setText(newConnection.firstName + " " + newConnection.lastName);
        titleText.setText(newConnection.title);
        companyText.setText(newConnection.company);


    }

    private ProfileCard DeserializeProfile(String jsonProfile){
        if(!jsonProfile.isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();

            try{
                ProfileCard profile = objectMapper.readValue(jsonProfile, ProfileCard.class);
                return profile;
            }
            catch(JsonProcessingException e){
                // do some error handling
            }
            catch(IOException e){
                // do some generic error handling
            }

        }

        ProfileCard p = new ProfileCard();
        p.firstName = "Something went ";
        p.lastName = "wrong.";

        return p;
    }
}