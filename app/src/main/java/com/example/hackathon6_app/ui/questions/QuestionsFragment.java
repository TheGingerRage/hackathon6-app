package com.example.hackathon6_app.ui.questions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hackathon6_app.R;

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
        }

        for (QuestionsViewModel.Question question : questionsViewModel.questions.getValue()) {
            addQuestionView(questionsContainer, question);
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

        TextView questionView = newQuestionView.findViewById(R.id.text_question);
        questionView.setText(question.text);

        Button upvote = newQuestionView.findViewById(R.id.button_upvote);
        upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.upvote();
            }
        });

        Button downvote = newQuestionView.findViewById(R.id.button_downvote);
        downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.downvote();
            }
        });

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

        questionsContainer.addView(newQuestionView);
    }

    private void addDummyQuestions() {
        this.questionsViewModel.addQuestion("What is the point of this?");
        this.questionsViewModel.addQuestion("Will two questions work?");
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
}