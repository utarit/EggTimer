package com.example.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text;
    SeekBar seekBar;
    Button button;
    Integer min = 25;
    Boolean isClockActive = false;
    CountDownTimer timer;

    public void resetTimer(){
        isClockActive = false;
        button.setText("GO !");
        seekBar.setEnabled(true);
        timer.cancel();
        text.setText(Integer.toString(min) + " : 00");
    }

    public String formattedNumber(int number){
        if(number < 10) {
            return "0" + Integer.toString(number);
        } else {
            return Integer.toString(number);
        }
    }

    public void onTimerClick(View view){

        if(isClockActive){
            resetTimer();
        } else {

            isClockActive = true;
            button.setText("STOP!");
            seekBar.setEnabled(false);

            timer = new CountDownTimer(min*60*1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String temp = formattedNumber(((int) millisUntilFinished / 1000) / 60) + " : " + formattedNumber(((int) millisUntilFinished / 1000) % 60);
                    text.setText(temp);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);
        button = findViewById(R.id.button);

        seekBar.setMax(24);
        seekBar.setProgress(5);
        text.setText(formattedNumber(min) + " : 00");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress < 1){
                    progress = 1;
                    seekBar.setProgress(1);
                }

                min = progress*5;
                text.setText(formattedNumber(min) + " : 00");

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
