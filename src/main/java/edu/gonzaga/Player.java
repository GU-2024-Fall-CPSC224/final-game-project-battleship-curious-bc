package edu.gonzaga;

import java.util.ArrayList;
import java.util.List;

public class Player {

    protected String name;
    protected int score;
    protected List<Ship> fleet; // List to store ships

    // Player constructor
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.fleet = new ArrayList<>();
    }

    // Set player's name
    public void setName(String name) {
        this.name = name;
    }

    // Get player's name
    public String getName() {
        return name;
    }

    // Get player's score
    public int getScore() {
        return score;
    }

    // Set player's score
    public void setScore(int score) {
        this.score = score;
    }

    // Add a ship to the player's fleet
    public void addShip(Ship ship) {
        fleet.add(ship);
    }

    // Get the list of ships in the player's fleet
    public List<Ship> getFleet() {
        return fleet;
    }

    // Place a ship on the board
    public boolean placeShip(Board board, Ship ship, boolean isHorizontal, Coordinate startCoordinate) {
        List<Coordinate> shipCoordinates = new ArrayList<>();
        int startRow = startCoordinate.getRow();
        int startCol = startCoordinate.getCol();

        // Calculate coordinates based on ship's direction (horizontal or vertical)
        for (int i = 0; i < ship.getCoordinates().size(); i++) {
            if (isHorizontal) {
                shipCoordinates.add(new Coordinate(startRow, startCol + i));
            } else {
                shipCoordinates.add(new Coordinate(startRow + i, startCol));
            }
        }

        // Check if the ship can be placed and place it on the board
        if (board.canPlaceShip(shipCoordinates)) {
            ship.setCoordinates(shipCoordinates);
            board.placeShip(ship); // Place the ship on the board
            return true;
        } else {
            return false; // If the ship cannot be placed, return false
        }
    }
}
