package com.ghhwer.describeit.access;

import com.google.gson.annotations.SerializedName;

public class DatamuseWordMap {
    @SerializedName("word")
    private String word;
    @SerializedName("score")
    private int score;
    @SerializedName("tags")
    private String[] tags;
    public DatamuseWordMap(String word, int score, String[] tags){
        this.word = word;
        this.score = score;
        this.tags = tags;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
