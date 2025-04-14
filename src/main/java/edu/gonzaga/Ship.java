package edu.gonzaga;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private String name;
    private List<Coordinate> coordinates;
    private List<Coordinate> hitCoordinates;

    public Ship(String name, List<Coordinate> coordinates) {
        this.name = name;
        this.coordinates = coordinates;
        this.hitCoordinates = new ArrayList<>();
    }

    // Return the ship's name
    public String getName() {
        return name;
    }

    // Return all ship coordinates
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    // Check if a coordinate is part of this ship
    public boolean hasCoordinates(Coordinate coord) {
        return coordinates.contains(coord);
    }

    // Register a hit on this ship
    public void shipHit(Coordinate coord) {
        if (hasCoordinates(coord) && !hitCoordinates.contains(coord)) {
            hitCoordinates.add(coord);
        }
    }

    // Check if the ship is completely hit (sunk)
    public boolean isSunk() {
        return hitCoordinates.size() == coordinates.size();
    }
    
    @Override
    public String toString() {
        return name + " at " + coordinates.toString();
    }
}
