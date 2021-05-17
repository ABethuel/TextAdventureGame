package com.example.textadventuregame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class PlayActivity extends AppCompatActivity {

    static final int NUM_OF_ROOMS = 10; // Setup the number of rooms of the app
    Room [] thedungeon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        initDungeon();
        displayRooms();
        readXMLFile();
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
                if (eventType == XmlPullParser.START_TAG){
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
                // We will add code there to read code
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
            }
        }
        catch (XmlPullParserException e){

        }
        catch (IOException e){

        }
    } // public void readXMLFile
} // public class PlayActivity