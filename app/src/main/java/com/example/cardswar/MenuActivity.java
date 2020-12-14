package com.example.cardswar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button menu_BTN_newGame;
    private Button menu_BTN_highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideStatusBar(this);
        setContentView(R.layout.activity_menu);

        findViews();
        initViews();
    }

    private void initViews() {
        menu_BTN_highScore.setOnClickListener(v -> {
            Intent myIntent = new Intent(MenuActivity.this, ShowHighScores.class);
            // myIntent.putExtra("winner", gameManager.gameWinner()); //Optional parameters
            MenuActivity.this.startActivity(myIntent);
        });
        menu_BTN_newGame.setOnClickListener(v -> {
            Intent myIntent = new Intent(MenuActivity.this, GameActivity.class);
           // myIntent.putExtra("winner", gameManager.gameWinner()); //Optional parameters
            MenuActivity.this.startActivity(myIntent);

        });
    }

    private void findViews() {
        menu_BTN_highScore = findViewById(R.id.menu_BTN_highScore);
        menu_BTN_newGame = findViewById(R.id.menu_BTN_newGame);
    }
}