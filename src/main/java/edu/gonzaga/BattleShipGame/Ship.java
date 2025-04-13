package edu.gonzaga.BattleShipGame;
import java.util.List;
import java.util.ArrayList;

public class Ship {
    private String shipName;
    private List<Coordinate> coords;

    // Constructor
    public Ship(String shipName, List<Coordinate> coords) {
        this.shipName = shipName;
        this.coords = new ArrayList<>(coords); // Defensive copy
    }

    // Returns the name of the ship
    public String getName() {
        return shipName;
    }

    // Returns the list of coordinates occupied by the ship
    public List<Coordinate> getPoints() {
        return new ArrayList<>(coords); // Return copy to prevent external modification
    }

    // Checks if the ship occupies a given coordinate
    public boolean hasCoordinates(Coordinate coord) {
        return coords.contains(coord);
    }

    // Checks if the ship has been completely hit
    public boolean noMoreShip() {
        return coords.isEmpty();
    }

    // Registers a hit on the ship at the given coordinate
    public void shipHit(Coordinate coord) {
        coords.remove(coord); // Removes the coordinate if present
    }
}
