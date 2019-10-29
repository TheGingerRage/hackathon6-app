package com.example.hackathon6_app.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hackathon6_app.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    private boolean legendSet = false;

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

        return root;
    }
}