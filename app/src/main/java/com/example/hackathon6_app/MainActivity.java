package com.example.hackathon6_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.example.hackathon6_app.bl.Profile;
import com.example.hackathon6_app.profile.ProfileCard;
import com.example.hackathon6_app.profile.ProfileTracker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private final ProfileTracker tracker = ProfileTracker.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_qr, R.id.nav_profile_creator, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_profile, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.e("Scan*******", "Cancelled scan");

            } else {
                Log.e("Scan", "Scanned: " + result.getContents());


                TextView tview = findViewById(R.id.nameTextView);
                tview.setText("hello, world!");

               ProfileCard profile = DeserializeProfile(result.getContents());

               if(profile != null){
                   ProcessNewConnection(profile);
               }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void ProcessNewConnection(ProfileCard newConnection){
        LinearLayout scanLayout = findViewById(R.id.scanResult);

        tracker.addConnection(newConnection);

        scanLayout.setVisibility(View.VISIBLE);
        TextView congratsText = findViewById(R.id.congratsText);
        TextView nameText = findViewById(R.id.nameTextView);
        TextView title = findViewById(R.id.titleLabelTextView);
        TextView titleText = findViewById(R.id.titleValueTextView);
        TextView company = findViewById(R.id.companyLabelTextView);
        TextView companyText = findViewById(R.id.companyValueTextView);

        congratsText.setVisibility(View.VISIBLE);

        title.setVisibility(View.VISIBLE);
        company.setVisibility(View.VISIBLE);

        titleText.setVisibility(View.VISIBLE);
        companyText.setVisibility(View.VISIBLE);

        nameText.setText(newConnection.firstName + " " + newConnection.lastName);
        titleText.setText(newConnection.title);
        companyText.setText(newConnection.company);


    }

    private ProfileCard DeserializeProfile(String jsonProfile){
        if(!jsonProfile.isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();

            try{
                ProfileCard profile = objectMapper.readValue(jsonProfile, ProfileCard.class);
                return profile;
            }
            catch(JsonProcessingException e){
                // do some error handling
            }
            catch(IOException e){
                // do some generic error handling
            }

        }

       ProfileCard p = new ProfileCard();
        p.firstName = "Something went ";
        p.lastName = "wrong.";

        return p;
    }
}
