package com.example.hackathon6_app.ui.poll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hackathon6_app.R;
import com.example.hackathon6_app.poll.Poll;

import java.util.ArrayList;
import java.util.Calendar;

public class PollFragment extends Fragment {

    private static final int POLL_TIME_IN_MINUTES = 1;

    private PollViewModel pollViewModel;
    private LinearLayout graphContainer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pollViewModel = ViewModelProviders.of(this).get(PollViewModel.class);
        View root = inflater.inflate(R.layout.fragment_poll, container, false);

        pollViewModel.initializePoll("How cool is this?", this.getResources());

        final TextView timerTextView = root.findViewById(R.id.text_countdown_timer);
        pollViewModel.getTimerText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                timerTextView.setText(s);
            }
        });

        final TextView topicTextView = root.findViewById(R.id.text_topic);
        topicTextView.setText(pollViewModel.getTopic());

        graphContainer = root.findViewById(R.id.container_graph);
        initializeGraph();

        for (final Poll.PollItem pollItem : pollViewModel.getPollItems()) {
            pollItem.observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer count) {
                    View v = graphContainer.getChildAt(pollItem.position);
                    int height = pollViewModel.getItemHeight(graphContainer.getHeight(), count);

                    ViewGroup.LayoutParams params = v.getLayoutParams();
                    params.height = height;
                    v.setLayoutParams(params);
                }
            });
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, POLL_TIME_IN_MINUTES);

        try {
            pollViewModel.startPoll(c.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return root;
    }

    private void initializeGraph() {
        ArrayList<Poll.PollItem> items = pollViewModel.getPollItems();
        graphContainer.setWeightSum(items.size());

        for (Poll.PollItem item : items) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 1);
            layoutParams.weight = 1;
            layoutParams.setMargins(5, 0, 5, 0);

            View v = new View(graphContainer.getContext());
            v.setBackgroundResource(item.color);
            graphContainer.addView(v, layoutParams);
        }
    }
}