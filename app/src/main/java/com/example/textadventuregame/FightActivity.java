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

    enum Choice {LIGHT, SHIELD, HEAVY, NONE};
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
                playerAttackImage.setImageResource(R.drawable.sworduser); // We change the path of the image if clicked
                playerAttackImage.setVisibility(View.VISIBLE); // We define that the image is visible

                enemyChoice();
            }
        });

        heavyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerChoice = Choice.HEAVY;
                playerAttackImage.setImageResource(R.drawable.heavysword); // We change the path of the image if clicked
                playerAttackImage.setVisibility(View.VISIBLE); // We define that the image is visible

                enemyChoice();
            }
        });

        shieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerChoice = Choice.SHIELD;
                playerAttackImage.setImageResource(R.drawable.shield); // We change the path of the image if clicked
                playerAttackImage.setVisibility(View.VISIBLE); // We define that the image is visible

                enemyChoice();
            }
        });
    } // private void setupControls()

    private void enemyChoice() {
        // We define the attack choice of the enemy
        int choice = 0;
        choice = generateRandomNumber();

        if (choice == 0){
            enemyChoice = Choice.LIGHT;
            enemyAttackImage.setImageResource(R.drawable.swordennemi);
            enemyAttackImage.setVisibility(View.VISIBLE);
        }
        else if (choice == 1){
            enemyChoice = Choice.SHIELD;
            enemyAttackImage.setImageResource(R.drawable.shield_ennemy);
            enemyAttackImage.setVisibility(View.VISIBLE);
        }
        else if (choice == 2){
            enemyChoice = Choice.HEAVY;
            enemyAttackImage.setImageResource(R.drawable.heavyswordennemy);
        }
        checkWin();
    } // private void EnemyChoice()

    private int generateRandomNumber() {
        // We define a random number
        random = new Random();
        return random.nextInt(3);
    } // private int enemyChoice()

    private void checkWin(){

    } // private void checkWin()

}