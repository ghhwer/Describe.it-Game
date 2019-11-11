package com.ghhwer.describeit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ghhwer.describeit.appmeat.GraphActivity;
import com.ghhwer.describeit.settings.SettingsActivity;

import static com.ghhwer.describeit.CrossAppVariables.USER_IN_EXPLORE;
import static com.ghhwer.describeit.CrossAppVariables.USER_IN_MAIN;
import static com.ghhwer.describeit.utils.utils.crossFadeIntent;

public class MainActivity extends AppCompatActivity {

    // On Create Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView settingsBtn = findViewById(R.id.gotoSettingsFromMain);
        Button gameBtn = findViewById(R.id.gameBtn);
        Button exploreBtn = findViewById(R.id.exploreBtn);
        final Intent settingsIntent = new Intent(this, SettingsActivity.class);
        final Intent graphIntent = new Intent(this, GraphActivity.class);
        final AppCompatActivity app = this;
        gameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USER_IN_EXPLORE = false;
                crossFadeIntent(app, graphIntent);
            }
        });

        exploreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USER_IN_EXPLORE = true;
                crossFadeIntent(app, graphIntent);
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crossFadeIntent(app, settingsIntent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        USER_IN_MAIN = true;
    }
}
