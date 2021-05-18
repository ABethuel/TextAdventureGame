package com.example.textadventuregame;

public class Player {
    // public class Player

    static final String NOTHING = "NOTHING";

    private int playerPos;
    private String inventory;

    Player()
    {
        playerPos = 0;
        inventory = NOTHING;
    }

    Player(int starPos){
        playerPos = starPos;
        inventory = NOTHING;
    }

    public int getPlayerPos(){
        return playerPos;
    }

    public void setPlayerPos(int playerPos){
        this.playerPos = playerPos;
    }

    public String getInventory(){
        return inventory;
    }

    public void setInventory(String inventory){
        this.inventory = inventory;
    }
}
