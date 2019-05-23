package com.codecool.klondike;

public class Player {
    //private String nickname;
    private int id;
    private boolean turn = true;
    private int score = 0;
    private boolean isPlayerAllive;

    public Player( int id, int score){
        //this.nickname = nickname;
        this.id = id;
        this.score = score;
        this.isPlayerAllive = true;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public int id() {
        return id;
    }

    public boolean hasTurn() {
        return turn;
    }

    public void setPlayerAllive(boolean playerAllive) {
        isPlayerAllive = playerAllive;
    }

    public int getScore() {
        return score;
    }
}
