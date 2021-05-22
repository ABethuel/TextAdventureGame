package com.example.textadventuregame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeathKnightActivity extends AppCompatActivity {

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death_knight);

        setupControls();
    }

    private void setupControls() {
        // Music
        MediaPlayer ring = MediaPlayer.create(DeathKnightActivity.this, R.raw.death);
        ring.setLooping(true); // We loop the music
        ring.start();

        backButton = findViewById(R.id.BackMainMenuButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                ring.stop();
            }
        });
    }
}