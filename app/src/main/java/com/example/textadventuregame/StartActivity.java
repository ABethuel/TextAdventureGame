package com.example.textadventuregame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    Button backButton;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setupControls();
    }

    private void setupControls() {

        // Music
        MediaPlayer ring = MediaPlayer.create(StartActivity.this, R.raw.ashesonthefire);
        ring.setLooping(true); // We loop the music in all activities
        ring.start();

        // Navigation
        nextButton = findViewById(R.id.buttonNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PlayActivity.class);
                intent.putExtra(PlayActivity.KEY, "0");
                startActivity(intent);

                ring.stop();
            }
        });

        backButton = findViewById(R.id.buttonStartBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}