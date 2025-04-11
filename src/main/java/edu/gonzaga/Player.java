package edu.gonzaga;

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

    public int getScore(int score) {
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

}