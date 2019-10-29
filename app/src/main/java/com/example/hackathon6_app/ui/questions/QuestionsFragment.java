package com.example.hackathon6_app.ui.questions;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hackathon6_app.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuestionsFragment extends Fragment {

    private QuestionsViewModel questionsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        questionsViewModel =
                ViewModelProviders.of(this).get(QuestionsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_questions, container, false);
        final ViewGroup questionsContainer = root.findViewById(R.id.container_questions);

        if (questionsViewModel.questions.getValue().isEmpty()) {
            addDummyQuestions();
        } else {
            for (QuestionsViewModel.Question question : questionsViewModel.questions.getValue()) {
                addQuestionView(questionsContainer, question);
            }
        }

        questionsViewModel.questions.observe(this, new Observer<ArrayList<QuestionsViewModel.Question>>() {
            @Override
            public void onChanged(ArrayList<QuestionsViewModel.Question> questions) {
                if (questions.isEmpty() || questions.size() == questionsContainer.getChildCount()) {
                    return;
                }

                QuestionsViewModel.Question newQuestion = questions.get(questions.size() -1);
                addQuestionView(questionsContainer, newQuestion);
            }
        });

        return root;
    }

    public void addQuestionView(ViewGroup questionsContainer, final QuestionsViewModel.Question question) {
        View newQuestionView = getLayoutInflater().inflate(R.layout.template_question, null);

        TextView whoView = newQuestionView.findViewById(R.id.text_who);
        whoView.setText(question.who + " asks:");

        TextView whatView = newQuestionView.findViewById(R.id.text_what);
        whatView.setText(question.what);

        final TextView total = newQuestionView.findViewById(R.id.text_votes);

        question.getUpvotes().observe(QuestionsFragment.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer votes) {
                int previous = getPreviousVoteCount(total);
                total.setText(getTotalVoteString(previous + 1));
            }
        });

        question.getDownvotes().observe(QuestionsFragment.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer votes) {
                int previous = getPreviousVoteCount(total);
                total.setText(getTotalVoteString(previous - 1));
            }
        });

        ImageButton upvote = newQuestionView.findViewById(R.id.button_upvote);
        Drawable upDrawable = upvote.getDrawable();
        upDrawable = DrawableCompat.wrap(upDrawable);
        DrawableCompat.setTint(upDrawable, getResources().getColor(R.color.orange));

        upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.upvote();
            }
        });

        ImageButton downvote = newQuestionView.findViewById(R.id.button_downvote);
        Drawable downDrawable = downvote.getDrawable();
        downDrawable = DrawableCompat.wrap(downDrawable);
        DrawableCompat.setTint(downDrawable, getResources().getColor(R.color.purple));

        downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.downvote();
            }
        });

        questionsContainer.addView(newQuestionView);
    }

    private void addDummyQuestions() {
        this.questionsViewModel.addQuestion("Nick", "Do you know the muffin man?");

        DummyQuestion[] questions = new DummyQuestion[] {
            //new DummyQuestion("Nick", "Do you know the muffin man?"),
            new DummyQuestion("Bryan", "Why won't Nick stop talking about the muffin man?")
        };

        new DummyQuestionGenerator(this.questionsViewModel).execute(questions);

        //this.questionsViewModel.addQuestion("Bryan", "Why won't Nick stop talking about the muffin man?");
    }

    private static int getPreviousVoteCount(TextView total) {
        return Integer.valueOf(total.getText().toString().replaceAll("\\+", ""));
    }

    private static String getTotalVoteString(Integer totalCount) {
        String totalString = String.valueOf(totalCount);
        if (totalCount > 0) {
            totalString = "+" + totalString;
        }
        return totalString;
    }

    private static class DummyQuestionGenerator extends AsyncTask<DummyQuestion, DummyQuestion, Void> {
        QuestionsViewModel viewModel;

        public DummyQuestionGenerator(QuestionsViewModel viewModel) {
            this.viewModel = viewModel;
        }

        @Override
        protected void onProgressUpdate(DummyQuestion... questions) {
            viewModel.addQuestion(questions[0].who, questions[0].what);
        }

        @Override
        protected Void doInBackground(DummyQuestion... questions) {
            for (DummyQuestion question : questions) {
                try {
                    Thread.sleep(10000);
                    publishProgress(question);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            return null;
        }
    }

    private static class DummyQuestion {
        public String who;
        public String what;

        public DummyQuestion(String who, String what) {
            this.who = who;
            this.what = what;
        }
    }
}