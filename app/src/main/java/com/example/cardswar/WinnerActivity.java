package com.example.cardswar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.cardswar.Game.P1;
import static com.example.cardswar.Game.P2;

public class WinnerActivity extends AppCompatActivity {
    private TextView winner_LBL_name;
    private ImageView winner_IMG_winner;
    private int score;
    private Button winner_BTN_newGame;
    private String winnerName;
    private int winner;
    private double latitude;
    private double longitude;
    private Button winner_BTN_mainMenu;
    private MediaPlayer mp;

    @Override
    protected void onPause() {
        super.onPause();
        if(this.mp!= null )this.mp.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.hideStatusBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        getExtras();

        findViews();
        initViews();


        if (winner == P1) {
            new Handler().postDelayed(() -> showInputDialog(), 3000);


        }
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your Name");

        // Set up the input

        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            winnerName = input.getText().toString();
            Utils.AddScores(new Highscore(winnerName, score, latitude, longitude), getBaseContext());
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void getExtras() {
        Intent intent = getIntent();
        winner = intent.getIntExtra("winner", -1);
        this.score = intent.getIntExtra("score", -1);
        latitude = intent.getDoubleExtra("latitude", 0.0);
        longitude = intent.getDoubleExtra("longitude", 0.0);
    }

    private void initViews() {
        mp= new MediaPlayer();
        if (this.winner == P1) {
            this.winner_LBL_name.setText("Winner!");
            this.winner_IMG_winner.setImageResource(R.drawable.img_winner);
            try {
                mp.setDataSource(this, Uri.parse("https://actions.google.com/sounds/v1/crowds/battle_crowd_celebrate_stutter.ogg"));
                mp.prepare();
                mp.start();
            } catch (Exception e) {
            }
        } else if (this.winner == P2) {
            this.winner_LBL_name.setText("Game Over.");
            this.winner_IMG_winner.setImageResource(R.drawable.ic_sadface_foreground);

        } else {
            this.winner_LBL_name.setText(R.string.tie_message);
            this.winner_IMG_winner.setImageResource(R.drawable.ic_sadface_foreground);
        }

        this.winner_BTN_newGame.setOnClickListener(v -> finish());
        this.winner_BTN_mainMenu.setOnClickListener(v -> {
            Intent intent = new Intent(this, MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
    }

    private void findViews() {
        winner_LBL_name = findViewById(R.id.winner_lbl_winnerName);
        this.winner_BTN_newGame = findViewById(R.id.winner_BTN_newGame);
        winner_IMG_winner = findViewById(R.id.winner_IMG_winner);
        this.winner_BTN_mainMenu = findViewById(R.id.winner_BTN_mainMenu);
    }
}