package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final int minutes = 10;
    TextView timerTextView;
    SeekBar timerSeekBar;
    CountDownTimer countDownTimer;
    boolean start = false;
    Button goButton;
    MediaPlayer mediaPlayer;

    public void timer(View view){
        //if start was true, then the user wants to stop the timer
        //if start was false, then the user wants to start the timer
        start = !start;

        //pressed start
        if(start){
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.siren);
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress()*1000 + 100, 1000){
                public void onTick(long millisecondsUntilDone){
                    //Log.i("Seconds Left: ", String.valueOf(millisecondsUntilDone/1000));
                    timerTextView.setText(timeToString((int)millisecondsUntilDone / 1000));
                }

                public void onFinish(){
                    //get context of the app
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();

            timerSeekBar.setEnabled(false);
            goButton.setText("STOP!");
        }
        //pressed stop
        else{
            resetTimer();
        }
    }

    public void resetTimer(){
        countDownTimer.cancel();
        timerSeekBar.setEnabled(true);
        goButton.setText("GO!");
        timerSeekBar.setProgress(30);
        timerTextView.setText(timeToString(30));
    }

    public String timeToString(int seconds){
        String result = "";
        int minutes = seconds/60;
        if(minutes < 10)result+="0";
        result+=minutes+":";

        int remaining = seconds - (minutes * 60);
        if(remaining < 10)result+="0";
        result+=remaining;
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView); //get id of displayed timer
        timerSeekBar = findViewById(R.id.timerSeekBar); //get id of seek bar
        goButton = findViewById(R.id.goButton); //get id of button

        //set defaults for seek bar
        timerSeekBar.setMax(minutes * 60); //10 minutes * 60 sec/min = 600 sec
        timerSeekBar.setProgress(30); //default of 30 sec
        timerTextView.setText(timeToString(timerSeekBar.getProgress()));


        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                timerTextView.setText(timeToString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
