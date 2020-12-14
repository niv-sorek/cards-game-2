package com.example.cardswar;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utils {
    private static final int NUM_OF_SCORES = 10;
    private static final String PREFS_TAG = "SP";
    private static final String SCORES_TAG = "scores";

    public static void hideStatusBar(AppCompatActivity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(activity.getSupportActionBar()).hide(); // hide the title bar
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        activity.setContentView(R.layout.activity_main);
    }

    public static void AddScores(Highscore score, Context ctxt) {
        List<Highscore> scores = getDataFromSharedPreferences(ctxt);
        if (scores == null)
            scores = new ArrayList<>();
        scores.add(score);
        scores.sort(Highscore::compareTo);
        // Remove extra scores
        if (scores.size() > NUM_OF_SCORES)
            for (int i = scores.size() - 1; i <= NUM_OF_SCORES; i--)
                scores.remove(i);
        saveSharedPreferences(scores, ctxt);
    }

    public static void saveSharedPreferences(List<Highscore> scores, Context ctxt) {
        Gson gson = new Gson();
        String jsonCurProduct = gson.toJson(scores);

        SharedPreferences sharedPref = ctxt.getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(SCORES_TAG, jsonCurProduct);
        editor.commit();
    }

    public static List<Highscore> getDataFromSharedPreferences(Context ctxt) {
        Gson gson = new Gson();
        List<Highscore> scoresFromSharedPreferences;
        SharedPreferences sharedPref = ctxt.getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(SCORES_TAG, "");

        Type type = new TypeToken<List<Highscore>>() {
        }.getType();
        scoresFromSharedPreferences = gson.fromJson(jsonPreferences, type);

        return scoresFromSharedPreferences;
    }
}

