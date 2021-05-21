package com.example.textadventuregame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class PlayActivity extends AppCompatActivity {

    //PLaying and saving information
    static final String SAVEFILE = "savedgame";
    static final String PLAYERPOS = "playerpos";
    static final String KEY = "pos";

    static final int NUM_OF_ROOMS = 10; // Setup the number of rooms of the app
    Room [] thedungeon;
    Player player; //player object

    // Items
    Button northButton, eastButton, southButton, westButton;
    Button pickupButton, dropButton;
    Button exitButton, saveButton;

    TextView txtRoomDescription;
    TextView txtPlayerInventory, txtRoomInventory;

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

        thedungeon[0].setInventory("Sword");
        player = new Player(Integer.parseInt(playerPosStr));

        txtRoomDescription.setText(thedungeon[player.getPlayerPos()].getDescription());
        showDirections(player.getPlayerPos());

        txtRoomInventory.setText(thedungeon[player.getPlayerPos()].getInventory());
        txtPlayerInventory.setText(player.getInventory());

        enabledInventory();

    } // protected void onCreate()

    private void setupControls() {
        // Music
        MediaPlayer ring = MediaPlayer.create(PlayActivity.this, R.raw.rohirrim);
        ring.setLooping(true);
        ring.start();

        txtRoomDescription = findViewById(R.id.txtRoomDescription);
        txtRoomInventory = findViewById(R.id.txtViewRoomInventory);
        txtPlayerInventory = findViewById(R.id.txtViewPlayerInventory);

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

                // if (player.getPlayerPos() == 9){
                if (thedungeon[player.getPlayerPos()] == thedungeon[9]){
                    Intent intent = new Intent(getBaseContext(), FightActivity.class);
                    startActivity(intent);

                    ring.stop();

                }

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
                if ((!thedungeon[player.getPlayerPos()].getInventory().equals(Player.NOTHING)) && (player.getInventory().equals(Player.NOTHING))) {
                    player.setInventory(thedungeon[player.getPlayerPos()].getInventory());
                    thedungeon[player.getPlayerPos()].setInventory(Player.NOTHING);

                    txtRoomInventory.setText(thedungeon[player.getPlayerPos()].getInventory());
                    txtPlayerInventory.setText(player.getInventory());

                    enabledInventory();
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

    } // private void setupControls

    private void updateRoomInformation() {
        txtRoomDescription.setText(thedungeon[player.getPlayerPos()].getDescription());
        showDirections(player.getPlayerPos());

        txtRoomInventory.setText(thedungeon[player.getPlayerPos()].getInventory());
        txtPlayerInventory.setText(player.getInventory());
    } // private void updateRoomInformation

    private void showDirections(int playerPos) {
        // Define if we can move or not in each room
        northButton.setEnabled(thedungeon[playerPos].getNorth() != Room.NO_EXIT);
        westButton.setEnabled(thedungeon[playerPos].getWest() != Room.NO_EXIT);
        southButton.setEnabled(thedungeon[playerPos].getSouth() != Room.NO_EXIT);

        if (playerPos == 9){
            eastButton.setEnabled(true);
        }
        else {
            eastButton.setEnabled(thedungeon[playerPos].getEast() != Room.NO_EXIT);
        }

    } // private void showDirections()

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
                // We will add code there to read rooms

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