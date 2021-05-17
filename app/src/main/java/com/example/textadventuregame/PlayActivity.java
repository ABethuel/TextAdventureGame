package com.example.textadventuregame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class PlayActivity extends AppCompatActivity {

    static final int NUM_OF_ROOMS = 10; // Setup the number of rooms of the app
    Room [] thedungeon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        initDungeon();
        displayRooms();
    } // protected void onCreate

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

} // public class PlayActivity