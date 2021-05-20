package com.example.textadventuregame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityDeath extends AppCompatActivity {

    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death);
        setupControls();
    }

    private void setupControls() {

        // Music
        MediaPlayer ring = MediaPlayer.create(ActivityDeath.this, R.raw.death);
        ring.setLooping(true); // We loop the music
        ring.start();

        nextButton = findViewById(R.id.nextDeathButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getBaseContext(), FinalDeathActivity.class);
                startActivity(intent);
                ring.stop();
            }
        });
    }
}