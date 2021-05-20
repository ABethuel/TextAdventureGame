  package com.example.textadventuregame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FinalDeathActivity extends AppCompatActivity {

    Button backToMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_death);

        setupControls();
    }

    private void setupControls() {
        // Music
        MediaPlayer ring = MediaPlayer.create(FinalDeathActivity.this, R.raw.death);
        ring.setLooping(true); // We loop the music
        ring.start();

        backToMenu = findViewById(R.id.buttonBackToMenu);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }
}