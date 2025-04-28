package edu.gonzaga.BattleShipGame;

import java.util.ArrayList; 
import java.util.Iterator;
import java.util.List;


public class Board {
    private int boardX = 10;
    private int boardY = 10; 

    private int[][] fieldStatic;
    ArrayList<Ship> fleet = new ArrayList<>();

    //Default constructor
    public Board(){
        fieldStatic = new int[boardX][boardY];
        for (int i = 0; i < boardX; i++){
            for (int j = 0; j < boardY; j++){
                fieldStatic[i][j] = 0;
            }
        }
    }

    //Board constructor with parameters
    public Board(int x, int y){
        boardX = x;
        boardY = y;
        fieldStatic = new int[x][y];
        for (int i = 0; i < boardX; i++){
            for (int j = 0; j < boardY; j++){
                fieldStatic[i][j] = 0;
            }
        }
    }
    
    public boolean attack(Coordinate coord) {
        if (!canPlaceShot(coord)) return false;
        int result = placeShot(coord);
        return result == 3; // true if hit
    }

    public int getFleetSize() {
        return fleet.size();
    }
    
    public int getFieldStatus(int x, int y){
        return this.fieldStatic[x][y];
    }

    public boolean canPlaceShip(Ship theShip) {
        for (Coordinate coord : theShip.getCoordinates()) {
            int row = coord.getRow();
            int col = coord.getCol();
            if (row < 0 || row >= boardX || col < 0 || col >= boardY) {
                return false; // Out of bounds
            }
            if (getFieldStatus(row, col) != 0) { // 0 = empty
                return false; // Collision with another ship
            }
        }
        return true;
    }    

public void placeShip(Ship theShip) {
    if (canPlaceShip(theShip)) {
        for (Coordinate coord : theShip.getCoordinates()) {
            int row = coord.getRow();
            int col = coord.getCol();
            fieldStatic[row][col] = 2; // Mark the field as occupied by a ship
        }
        fleet.add(theShip); // Add the ship to the fleet
    } else {
        throw new IllegalStateException("Cannot place ship at given coordinates.");
    }
}

    public void removeShip(Ship theShip){
        Iterator<Coordinate> iterate = theShip.getCoordinates().iterator();
        while (iterate.hasNext()){
            Coordinate coord = iterate.next();
            int x = coord.getRow();
            int y = coord.getCol();
            fieldStatic[x][y] = 0; // Remove the ship from the board
        }
        fleet.remove(theShip); // Remove the ship from the fleet
    }


    public boolean canPlaceShot(Coordinate coord){
        int x = coord.getRow();
        int y = coord.getCol();
        int field = fieldStatic[x][y];
        if (x < 0 || x >= boardX || y < 0 || y >= boardY) {
            return false; // Out of bounds
        }
        else if (field == 0 || field == 2) {
        return true; // empty/not hit yet, or empty/hit already
        } 
        return false; // already occupied
    }


    public int placeShot (Coordinate coord){
        int x = coord.getRow();
        int y = coord.getCol();
        int field = fieldStatic[x][y];
        
        if (field == 1 || field == 3){
        throw new IllegalStateException("Field already been shot at/hit.");
        } else if (field == 0) {
            fieldStatic[x][y] = 1;
            return field; // Missed shot
        } else {
            fieldStatic[x][y] = 3; // Mark as Shot Hit
            Iterator<Ship> iterator = fleet.iterator();
        while (iterator.hasNext()) {
            Ship ship = iterator.next();
            if (ship.hasCoordinates(coord)) {
                ship.shipHit(coord); // Register the hit on the ship
                if (ship.noMoreShip()) {
                    iterator.remove(); // Safely remove the ship from the fleet
                    }
                }
            }
        }
        return fieldStatic [x][y];
    }

    // Get board X dimension
    public int getBoardX() {
        return this.boardX;
    }

    // Get board Y dimension
    public int getBoardY() {
        return boardY;
    }

    public String shipNameIfKill (Coordinate theCoord){
        for (Ship ship : fleet) {
            if (ship.hasCoordinates(theCoord)) {
                return ship.getName(); // Return the name of the ship that was sunk
            }
        }
        return null; // No ship found at the coordinate
    }

    public int shipPointsIfKill (Coordinate theCoord){
        for (int i = 0; i < fleet.size(); i++) {
            Ship ship = fleet.get(i);
            if (ship.hasCoordinates(theCoord)) {
                return ship.getCoordinates().size(); // Return the number of points of the ship that was sunk
            }
        }
        return 0; // No ship found at the coordinate
    }

    public boolean isGameOver(){
        return fleet.isEmpty(); // Return true if all ships have been sunk, false otherwise
    }

    public void displayBoard(boolean revealShips) {
        System.out.print("   ");
        for (int col = 0; col < boardX; col++) {
            System.out.printf("%2d ", col);
        }
        System.out.println();

        for (int row = 0; row < boardY; row++) {
            System.out.printf("%c  ", (char)('A' + row));
            for (int col = 0; col < boardX; col++) {
                int val = fieldStatic[row][col];
                if (val == 0) {
                    System.out.print(" ~ ");
                } else if (val == 2 && revealShips) {
                    System.out.print(" S ");
                } else if (val == 1) {
                    System.out.print(" O "); // Miss
                } else if (val == 3) {
                    System.out.print(" X "); // Hit
                } else {
                    System.out.print(" ~ ");
                }
            }
            System.out.println();
        }
        System.out.print("   ");
        for (int col = 0; col < boardX; col++) {
            System.out.printf("%2d ", col);
        }
        System.out.println();
    }
}
