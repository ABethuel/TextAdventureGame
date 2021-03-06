package com.example.textadventuregame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class PlayActivity extends AppCompatActivity {

    //PLaying and saving information
    static final String SAVEFILE = "savedgame";
    static final String PLAYERPOS = "playerpos";
    static final String PLAYERINVENTORY = "playerinventory";
    static final String ROOMINVENTORY = "roominventory";
    static final String KEY = "pos";

    static final int NUM_OF_ROOMS = 10; // Setup the number of rooms of the app
    Room [] thedungeon;
    Player player; //player object

    // Items
    Button northButton, eastButton, southButton, westButton;
    Button pickupButton, dropButton;
    Button exitButton, saveButton;
    Button backFromFight;
    Button attackButton;

    ImageView roomImageView;
    ImageView cardinalPointView;
    GifImageView knightGif;
    GifImageView victoryGif;

    TextView title;
    TextView txtRoomDescription;
    TextView txtPlayerInventory, txtRoomInventory, txtNameInventory, textNameRoom;
    TextView titleFight;
    TextView PvKnightText, LifeKnightText;
    TextView PVPlayerText, LifePlayerText;

    MediaPlayer ring;

    int fightSoldier = 0;
    private int lifePlayer = 3;
    private int lifeSoldier = 3;

    enum Choice {NONE, LIGHT, HEAVY};
    Choice playerChoice = Choice.NONE;
    Choice knightChoice = Choice.NONE;

    Random randomPlayer;
    Random randomKnight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //Setting up our shared preference and bundle
        Bundle bundle = getIntent().getExtras();
        String playerPosStr = bundle.getString(PlayActivity.KEY);

        initDungeon();
        readXMLFile();
        displayRooms();

        setupControls();

        thedungeon[0].setInventory("Key");
        thedungeon[2].setInventory("Sword");
        player = new Player(Integer.parseInt(playerPosStr));

        txtRoomDescription.setText(thedungeon[player.getPlayerPos()].getDescription());
        showDirections(player.getPlayerPos());

        txtRoomInventory.setText(thedungeon[player.getPlayerPos()].getInventory());
        txtPlayerInventory.setText(player.getInventory());

        enabledInventory();
        setupDungeonImage();

    } // protected void onCreate()

    private void setupControls() {
        // Music
        ring = MediaPlayer.create(PlayActivity.this, R.raw.rohirrim);
        ring.setLooping(true);
        ring.start();

        txtRoomDescription = findViewById(R.id.txtRoomDescription);
        txtRoomInventory = findViewById(R.id.txtViewRoomInventory);
        txtPlayerInventory = findViewById(R.id.txtViewPlayerInventory);
        title = findViewById(R.id.txtViewTitle);
        txtNameInventory = findViewById(R.id.txtViewnameinventory);
        textNameRoom = findViewById(R.id.txtViewNameRoom);
        PvKnightText = findViewById(R.id.txtPVKnight);
        LifeKnightText = findViewById(R.id.txtLifeKnight);
        PVPlayerText = findViewById(R.id.txtViewPVPlayer);
        LifePlayerText = findViewById(R.id.txtLifePlayer2);


        roomImageView = findViewById(R.id.imageViewRoom);
        cardinalPointView = findViewById(R.id.imageViewCardinalPoint);
        knightGif = findViewById(R.id.gifKnight);
        victoryGif = findViewById(R.id.gifVictory);

        titleFight = findViewById(R.id.txtViewTitleFight);

        // Setup button (navigation, updating...)
        northButton = findViewById(R.id.btnNorth);
        northButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setPlayerPos(thedungeon[player.getPlayerPos()].getNorth());
                updateRoomInformation();

                enabledInventory();
            }
        });

        eastButton = findViewById(R.id.btnEast);
        eastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setPlayerPos(thedungeon[player.getPlayerPos()].getEast());
                updateRoomInformation();

                enabledInventory();
            }
        });

        southButton = findViewById(R.id.btnSouth);
        southButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setPlayerPos(thedungeon[player.getPlayerPos()].getSouth());
                updateRoomInformation();
                enabledInventory();
            }
        });

        westButton = findViewById(R.id.btnWest);
        westButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setPlayerPos(thedungeon[player.getPlayerPos()].getWest());
                updateRoomInformation();
                enabledInventory();
            }
        });

        pickupButton = findViewById(R.id.buttonPickup);
        pickupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!thedungeon[player.getPlayerPos()].getInventory().equals(Player.NOTHING)) ) {

                    String inventory = player.getInventory();
                    player.setInventory(thedungeon[player.getPlayerPos()].getInventory());
                    thedungeon[player.getPlayerPos()].setInventory(inventory);

                    txtRoomInventory.setText(thedungeon[player.getPlayerPos()].getInventory());
                    txtPlayerInventory.setText(player.getInventory());

                    enabledInventory();
                    updateRoomInformation();
                }
            }
        });

        dropButton = findViewById(R.id.buttonDrop);
        dropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((thedungeon[player.getPlayerPos()].getInventory().equals(Player.NOTHING)) && (!player.getInventory().equals(Player.NOTHING))){
                    thedungeon[player.getPlayerPos()].setInventory(player.getInventory());
                    player.setInventory(Player.NOTHING);

                    txtRoomInventory.setText(thedungeon[player.getPlayerPos()].getInventory());
                    txtPlayerInventory.setText(player.getInventory());

                    enabledInventory();
                    updateRoomInformation();
                }
            }
        });

        saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPrefs = getSharedPreferences(SAVEFILE, MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPrefs.edit();

                edit.putInt(PLAYERPOS, player.getPlayerPos());
                edit.putString(PLAYERINVENTORY, player.getInventory());
                edit.commit();

                Toast.makeText(getApplicationContext(), "Game Saved", Toast.LENGTH_LONG).show();
            }
        });

        exitButton = findViewById(R.id.btnExit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                ring.stop();
            }
        });

        backFromFight = findViewById(R.id.buttonBackFight);
        backFromFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setVisibility(View.VISIBLE);
                txtPlayerInventory.setVisibility(View.VISIBLE);
                txtRoomInventory.setVisibility(View.VISIBLE);
                txtNameInventory.setVisibility(View.VISIBLE);
                textNameRoom.setVisibility(View.VISIBLE);
                txtRoomDescription.setVisibility(View.VISIBLE);

                eastButton.setVisibility(View.VISIBLE);

                westButton.setVisibility(View.VISIBLE);

                southButton.setVisibility(View.VISIBLE);
                southButton.setEnabled(true);

                northButton.setVisibility(View.VISIBLE);
                northButton.setEnabled(true);

                saveButton.setVisibility(View.VISIBLE);
                saveButton.setEnabled(true);

                exitButton.setVisibility(View.VISIBLE);
                exitButton.setEnabled(true);

                dropButton.setVisibility(View.VISIBLE);
                dropButton.setEnabled(true);

                pickupButton.setVisibility(View.VISIBLE);
                pickupButton.setEnabled(true);

                roomImageView.setVisibility(View.VISIBLE);
                cardinalPointView.setVisibility(View.VISIBLE);

                fightSoldier = 1;

                backFromFight.setVisibility(View.INVISIBLE);
                backFromFight.setEnabled(false);

                attackButton.setVisibility(View.INVISIBLE);
                attackButton.setEnabled(false);

                knightGif.setVisibility(View.INVISIBLE);
                titleFight.setVisibility(View.INVISIBLE);
                victoryGif.setVisibility(View.INVISIBLE);

                PvKnightText.setVisibility(View.INVISIBLE);
                LifeKnightText.setVisibility(View.INVISIBLE);
                PVPlayerText.setVisibility(View.INVISIBLE);
                LifePlayerText.setVisibility(View.INVISIBLE);

            }
        });

        attackButton = findViewById(R.id.attackButton);
        attackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attack();
                knightAttack();
                checkWin();
            }
        });

    } // private void setupControls


    protected void setupDungeonImage() {
        roomImageView = findViewById(R.id.imageViewRoom);
        int [] imageRoom = new int[]{R.drawable.room0, R.drawable.room1, R.drawable.room2, R.drawable.room3, R.drawable.room4,R.drawable.room5, R.drawable.room6, R.drawable.room7, R.drawable.room8, R.drawable.room9};
        roomImageView.setImageResource(imageRoom[player.getPlayerPos()]);
    } // protected void setupDungeonImage

    private void updateRoomInformation() {
        txtRoomDescription.setText(thedungeon[player.getPlayerPos()].getDescription());
        showDirections(player.getPlayerPos());

        txtRoomInventory.setText(thedungeon[player.getPlayerPos()].getInventory());
        txtPlayerInventory.setText(player.getInventory());

        setupDungeonImage();
    } // private void updateRoomInformation

    private void showDirections(int playerPos) {
        // Define if we can move or not in each room
        northButton.setEnabled(thedungeon[playerPos].getNorth() != Room.NO_EXIT);
        westButton.setEnabled(thedungeon[playerPos].getWest() != Room.NO_EXIT);
        southButton.setEnabled(thedungeon[playerPos].getSouth() != Room.NO_EXIT);

        if (!player.getInventory().equals("Key") && thedungeon[player.getPlayerPos()] == thedungeon[0]){
            eastButton.setEnabled(false);
        }
        else if (player.getInventory().equals("Key") && thedungeon[player.getPlayerPos()] == thedungeon[0]) {
            eastButton.setEnabled(true);
        }
        else if (thedungeon[player.getPlayerPos()] == thedungeon[9]){
            eastButton.setEnabled(true);
            eastButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), FightActivity.class);
                    startActivity(intent);
                    ring.stop();
                }
            });
        }
        else {
            eastButton.setEnabled(thedungeon[playerPos].getEast() != Room.NO_EXIT);
        }

        if (thedungeon[player.getPlayerPos()] == thedungeon[6] &&  fightSoldier == 0){
            title.setVisibility(View.INVISIBLE);
            txtPlayerInventory.setVisibility(View.INVISIBLE);
            txtRoomInventory.setVisibility(View.INVISIBLE);
            txtNameInventory.setVisibility(View.INVISIBLE);
            textNameRoom.setVisibility(View.INVISIBLE);
            txtRoomDescription.setVisibility(View.INVISIBLE);

            eastButton.setVisibility(View.INVISIBLE);
            eastButton.setEnabled(false);

            westButton.setVisibility(View.INVISIBLE);
            westButton.setEnabled(false);

            southButton.setVisibility(View.INVISIBLE);
            southButton.setEnabled(false);

            northButton.setVisibility(View.INVISIBLE);
            northButton.setEnabled(false);

            saveButton.setVisibility(View.INVISIBLE);
            saveButton.setEnabled(false);

            exitButton.setVisibility(View.INVISIBLE);
            exitButton.setEnabled(false);

            dropButton.setVisibility(View.INVISIBLE);
            dropButton.setEnabled(false);

            pickupButton.setVisibility(View.INVISIBLE);
            pickupButton.setEnabled(false);

            roomImageView.setVisibility(View.INVISIBLE);
            cardinalPointView.setVisibility(View.INVISIBLE);

            attackButton.setVisibility(View.VISIBLE);
            attackButton.setEnabled(true);

            knightGif.setVisibility(View.VISIBLE);
            titleFight.setVisibility(View.VISIBLE);

            PvKnightText.setVisibility(View.VISIBLE);
            LifeKnightText.setVisibility(View.VISIBLE);

            PVPlayerText.setVisibility(View.VISIBLE);
            LifePlayerText.setVisibility(View.VISIBLE);
        }

    } // private void showDirections()

    private int generateRandomNumberKnight() {
        // We define a random number
        randomKnight = new Random();
        return randomKnight.nextInt(2);
    } // private int generateRandomNumberKnight()

    private int generateRandomNumberPlayer() {
        randomPlayer = new Random();
        return randomPlayer.nextInt(3);
    }

    private void attack(){
        if (player.getInventory().equals("Sword")){

            attackButton.setVisibility(View.INVISIBLE);
            attackButton.setEnabled(false);

            knightGif.setVisibility(View.INVISIBLE);
            titleFight.setVisibility(View.INVISIBLE);

            PvKnightText.setVisibility(View.INVISIBLE);
            LifeKnightText.setVisibility(View.INVISIBLE);

            PVPlayerText.setVisibility(View.INVISIBLE);
            LifePlayerText.setVisibility(View.INVISIBLE);

            victoryGif.setVisibility(View.VISIBLE);

            backFromFight.setVisibility(View.VISIBLE);
            backFromFight.setEnabled(true);


        }
        else {
            int choice = 0;
            choice = generateRandomNumberPlayer();
            if (choice == 0){
                LifeKnightText.setText(String.valueOf(lifeSoldier));
                Toast.makeText(getApplicationContext(), "No Damage", Toast.LENGTH_LONG).show();
            }
            else if (choice == 1){
                lifeSoldier -= 1;
                LifeKnightText.setText(String.valueOf(lifeSoldier));
                Toast.makeText(getApplicationContext(), "1PV Damage", Toast.LENGTH_LONG).show();
            }
            else if (choice == 2){
                lifeSoldier -= 2;
                LifeKnightText.setText(String.valueOf(lifeSoldier));
                Toast.makeText(getApplicationContext(), "2PV Damage", Toast.LENGTH_LONG).show();
            }
        }
    } // private void attack()

    private void knightAttack(){
        int choice = 0;
        choice = generateRandomNumberKnight();

        if (choice == 0){
            lifePlayer -= 1;
            LifePlayerText.setText(String.valueOf(lifePlayer));
        }
        else if (choice == 1) {
            lifePlayer -= 2;
            LifePlayerText.setText(String.valueOf(lifePlayer));
        }
    } // private void knightAttack()

    private void checkWin(){
        if (lifePlayer <= 0 ) {
            Intent intent = new Intent(getBaseContext(), DeathKnightActivity.class);
            startActivity(intent);
            ring.stop();
        }
        else if (lifeSoldier <= 0){

            attackButton.setVisibility(View.INVISIBLE);
            attackButton.setEnabled(false);

            knightGif.setVisibility(View.INVISIBLE);
            titleFight.setVisibility(View.INVISIBLE);

            PvKnightText.setVisibility(View.INVISIBLE);
            LifeKnightText.setVisibility(View.INVISIBLE);

            PVPlayerText.setVisibility(View.INVISIBLE);
            LifePlayerText.setVisibility(View.INVISIBLE);

            victoryGif.setVisibility(View.VISIBLE);

            backFromFight.setVisibility(View.VISIBLE);
            backFromFight.setEnabled(true);
        }
    }

    private void enabledInventory() {
        dropButton.setEnabled(!player.getInventory().equals(Player.NOTHING));
        pickupButton.setEnabled(!thedungeon[player.getPlayerPos()].getInventory().equals(Player.NOTHING));
    } // private void enabledInventory()

    protected void initDungeon() {
        thedungeon = new Room[NUM_OF_ROOMS];
        for (int pos = 0; pos < NUM_OF_ROOMS; pos++)
        {
            thedungeon[pos] = new Room();
        }
    } // public static void initDungeon()

    private void displayRooms() {
        Log.w("display ROOM", "**** start of display rooms ****");

        for (int pos = 0; pos < NUM_OF_ROOMS; pos++)
        {
            Log.w("display ROOM", "North = " + thedungeon[pos].getNorth());
            Log.w("display ROOM", "East = " + thedungeon[pos].getEast());
            Log.w("display ROOM", "West = " + thedungeon[pos].getWest());
            Log.w("display ROOM", "South = " + thedungeon[pos].getSouth());
            Log.w("display ROOM", "Description = " + thedungeon[pos].getDescription());
        }
        Log.w("display ROOM", "**** end of display rooms ****");
    } // public void displayRooms()

    public void readXMLFile() {
        int pos = 0; // initialization of position
        try {
            int room_count = 0;

            XmlResourceParser xpp = getResources().getXml(R.xml.dungeon);
            xpp.next();
            int eventType = xpp.getEventType();
            String elemText = null;

            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if (eventType == XmlPullParser.START_TAG){      //start to read xml file
                    String elemName = xpp.getName();
                    if (elemName.equals("dungeon")){ // We get the attributes of the XML file (Author and title)
                        String titleAttr = xpp.getAttributeValue(null, "title");
                        String authorAttr = xpp.getAttributeValue(null, "author");
                    }
                    if (elemName.equals("room")){
                        room_count += 1;
                    }
                    if (elemName.equals("north")) {
                        elemText = "north";
                    }
                    if (elemName.equals("east")) {
                        elemText = "east";
                    }
                    if (elemName.equals("south")) {
                        elemText = "south";
                    }
                    if (elemName.equals("west")) {
                        elemText = "west";
                    }
                    if (elemName.equals("description")) {
                        elemText = "description";
                    }
                }

                else if (eventType == XmlPullParser.TEXT){
                    switch (elemText) {
                        case "north":
                            Log.w("ROOM", "north = " + xpp.getText());
                            thedungeon[room_count - 1].setNorth(Integer.valueOf(xpp.getText()));
                            break;
                        case "east":
                            Log.w("ROOM", "east = " + xpp.getText());
                            thedungeon[room_count - 1].setEast(Integer.valueOf(xpp.getText()));
                            break;
                        case "south":
                            Log.w("ROOM", "south = " + xpp.getText());
                            thedungeon[room_count - 1].setSouth(Integer.valueOf(xpp.getText()));
                            break;
                        case "west":
                            Log.w("ROOM", "west = " + xpp.getText());
                            thedungeon[room_count - 1].setWest(Integer.valueOf(xpp.getText()));
                            break;
                        case "description":
                            Log.w("ROOM", "description = " + xpp.getText());
                            thedungeon[room_count - 1].setDescription(xpp.getText());
                            break;
                    }
                }

                eventType = xpp.next();
            }
        }
        catch (XmlPullParserException e){

        }
        catch (IOException e){

        }
    } // public void readXMLFile

} // public class PlayActivity