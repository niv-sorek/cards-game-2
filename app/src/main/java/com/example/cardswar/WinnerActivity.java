package com.example.cardswar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.cardswar.Game.P1;
import static com.example.cardswar.Game.P2;

public class WinnerActivity extends AppCompatActivity {
    private TextView winner_LBL_name;
    private int winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        Intent intent = getIntent();
        this.winner = intent.getIntExtra("winner", -1);
        findViews();
        initViews();
    }


    private void initViews() {
        if(this.winner == P1)
            this.winner_LBL_name.setText(R.string.player_a_name);
        else if (this.winner == P2)
            this.winner_LBL_name.setText(R.string.player_b_name);
        else this.winner_LBL_name.setText(R.string.tie_message);
    }

    private void findViews() {
        winner_LBL_name = findViewById(R.id.winner_lbl_winnerName);
    }
}