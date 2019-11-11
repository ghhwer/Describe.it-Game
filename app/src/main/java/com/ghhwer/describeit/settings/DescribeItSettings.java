package com.ghhwer.describeit.settings;

import android.content.Context;
import android.content.SharedPreferences;

import static com.ghhwer.describeit.CrossAppVariables.NUMBER_RANDOM_RETRIEVAL;
import static com.ghhwer.describeit.CrossAppVariables.SHARED_PREFERENCE_NAME;
import static com.ghhwer.describeit.CrossAppVariables.SHOULD_GET_ADJECTIVE;
import static com.ghhwer.describeit.CrossAppVariables.SHOULD_GET_NOUNS;
import static com.ghhwer.describeit.CrossAppVariables.SHOULD_GET_SYMNS;
import static com.ghhwer.describeit.CrossAppVariables.SHOULD_GET_VERBS;

public class DescribeItSettings {
    private boolean syns = true;
    private boolean nouns = true;
    private boolean adjective = true;
    private boolean verbs = true;
    private int randomwordsnum = 10;
    private SharedPreferences preferences;

    public DescribeItSettings(Context ctx){
        preferences = ctx.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_MULTI_PROCESS);
        getSettings();
        refreshGlobals();
    }

    public void refreshGlobals(){
        SHOULD_GET_SYMNS = syns;
        SHOULD_GET_NOUNS = nouns;
        SHOULD_GET_ADJECTIVE = adjective;
        SHOULD_GET_VERBS = verbs;
        NUMBER_RANDOM_RETRIEVAL = randomwordsnum;
    }

    public void commitSettings(){
        SharedPreferences.Editor edit= preferences.edit();
        edit.putBoolean("syns", syns);
        edit.putBoolean("nouns", nouns);
        edit.putBoolean("adjective", adjective);
        edit.putBoolean("verbs", verbs);
        edit.putInt("randomwordsnum", randomwordsnum);
        edit.commit();
        refreshGlobals();
    }

    public DescribeItSettings getSettings(){
        syns = preferences.getBoolean("syns", true);
        nouns = preferences.getBoolean("nouns", true);
        adjective = preferences.getBoolean("adjective", true);
        verbs = preferences.getBoolean("verbs", true);
        randomwordsnum = preferences.getInt("randomwordsnum", 3);
        return this;
    }

    public boolean isSyns() {
        return syns;
    }

    public void setSyns(boolean syns) {
        this.syns = syns;
    }

    public boolean isNouns() {
        return nouns;
    }

    public void setNouns(boolean nouns) {
        this.nouns = nouns;
    }

    public boolean isAdjective() {
        return adjective;
    }

    public void setAdjective(boolean adjective) {
        this.adjective = adjective;
    }

    public boolean isVerbs() {
        return verbs;
    }

    public void setVerbs(boolean verbs) {
        this.verbs = verbs;
    }

    public int getRandomwordsnum() {
        return randomwordsnum;
    }
    public void getRandomwordsnum(int num) { this.randomwordsnum = num; }
}
