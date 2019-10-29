package com.example.hackathon6_app.ui.poll;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hackathon6_app.R;

import java.util.ArrayList;
import java.util.Calendar;

public class PollFragment extends Fragment {

    private static final int POLL_TIME_IN_SECONDS = 30;

    private PollViewModel pollViewModel;
    private LinearLayout labelContainer, graphContainer;
    private Spinner loader;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pollViewModel = ViewModelProviders.of(this).get(PollViewModel.class);
        View root = inflater.inflate(R.layout.fragment_poll, container, false);

        labelContainer = root.findViewById(R.id.container_labels);
        graphContainer = root.findViewById(R.id.container_graph);
        loader = root.findViewById(R.id.loader);

        if (pollViewModel.getPollItems().isEmpty()) {
            pollViewModel.initializeSamplePoll();
        }

        initializePoll();

        for (final PollViewModel.PollItem pollItem : pollViewModel.getPollItems()) {
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
        c.add(Calendar.SECOND, POLL_TIME_IN_SECONDS);

        try {
            if (!pollViewModel.isRunning()) {
                pollViewModel.startPoll("How cool is this?", c.getTime());
            }

            final TextView topicTextView = root.findViewById(R.id.text_topic);
            topicTextView.setText(pollViewModel.getTopic());

            final TextView timerTextView = root.findViewById(R.id.text_countdown_timer);
            pollViewModel.getTimerText().observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    timerTextView.setText(s);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return root;
    }

    private void initializePoll() {
        labelContainer.removeAllViews();
        graphContainer.removeAllViews();

        ArrayList<PollViewModel.PollItem> items = pollViewModel.getPollItems();

        graphContainer.setWeightSum(items.size());

        for (PollViewModel.PollItem item : items) {
            View root = getLayoutInflater().inflate(R.layout.template_label, labelContainer, false);
            View key = root.findViewById(R.id.view_key);
            key.setBackgroundResource(item.color);
            TextView label = root.findViewById(R.id.text_label);
            label.setText(item.text);
            labelContainer.addView(root);

            LinearLayout.LayoutParams barLayoutParams = new LinearLayout.LayoutParams(0, 1);
            barLayoutParams.weight = 1;
            barLayoutParams.setMargins(15, 0, 15, 0);

            View bar = new View(graphContainer.getContext());
            bar.setBackgroundResource(item.color);
            graphContainer.addView(bar, barLayoutParams);
        }

        new ViewSwapper(loader, graphContainer).execute(3);
    }

    public static class ViewSwapper extends AsyncTask<Integer, Void, Void> {

        private View toHide;
        private View toShow;

        public ViewSwapper(View toHide, View toShow) {
            this.toHide = toHide;
            this.toShow = toShow;

            this.toHide.setVisibility(View.VISIBLE);
            this.toShow.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int waitCount = integers[0];

            for (int i = 0; i < waitCount; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            doIt();
        }

        private void doIt() {
            this.toHide.setVisibility(View.GONE);
            this.toShow.setVisibility(View.VISIBLE);
        }
    }
}