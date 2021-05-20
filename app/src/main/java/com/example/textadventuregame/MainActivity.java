package com.example.textadventuregame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button helpButton;
    Button aboutButton;
    Button exitButton;
    Button playButton;
    Button loadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupControls();
    }

    private void setupControls() {

        // Music
        MediaPlayer ring = MediaPlayer.create(MainActivity.this, R.raw.ashesonthefire);
        ring.setLooping(true); // We loop the music in all activities
        ring.start();


        // Navigation
        helpButton = findViewById(R.id.buttonHelp);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HelpActivity.class);
                startActivity(intent);
            }
        });

        aboutButton = findViewById(R.id.buttonAbout);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AboutActivity.class);
                startActivity(intent);
            }
        });

        playButton = findViewById(R.id.buttonPlay);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), StartActivity.class);
                startActivity(intent);

                ring.stop();

                /*Intent intent = new Intent(getBaseContext(), FightActivity.class);
                startActivity(intent);*/
            }
        });

        loadButton = findViewById(R.id.buttonLoad);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PlayActivity.class);
                SharedPreferences sharPrefs = getSharedPreferences(PlayActivity.SAVEFILE, MODE_PRIVATE);

                int playerpos = sharPrefs.getInt(PlayActivity.PLAYERPOS, 0);

                intent.putExtra(PlayActivity.KEY, Integer.toString(playerpos));
                startActivity(intent);
            }
        });

        exitButton = findViewById(R.id.buttonExit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}