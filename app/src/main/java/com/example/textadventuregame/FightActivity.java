package com.example.textadventuregame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class FightActivity extends AppCompatActivity {

    private int rollPlayer = 0;
    private int rollEnnemy = 0;

    Player player;
    private int lifeEnnemy = 5;

    //Items
    TextView txtLifeEnnemy;
    TextView textLifePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        txtLifeEnnemy.setText(lifeEnnemy);
    }
}