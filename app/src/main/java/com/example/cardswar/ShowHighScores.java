package com.example.cardswar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ShowHighScores extends AppCompatActivity {

    private ListView scores_LST_scores;


    private List<Highscore> highscores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideStatusBar(this);
        setContentView(R.layout.activity_show_high_scores);
        findViews();
        initViews();

    }

    private void findViews() {
        scores_LST_scores = findViewById(R.id.scores_LST_scores);
    }

    private void initViews() {
        this.highscores = Utils.getDataFromSharedPreferences(this);
        List<String> names = new ArrayList<>();
        if (highscores != null) {
            int place = 1;
            for (Highscore h : highscores) {

                names.add(String.format("%-20d|%-40s|%-3d", place++, h.getName(), h.getScore()));
            }
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, names );
            scores_LST_scores.setAdapter(adapter);

        }
    }

    public List<Highscore> getHighscores() {
        return highscores;
    }
}
