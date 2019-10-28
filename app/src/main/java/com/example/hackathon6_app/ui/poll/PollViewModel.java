package com.example.hackathon6_app.ui.poll;

import android.content.res.Resources;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hackathon6_app.R;
import com.example.hackathon6_app.poll.Poll;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PollViewModel extends ViewModel {

    private static int FINAL_COUNT_0 = 35;
    private static int FINAL_COUNT_1 = 75;
    private static int FINAL_COUNT_2 = 55;
    private static int FINAL_COUNT_3 = 100;

    private CountDownTimer timer;
    private Poll poll;
    private long timeRemaining;
    private MutableLiveData<String> timerText;

    public PollViewModel() {
        this.timerText = new MutableLiveData<>();
        this.timerText.setValue("Waiting on poll...");
    }

    public void stopPoll() {
        timer.onFinish();
    }

    public int getItemHeight(int maxHeight, int votes) {
        if (poll.mostVotes == 0) {
            return 0;
        }

        int height = (int) (maxHeight * (votes / (double) poll.mostVotes));
        return height == 0 ? 1 : height;
    }

    public ArrayList<Poll.PollItem> getPollItems() {
        return this.poll.getPollItems();
    }

    public LiveData<String> getTimerText() {
        return this.timerText;
    }

    public String getTopic() {
        return this.poll.getTopic();
    }

    public void initializePoll(String topic, Resources res) {
        this.poll = new Poll(topic);
        this.poll.addPollItem("Item 1", R.color.colorAccent);
        this.poll.addPollItem("Item 2", R.color.colorPrimary);
        this.poll.addPollItem("Item 3", R.color.design_default_color_primary);
        this.poll.addPollItem("Item 4", R.color.colorPrimaryDark);
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

    public void startPoll(Date endTime) throws Exception {
        if (timer != null) {
            throw new Exception("Must stop timer before starting a new one");
        }

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

        Log.d("Votes", "Left: " + updatesLeft);

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
            int total = (int) ((poll.mostVotes + 10) * (finalCount / 100.00));
            change = total - this.poll.getVoteCount(position);
        } else {
            change = finalCount / updatesLeft;
        }

        Log.d("Votes", "Change: " + change);

        this.poll.updateVotes(position, change);
    }

    private void closePoll() {
        this.timerText.setValue("Poll is closed.");
        timer = null;
    }
}