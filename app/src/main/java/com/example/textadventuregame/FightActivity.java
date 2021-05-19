package com.example.textadventuregame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class FightActivity extends AppCompatActivity {

    private int rollPlayer;
    private int rollEnnemy;

    Player player;
    private int lifeEnnemy;

    //Items
    TextView txtLifeEnnemy;
    TextView textLifePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        lifeEnnemy = 5;
        txtLifeEnnemy.setText(String.valueOf(lifeEnnemy));
    }
}