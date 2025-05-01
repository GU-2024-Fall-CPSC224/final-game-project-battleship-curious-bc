package edu.gonzaga.BattleShipGame;

import java.util.List;

public class Island {
    private String name;
    private List<Coordinate> coordinates;

    // Constructor
    public Island(String name, List<Coordinate> coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    // Get the name of the island
    public String getName() {
        return name;
    }

    // Get the coordinates of the island
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    // Check if the island contains a specific coordinate
    public boolean hasCoordinates(Coordinate coord) {
        return coordinates.contains(coord);
    }
}