package com.example.hackathon6_app.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.hackathon6_app.R;
import com.example.hackathon6_app.ui.event.EventFragment;
import com.example.hackathon6_app.ui.poll.PollFragment;
import com.example.hackathon6_app.ui.questions.QuestionsFragment;
import com.google.android.material.tabs.TabLayout;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    private PageAdapter mAdapter;
    private ViewPager mPager;

    private boolean legendSet = false;
    private boolean eventSet = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_agenda, container, false);

        final ImageView legendImg = root.findViewById(R.id.legendImg);
        final ScrollView scrollView = root.findViewById(R.id.scrollView2);
        final Button legendBtn = root.findViewById(R.id.button3);

        // when you click this button
        legendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!legendSet) {
                    legendImg.setImageResource(R.drawable.event_legend);
                    legendSet = true;
                }
                else {
                    legendImg.setImageResource(R.drawable.nsight_agenda);
                    legendSet = false;
                }
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        final ViewGroup event = root.findViewById(R.id.container_event);
        final Button eventBtn = root.findViewById(R.id.button4);

        // when you click this button
        eventBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (eventSet) {
                    event.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                }
                else {
                    event.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                }
                eventSet = !eventSet;
                legendBtn.setClickable(!eventSet);
            }
        });


        mAdapter = new PageAdapter(getActivity().getSupportFragmentManager());

        mPager = root.findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        TabLayout tabs = root.findViewById(R.id.pager_header);
        tabs.setupWithViewPager(mPager, false);

        return root;
    }

    public static class PageAdapter extends FragmentPagerAdapter {
        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return new QuestionsFragment();
                case 2:
                    return new PollFragment();
                case 0:
                default:
                    return new EventFragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 1:
                    return "Questions";
                case 2:
                    return "Poll";
                case 0:
                default:
                    return "Event";
            }
        }
    }
}