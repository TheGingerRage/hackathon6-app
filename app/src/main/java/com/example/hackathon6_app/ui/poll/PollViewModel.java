package com.example.hackathon6_app.ui.poll;

import android.os.CountDownTimer;
import android.text.TextUtils;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.hackathon6_app.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PollViewModel extends ViewModel {

    private static int FINAL_COUNT_0 = 35;
    private static int FINAL_COUNT_1 = 75;
    private static int FINAL_COUNT_2 = 55;
    private static int FINAL_COUNT_3 = 100;

    private CountDownTimer timer;
    private long timeRemaining;
    private MutableLiveData<String> timerText;
    
    private int mostVotes;
    private String pollTopic;
    private ArrayList<PollItem> pollItems;

    public PollViewModel() {
        this.timerText = new MutableLiveData<>();
        this.timerText.setValue("Waiting on this...");
        this.pollItems = new ArrayList<>();
        this.mostVotes = 0;
    }

    public void addPollItem(String text, int color) {
        this.pollItems.add(new PollItem(this.pollItems.size(), text, color));
    }

    public boolean isRunning() {
        return timer != null && !TextUtils.isEmpty(this.pollTopic);
    }

    public void stopPoll() {
        timer.onFinish();
    }

    public int getItemHeight(int maxHeight, int votes) {
        if (this.mostVotes == 0) {
            return 0;
        }

        int height = (int) (maxHeight * (votes / (double) this.mostVotes));
        return height == 0 ? 1 : height;
    }

    public ArrayList<PollItem> getPollItems() {
        return this.pollItems;
    }

    public LiveData<String> getTimerText() {
        return this.timerText;
    }

    public String getTopic() {
        return this.pollTopic;
    }

    public void initializeSamplePoll() {
        this.addPollItem("Lame", R.color.blue);
        this.addPollItem("Cool", R.color.yellow);
        this.addPollItem("Super Cool", R.color.red);
        this.addPollItem("Coolest Ever", R.color.green);
    }

    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
        if (this.timeRemaining <= 0) {
            stopPoll();
            return;
        }

        String timeString = String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(this.timeRemaining),
            TimeUnit.MILLISECONDS.toSeconds(this.timeRemaining) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this.timeRemaining))
        );

        this.timerText.setValue(timeString);
    }

    public void startPoll(String topic, Date endTime) throws Exception {
        if (timer != null) {
            throw new Exception("Must stop timer before starting a new one");
        }

        this.pollTopic = topic;
        Date now = new Date();
        long timeRemaining = Math.abs(endTime.getTime() - now.getTime());
        timer = new CountDownTimer(timeRemaining, 1000) {

            public void onTick(long millisUntilFinished) {
                setTimeRemaining(millisUntilFinished);

                if (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 3 == 0) {
                    randomizeVotes(0);
                    randomizeVotes(1);
                    randomizeVotes(2);
                    randomizeVotes(3);
                }
            }

            public void onFinish() {
               closePoll();
            }
        };
        timer.start();
    }

    public void randomizeVotes(int position) {
        int updatesLeft = (int) TimeUnit.MILLISECONDS.toSeconds(this.timeRemaining) / 3;

        int finalCount = 0;

        switch (position) {
            case 0:
                finalCount = FINAL_COUNT_0;
                break;
            case 1:
                finalCount = FINAL_COUNT_1;
                break;
            case 2:
                finalCount = FINAL_COUNT_2;
                break;
            case 3:
                finalCount = FINAL_COUNT_3;
                break;
        }

        int change;
        if (updatesLeft == 0) {
            int total = (int) ((this.mostVotes + 10) * (finalCount / 100.00));
            change = total - this.getVoteCount(position);
        } else {
            change = finalCount / updatesLeft;
        }

        this.updateVotes(position, change);
    }

    private void closePoll() {
        this.timerText.setValue("Poll is closed.");
        timer = null;
    }

    private int getVoteCount(int position) {
        return this.pollItems.get(position).getCount().getValue();
    }

    private void updateVotes(int position, int changeInVotes) {
        int votes = this.getVoteCount(position) + changeInVotes;
        if (votes > mostVotes) {
            mostVotes = votes;
        }

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