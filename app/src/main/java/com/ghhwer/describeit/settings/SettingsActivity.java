package com.ghhwer.describeit.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ghhwer.describeit.MainActivity;
import com.ghhwer.describeit.R;
import com.ghhwer.describeit.appmeat.GraphActivity;

import static com.ghhwer.describeit.CrossAppVariables.USER_IN_MAIN;
import static com.ghhwer.describeit.utils.utils.crossFadeIntent;

public class SettingsActivity extends AppCompatActivity {

    DescribeItSettings settings;
    Switch synonymsSwitch;
    Switch nounsSwitch;
    Switch adjectivesSwitch;
    Switch verbsSwitch;
    EditText numberOfRandom;
    Button applySettings;
    Button resetSettings;
    ImageButton goBack;

    // On Create Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final Intent goBackGraphIntent = new Intent(this, GraphActivity.class);
        final Intent goBackMainIntent = new Intent(this, MainActivity.class);

        // Settings View Variables
        synonymsSwitch = findViewById(R.id.synsSwitch);
        nounsSwitch = findViewById(R.id.nounsSwitch);
        adjectivesSwitch = findViewById(R.id.adjectivesSwitch);
        verbsSwitch = findViewById(R.id.verbsSwitch);
        numberOfRandom = findViewById(R.id.randomWordsNum);
        applySettings = findViewById(R.id.applyBtn);
        resetSettings = findViewById(R.id.defaultBtn);
        goBack = findViewById(R.id.goBackFromSettings);
        final AppCompatActivity appCompatActivity = this;

       goBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (USER_IN_MAIN)
                crossFadeIntent(appCompatActivity, goBackMainIntent);
               else
                crossFadeIntent(appCompatActivity, goBackGraphIntent);
           }
       });
       applySettings.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int contador = 0;
               if (synonymsSwitch.isChecked())
                   contador += 1;
               if (nounsSwitch.isChecked())
                   contador += 1;
               if (adjectivesSwitch.isChecked())
                   contador += 1;
               if (verbsSwitch.isChecked())
                   contador += 1;
               int numeroSelecionado = Integer.valueOf(numberOfRandom.getText().toString());
               if( contador >0 && numeroSelecionado > 1){
                   settings.setSyns(synonymsSwitch.isChecked());
                   settings.setNouns(nounsSwitch.isChecked());
                   settings.setAdjective(adjectivesSwitch.isChecked());
                   settings.setVerbs(verbsSwitch.isChecked());
                   settings.getRandomwordsnum(numeroSelecionado);
                   settings.commitSettings();
                   Toast.makeText(applySettings.getContext(), "Settings Saved!", Toast.LENGTH_LONG).show();
               }
               else{
                   Toast.makeText(applySettings.getContext(), "Invalid Settings!", Toast.LENGTH_LONG).show();
               }
           }
       });
    }

    @Override
    protected void onStart() {
        super.onStart();
        settings = new DescribeItSettings(this.getBaseContext());
        synonymsSwitch.setChecked(settings.isSyns());
        nounsSwitch.setChecked(settings.isNouns());
        adjectivesSwitch.setChecked(settings.isAdjective());
        verbsSwitch.setChecked(settings.isVerbs());
        numberOfRandom.setText(""+settings.getRandomwordsnum());
    }

}
