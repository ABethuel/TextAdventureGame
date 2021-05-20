package com.example.textadventuregame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityWin extends AppCompatActivity {

    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        setupControls();
    }

    private void setupControls() {
        MediaPlayer ring = MediaPlayer.create(ActivityWin.this, R.raw.victory);
        ring.setLooping(true); // We loop the music
        ring.start();

        nextButton = findViewById(R.id.buttonNextWin);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), FinalWinActivity.class);
                startActivity(intent);
                ring.stop();
            }
        });
    }


}