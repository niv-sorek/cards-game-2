package com.example.cardswar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.cardswar.Game.P1;
import static com.example.cardswar.Game.P2;


public class MainActivity extends AppCompatActivity {

    private ImageView main_IMG_card1;
    private ImageView main_IMG_card2;
    private Button main_BTN_nextCard;
    private TextView txtPlayer1Score;
    private TextView txtPlayer2Score;

    Game gameManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();


    }

    private void initViews() {
        gameManager = new Game();
        updateScore();
        main_BTN_nextCard.setOnClickListener(v -> nextTurn());
    }


    public void nextTurn() {
        if (!gameManager.isFinished()) {

            Card[] cards = gameManager.playTurn();

            this.main_IMG_card1.setImageResource(this.getResources().getIdentifier("drawable/" + cards[P1].getCardFileName(), null, this.getPackageName()));
            this.main_IMG_card2.setImageResource(this.getResources().getIdentifier("drawable/" + cards[P2].getCardFileName(), null, this.getPackageName()));


            updateScore();
        }
       else {
            main_BTN_nextCard.setEnabled(false);
            main_BTN_nextCard.setText(R.string.game_finished);
            showWinner();
        }
    }

    private void showWinner() {
        Intent myIntent = new Intent(MainActivity.this, WinnerActivity.class);
        myIntent.putExtra("winner", gameManager.gameWinner()); //Optional parameters
        MainActivity.this.startActivity(myIntent);
    }

    private void updateScore() {
        this.txtPlayer1Score.setText("" + gameManager.getScores()[P1]);
        this.txtPlayer2Score.setText("" + gameManager.getScores()[P2]);
    }

    private void findViews() {

        this.main_IMG_card1 = findViewById(R.id.main_IMG_card1);
        this.main_IMG_card2 = findViewById(R.id.main_IMG_card2);
        this.main_BTN_nextCard = findViewById(R.id.main_BTN_nextCard);
        this.txtPlayer1Score = findViewById((R.id.main_LBL_p1_score));
        this.txtPlayer2Score = findViewById((R.id.main_LBL_p2_score));
    }

}
