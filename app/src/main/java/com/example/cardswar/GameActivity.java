package com.example.cardswar;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static com.example.cardswar.Game.P1;
import static com.example.cardswar.Game.P2;


public class GameActivity extends AppCompatActivity {

    private static final int TIMER_INTERVAL = 300;
    private ImageView game_IMG_card1;
    private ImageView game_IMG_card2;
    private Button game_BTN_nextCard;
    private TextView txtPlayer1Score;
    private TextView txtPlayer2Score;
    private ImageView game_IMG_avatarB;
    private ImageView game_IMG_avatarA;
    private Switch game_SWT_auto;
    private ProgressBar game_PRG_gameProgress;
    private TextView game_TXT_popup;
    private MediaPlayer mp;
    private Game gameManager;

    private final Timer timer = new Timer();
    private boolean timerOn = false;

    private BroadcastReceiver broadcastReceiver;
    private double latitude;
    private double longitude;
    private boolean isUp;
    private View myView;
    private Vibrator vibrator;


    private void startNewGame() {

        gameManager = new Game();
        this.game_IMG_card2.setImageResource(R.drawable.img_card_blank);
        this.game_IMG_card1.setImageResource(R.drawable.img_card_blank);
        game_BTN_nextCard.setEnabled(true);
        game_BTN_nextCard.setText(R.string.play);
        updateScore();
    }

    public void nextTurn() {
        if (isUp)
            slideDown(myView);
        Card[] cards = gameManager.playTurn();
        this.game_IMG_card1.setImageResource(this.getResources().getIdentifier("drawable/" + cards[P1].getCardFileName(), null, this.getPackageName()));
        this.game_IMG_card2.setImageResource(this.getResources().getIdentifier("drawable/" + cards[P2].getCardFileName(), null, this.getPackageName()));
        updateScore();
        if (this.gameManager.isFinished()) {
            game_BTN_nextCard.setEnabled(false);
            game_BTN_nextCard.setText(R.string.game_finished);
            showWinner();

        }
    }

    private void showWinner() {
        int winnerIndex = gameManager.gameWinner();

        Intent myIntent = new Intent(GameActivity.this, WinnerActivity.class);
        if (winnerIndex != Game.TIE)
            myIntent.putExtra("score", gameManager.getScores()[winnerIndex]);
        myIntent.putExtra("winner", gameManager.gameWinner());
        myIntent.putExtra("latitude", latitude);
        myIntent.putExtra("longitude", longitude);
        timerOn = false;
        GameActivity.this.startActivity(myIntent);
        initViews();
        startNewGame();
    }

    private void updateScore() {
        if (gameManager.getRow() >= Game.MIN_ROW_BONUS) {
            game_TXT_popup.setText(gameManager.getRow() + " In a row!");
            slideUp(myView);
            playBlip();
            vibrate();

        }
        this.game_PRG_gameProgress.setProgress(this.gameManager.getProgress());
        this.txtPlayer1Score.setText("" + gameManager.getScores()[P1]);
        this.txtPlayer2Score.setText("" + gameManager.getScores()[P2]);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideStatusBar(this);
        findViews();
        initViews();
        initTimer();
        startGpsService();
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    latitude = (double) intent.getExtras().get("latitude");
                    longitude = (double) intent.getExtras().get("longitude");
                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
        startNewGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timerOn = game_SWT_auto.isChecked();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timerOn = false;
    }

    private void initViews() {
        this.setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
        this.game_BTN_nextCard.setOnClickListener(v -> nextTurn());
        this.game_IMG_avatarA.setImageResource(R.drawable.img_avatar_1);
        this.game_IMG_avatarB.setImageResource(R.drawable.ic_android_foreground);
        game_SWT_auto.setOnCheckedChangeListener((buttonView, isChecked) -> timerOn = isChecked);
        game_SWT_auto.setChecked(false);

        myView.setVisibility(View.INVISIBLE);
        isUp = false;
        this.game_PRG_gameProgress.setMax(Deck.DECK_CAPACITY);
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void playBlip() {
        try {
            mp = new MediaPlayer();
            mp.setDataSource(this, Uri.parse("https://actions.google.com/sounds/v1/cartoon/wood_plank_flicks.ogg"));
            mp.setVolume(0.3f, 0.3f);
            mp.prepare();
            if (mp != null && !timerOn) { // Dont play sounds when on AUTOGAME mode
                mp.start();
            }
        } catch (Exception e) {
        }
    }

    private void initTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (timerOn)
                        nextTurn();
                });

            }
        }, 2000, TIMER_INTERVAL);
    }

    private void findViews() {
        this.game_IMG_card1 = findViewById(R.id.game_IMG_card1);
        this.game_IMG_card2 = findViewById(R.id.game_IMG_card2);
        this.game_BTN_nextCard = findViewById(R.id.game_BTN_nextCard);
        this.txtPlayer1Score = findViewById((R.id.game_LBL_p1_score));
        this.txtPlayer2Score = findViewById((R.id.game_LBL_p2_score));
        this.game_IMG_avatarB = findViewById(R.id.game_IMG_avatarB);
        this.game_IMG_avatarA = findViewById(R.id.game_IMG_avatarA);
        this.game_SWT_auto = findViewById(R.id.game_SWT_auto);
        this.game_PRG_gameProgress = findViewById(R.id.game_PRG_gameProgress);
        this.myView = findViewById(R.id.my_view);
        this.game_TXT_popup = findViewById(R.id.game_TXT_popup);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    private boolean runtime_permissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);

            return true;
        }
        return false;
    }

    private void startGpsService() {
        if (!runtime_permissions()) {
            Intent i = new Intent(getApplicationContext(), Gps_Service.class);
            startService(i);
        }
    }

    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        isUp = true;
    }

    private void vibrate() {
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(200);
        }
    }

    // slide the view from its current position to below itself
    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        isUp = false;
    }

}
