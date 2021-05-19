package com.example.textadventuregame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class FightActivity extends AppCompatActivity {

    private int rollPlayer;
    private int rollEnemy;

    private int lifeEnemy;
    private int lifePlayer;

    //Items
    TextView txtLifeEnemy;
    TextView txtLifePlayer;
    TextView txtShowWinner;
    Button swordButton, shieldButton, heavyButton;
    ImageView playerAttackImage;
    ImageView enemyAttackImage;

    enum Choice {LIGHT, SHIELD, HARD, NONE};
    Choice playerChoice = Choice.NONE;
    Choice enemyChoice = Choice.NONE;

    Random random;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        setupControls();

        lifeEnemy = 3;
        lifePlayer = 3;
        txtLifeEnemy.setText(String.valueOf(lifeEnemy));
        txtLifePlayer.setText(String.valueOf(lifePlayer));
    }

    private void setupControls() {
        txtLifeEnemy = findViewById(R.id.txtLifeEnnemy);
        txtLifePlayer = findViewById(R.id.txtLifePlayer);

        swordButton = findViewById(R.id.buttonLightAttack);
        heavyButton = findViewById(R.id.buttonHeavyAttack);
        shieldButton = findViewById(R.id.buttonShield);

        enemyAttackImage = findViewById(R.id.imageAttackEnnemy);
        playerAttackImage = findViewById(R.id.imageAttackUser);

        swordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerChoice = Choice.LIGHT;
                playerAttackImage.setImageResource(R.drawable.sworduser);
            }
        });

    }
}