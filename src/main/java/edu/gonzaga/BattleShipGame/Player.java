package edu.gonzaga.BattleShipGame;

public class Player {

    // protected so that AI player can acess both
    protected String name; 
    protected int score;

    //Player using this keyword for ease of use
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void handleHit(Coordinate hitCoord, Board board) {
    }

}