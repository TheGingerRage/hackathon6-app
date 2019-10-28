package com.example.hackathon6_app.poll;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;

public class Poll {

    public int mostVotes;
    private String pollTopic;
    private ArrayList<PollItem> pollItems;

    public Poll(String pollTopic) {
        this.pollTopic = pollTopic;
        this.pollItems = new ArrayList<>();
        this.mostVotes = 0;
    }

    public String getTopic() {
        return this.pollTopic;
    }

    public void addPollItem(String text, int color) {
        this.pollItems.add(new Poll.PollItem(this.pollItems.size(), text, color));
    }

    public ArrayList<PollItem> getPollItems() {
        return this.pollItems;
    }

    public int getVoteCount(int position) {
        return this.pollItems.get(position).getCount().getValue();
    }

    public void updateVotes(int position, int changeInVotes) {
        int votes = getVoteCount(position) + changeInVotes;
        if (votes > mostVotes) {
            mostVotes = votes;
        }
        Log.d("Votes", "Updating " + position + " to have " + votes + " votes");
        this.pollItems.get(position).count.setValue(votes);
    }

    public static class PollItem {
        public int position;
        public String text;
        public int color;
        MutableLiveData<Integer> count;

        public PollItem(int position, String text, int color) {
            this(position, text, color, 0);
        }

        public PollItem(int position, String text, int color, int count) {
            this.position = position;
            this.text = text;
            this.color = color;
            this.count = new MutableLiveData<>();
            this.count.setValue(count);
        }

        public void observe(LifecycleOwner owner, Observer<Integer> observer) {
            this.count.observe(owner, observer);
        }

        LiveData<Integer> getCount() {
            return this.count;
        }
    }
}
