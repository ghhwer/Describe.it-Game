package com.ghhwer.describeit.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.ghhwer.describeit.R;

import java.util.ArrayList;
import java.util.Random;

public class utils {
    // Data Structure Helpers
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();
        // Traverse through the first list
        for (T element : list) {
            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }
    public static <T> ArrayList<T> removeInArray(ArrayList<T> listFrom, ArrayList<T> listRemove){
        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>(listFrom);
        // Traverse through the first list
        for (T element : listRemove) {
            // If this element is not present in newList
            // then add it
            if (newList.contains(element)) {
                newList.remove(element);
            }
        }
        // return the new list
        return newList;
    }
    public static <T> ArrayList<T> pickSample(ArrayList<T> population, int nSamplesNeeded) {
        if(population.size() <= nSamplesNeeded)
            return population;
        ArrayList<T> ret = new ArrayList<T>();

        int nPicked = 0, i = 0, nLeft = population.size();
        Random r = new Random();

        while (nSamplesNeeded > 0) {
            int rand = r.nextInt(nLeft);
            if (rand < nSamplesNeeded) {
                ret.add(population.get(i));
                nSamplesNeeded--;
            }
            nLeft--;
            i++;
        }
        return ret;
    }
    public static <T> ArrayList<T> arrayListFromArray(T[] array){
        ArrayList<T> out = new ArrayList<>();
        for(T e:array){
            out.add(e);
        }
        return out;
    }

    // Android Helpers
    public static void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
    public static void switchImageBtnOnState(boolean state, int resOnTrue, int resOnFalse, ImageButton btn) {
        btn.setEnabled(state);
        if (state)
            btn.setImageResource(resOnTrue);
        else
            btn.setImageResource(resOnFalse);
    }
    public static void crossFadeIntent(AppCompatActivity app, Intent intent){
        app.startActivity(intent);
        app.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
