package com.example.textadventuregame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class FightActivity extends AppCompatActivity {

    private int rollPlayer;
    private int rollEnnemy;

    Player player;
    private int lifeEnnemy;

    //Items
    TextView txtLifeEnnemy;
    TextView txtLifePlayer;
    TextView txtShowWinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        setupControls();

        lifeEnnemy = 3;
        txtLifeEnnemy.setText(String.valueOf(lifeEnnemy));
        txtLifePlayer.setText(String.valueOf(player.getLife()));
    }

    private void setupControls() {
        txtLifeEnnemy = findViewById(R.id.txtLifeEnnemy);
        txtLifePlayer = findViewById(R.id.txtLifePlayer);
    }
}