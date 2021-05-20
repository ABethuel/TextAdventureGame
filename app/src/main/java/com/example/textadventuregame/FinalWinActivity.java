package com.example.textadventuregame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FinalWinActivity extends AppCompatActivity {

    Button backToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_win);

        setupControls();
    }

    private void setupControls() {

        // Music
        MediaPlayer ring = MediaPlayer.create(FinalWinActivity.this, R.raw.victory);
        ring.setLooping(true);
        ring.start();

        backToMenu = findViewById(R.id.buttonBAckToMenuW);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                ring.stop();
            }
        });
    }
}