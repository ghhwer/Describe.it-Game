package com.ghhwer.describeit.appmeat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ghhwer.describeit.MainActivity;
import com.ghhwer.describeit.R;
import com.ghhwer.describeit.graphUI.GraphUI;
import com.ghhwer.describeit.mentors.GraphApiMentor;
import com.ghhwer.describeit.settings.DescribeItSettings;
import com.ghhwer.describeit.settings.SettingsActivity;

import jp.kai.forcelayout.Forcelayout;

import static com.ghhwer.describeit.CrossAppVariables.USER_IN_EXPLORE;
import static com.ghhwer.describeit.CrossAppVariables.USER_IN_MAIN;
import static com.ghhwer.describeit.appmeat.GraphUiFunctions.createExploreApiMentor;
import static com.ghhwer.describeit.appmeat.GraphUiFunctions.createGameApiMentor;
import static com.ghhwer.describeit.appmeat.GraphUiFunctions.setupButtons;
import static com.ghhwer.describeit.appmeat.GraphUiFunctions.setupLoadingAnimation;
import static com.ghhwer.describeit.appmeat.GraphUiFunctions.setupSearchTextbox;

public class GraphActivity extends AppCompatActivity {

    // UI Elements
    Forcelayout fLayout;
    EditText searchText;
    ImageButton undoBtn;
    ImageButton redoBtn;
    ImageButton expandBtn;
    TextView loadingText;
    TextView gameTarget;
    TextView gameWinScreen;
    TextView gameWinScore;
    ImageView gotoSettings;
    ImageView goBack;
    ImageView loadingImage;

    // Game Logic
    GraphUI graphUI = null;
    GraphApiMentor graphApiMentor;
    DescribeItSettings settings;

    // On Create Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        //Main View Variables
        searchText = findViewById(R.id.searchText);
        fLayout = findViewById(R.id.forcelayout);
        undoBtn = findViewById(R.id.undoBtn);
        redoBtn = findViewById(R.id.redoBtn);
        expandBtn = findViewById(R.id.expandBtn);
        loadingImage = findViewById(R.id.loading);
        loadingText = findViewById(R.id.loadingText);
        gotoSettings = findViewById(R.id.gotoSettings);
        goBack = findViewById(R.id.goBackFromGraph);
        gameTarget = findViewById(R.id.gameTargetWord);
        gameWinScreen = findViewById(R.id.winScreen);
        gameWinScore = findViewById(R.id.score);

        // Default Visibility
        gameTarget.setVisibility(View.INVISIBLE);
        gameWinScreen.setVisibility(View.INVISIBLE);
        gameWinScore.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Intents
        final Intent settingsIntent = new Intent(this, SettingsActivity.class);
        final Intent mainIntent = new Intent(this, MainActivity.class);

        // Cross App Variables
        USER_IN_MAIN = false;
        settings = new DescribeItSettings(this.getBaseContext());
        final AppCompatActivity appCompatActivity = this;

        // Setup Graph-API Logic Modules Based on Enabled Mode
        graphUI = new GraphUI(fLayout);
        if (USER_IN_EXPLORE)
            graphApiMentor = createExploreApiMentor(
                    undoBtn,
                    redoBtn,
                    expandBtn,
                    loadingImage,
                    loadingText,
                    graphUI
            );
        else
            graphApiMentor = createGameApiMentor(
                    undoBtn,
                    redoBtn,
                    expandBtn,
                    gameTarget,
                    loadingImage,
                    loadingText,
                    gameWinScreen,
                    gameWinScore,
                    graphUI
            );

        // Setup UI Element Behaviour
        setupLoadingAnimation(loadingImage, loadingText);
        setupButtons(
                undoBtn,
                redoBtn,
                expandBtn,
                gotoSettings,
                goBack,
                settingsIntent,
                mainIntent,
                appCompatActivity,
                graphApiMentor, graphUI);
        setupSearchTextbox(searchText, graphApiMentor);
    }


}
