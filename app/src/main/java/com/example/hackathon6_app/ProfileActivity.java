package com.example.hackathon6_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.hackathon6_app.ui.PlaceholderFragment;
import com.example.hackathon6_app.ui.poll.PollFragment;
import com.example.hackathon6_app.ui.profile.ProfileFragment;
import com.example.hackathon6_app.ui.questions.QuestionsFragment;
import com.google.android.material.tabs.TabLayout;

public class ProfileActivity extends NavigationActivity {

    private static final int NUM_ITEMS = 5;

    private PageAdapter mAdapter;
    private ViewPager mPager;

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
                case 4:
                default:
                    return "Replace Me!";
            }
        }
    }
}