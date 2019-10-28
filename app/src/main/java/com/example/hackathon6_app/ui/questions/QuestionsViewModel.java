package com.example.hackathon6_app.ui.questions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class QuestionsViewModel extends ViewModel {

    public MutableLiveData<ArrayList<Question>> questions;

    public QuestionsViewModel() {
        questions = new MutableLiveData<>();
        questions.setValue(new ArrayList<Question>());
    }

    public void addQuestion(String who ,String what) {
        ArrayList<Question> curr = questions.getValue();
        curr.add(new Question(who, what));
        questions.setValue(curr);
    }

    public void markAddressed(int position) {
        questions.getValue().get(position).setAddressed();
    }

    public static class Question {
        public String who, what;
        private MutableLiveData<Boolean> addressed;
        private MutableLiveData<Integer> up, down;

        public Question(String who, String what) {
            this.who = who;
            this.what = what;
            this.addressed = new MutableLiveData<>();
            this.addressed.setValue(false);
            this.up = new MutableLiveData<>();
            this.up.setValue(0);
            this.down = new MutableLiveData<>();
            this.down.setValue(0);
        }

        public void downvote() {
            this.down.setValue(this.down.getValue() - 1);
        }

        public LiveData<Integer> getUpvotes() {
            return this.up;
        }

        public LiveData<Integer> getDownvotes() {
            return this.down;
        }

        public void setAddressed() {
            this.addressed.setValue(true);
        }

        public void upvote() {
            this.up.setValue(this.up.getValue() + 1);
        }
    }
}