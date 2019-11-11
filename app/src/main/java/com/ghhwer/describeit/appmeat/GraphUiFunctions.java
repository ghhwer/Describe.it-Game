package com.ghhwer.describeit.appmeat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimatedImageDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ghhwer.describeit.R;
import com.ghhwer.describeit.graphUI.GraphUI;
import com.ghhwer.describeit.mentors.ExploreGraphApiMentor;
import com.ghhwer.describeit.mentors.GameGraphApiMentor;
import com.ghhwer.describeit.mentors.GraphApiMentor;

import static com.ghhwer.describeit.utils.utils.crossFadeIntent;
import static com.ghhwer.describeit.utils.utils.hideSoftKeyboard;
import static com.ghhwer.describeit.utils.utils.switchImageBtnOnState;

public class GraphUiFunctions {

    // Sets Up Loading Animations
    protected static void setupLoadingAnimation(ImageView loadingImage, TextView loadingText){
        // If Android Supports, make Loading GIF
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            AnimatedImageDrawable anim = (AnimatedImageDrawable) loadingImage.getDrawable();;
            anim.start();

        }
        else{ // Else just make it transparent
            loadingImage.setImageResource(R.drawable.transparent);
        }
        // Defaults to not visible
        loadingImage.setVisibility(View.INVISIBLE);
        loadingText.setVisibility(View.INVISIBLE);
    }
    // Sets Up All Buttons
    protected static void setupButtons(ImageButton undoBtn,
                                     ImageButton redoBtn,
                                     ImageButton expandBtn,
                                     ImageView gotoSettings,
                                     ImageView goBack,
                                     final Intent settingsIntent,
                                     final Intent mainIntent,
                                     final AppCompatActivity appCompatActivity,
                                     final GraphApiMentor exploreGraphApiMentor,
                                     final GraphUI graphUI) {
        // Settings Button
        gotoSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crossFadeIntent(appCompatActivity, settingsIntent);
            }
        });

        // Go Back
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crossFadeIntent(appCompatActivity, mainIntent);
            }
        });

        // Expand Terms
        expandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exploreGraphApiMentor.expandOnTerm(graphUI.getSelectedNode());
            }
        });
        // Undo
        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exploreGraphApiMentor.undo();
            }
        });
        // Redo
        redoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exploreGraphApiMentor.redo();
            }
        });
    }
    // Sets Up Search Box
    protected static void setupSearchTextbox(final EditText searchText,
                                           final GraphApiMentor exploreGraphApiMentor){
        // Search Action Press Enter to Search
        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                        keyCode == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                ) {
                    if (!event.isShiftPressed()) {
                        searchText.clearFocus();
                        hideSoftKeyboard(searchText.getRootView());
                        exploreGraphApiMentor.doSearch(searchText.getText().toString());
                    }
                }
                return false;
            }}
        );
    }
    // Sets Up Explore GraphAPI
    protected static ExploreGraphApiMentor createExploreApiMentor(final ImageButton undoBtn,
                                                                final ImageButton redoBtn,
                                                                final ImageButton expandBtn,
                                                                final ImageView loading,
                                                                final TextView loadingText,
                                                                GraphUI graphUI){
        return new ExploreGraphApiMentor(graphUI) {
            @Override
            public void allowanceStateChange(boolean allowUndo, boolean allowRedo, boolean graphIsLoaded) {
                switchImageBtnOnState(
                        allowUndo,
                        R.drawable.ic_action_undo,
                        R.drawable.ic_action_undo_disable,
                        undoBtn);
                switchImageBtnOnState(
                        allowRedo,
                        R.drawable.ic_action_redo,
                        R.drawable.ic_action_redo_disable,
                        redoBtn);
                switchImageBtnOnState(
                        graphIsLoaded,
                        R.drawable.ic_action_expand,
                        R.drawable.ic_action_expand_disable,
                        expandBtn);
            }

            @Override
            public void onStartLoad() {
                loading.setVisibility(View.VISIBLE);
                loadingText.setVisibility(View.VISIBLE);
                loadingText.setText("Loading..");
                loadingText.setTextColor(Color.BLACK);
            }

            @Override
            public void onEndLoad(boolean error) {
                loading.setVisibility(View.INVISIBLE);
                if (error)
                {
                    loadingText.setText("Error on API Request.. Check Internet Connection");
                    loadingText.setTextColor(Color.RED);
                }
                else
                    loadingText.setVisibility(View.INVISIBLE);
            }
        };

    }
    protected static GameGraphApiMentor createGameApiMentor(final ImageButton undoBtn,
                                                            final ImageButton redoBtn,
                                                            final ImageButton expandBtn,
                                                            final TextView gameTarget,
                                                            final ImageView loading,
                                                            final TextView loadingText,
                                                            GraphUI graphUI){
        return new GameGraphApiMentor(graphUI) {
            @Override
            public void allowanceStateChange(boolean allowUndo, boolean allowRedo, boolean graphIsLoaded) {
                switchImageBtnOnState(
                        allowUndo,
                        R.drawable.ic_action_undo,
                        R.drawable.ic_action_undo_disable,
                        undoBtn);
                switchImageBtnOnState(
                        allowRedo,
                        R.drawable.ic_action_redo,
                        R.drawable.ic_action_redo_disable,
                        redoBtn);
                switchImageBtnOnState(
                        graphIsLoaded,
                        R.drawable.ic_action_expand,
                        R.drawable.ic_action_expand_disable,
                        expandBtn);
            }

            @Override
            public void onStartLoad() {
                loading.setVisibility(View.VISIBLE);
                loadingText.setVisibility(View.VISIBLE);
                loadingText.setText("Loading..");
                loadingText.setTextColor(Color.BLACK);
            }

            @Override
            public void signalWin(int score) {
                Toast.makeText(gameTarget.getContext(),"You've Won! Moves:"+score, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onEndLoad(boolean error) {
                loading.setVisibility(View.INVISIBLE);
                if (error)
                {
                    loadingText.setText("Error on API Request.. Check Internet Connection");
                    loadingText.setTextColor(Color.RED);
                }
                else{
                    loadingText.setVisibility(View.INVISIBLE);
                    gameTarget.setVisibility(View.VISIBLE);
                    gameTarget.setText("Find "+getTargetWord());
                }
            }
        };
    }
}
