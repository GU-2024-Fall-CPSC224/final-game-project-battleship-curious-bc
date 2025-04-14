package edu.gonzaga;

import java.util.ArrayList;
import java.util.List;

public class MainGameTest {
    public static void main(String[] args) {
        // Initial greetings and information
        System.out.println("Hello Team Game");
        
        // Player creation
        String playerName = "Test Player";
        int playerScore = 0;
        System.out.println("Player created: " + playerName);
        System.out.println("Player score: " + playerScore);

        // Create the game board
        Board theBoard = new Board();
        
        // Print board details
        System.out.println("Board created: " + theBoard.getBoardX() + " x " + theBoard.getBoardY());
        
        // Board status designations
        System.out.println("\nBoard Status Designations:");
        System.out.println("0 = empty, 1 = ship, 2 = hit, 3 = miss");

        // Create the ships
        Ship ship1 = createShip("Carrier", new String[]{"A0", "A1", "A2", "A3", "A4"});
        Ship ship2 = createShip("Battleship", new String[]{"E5", "E6", "E7", "E8"});

        // Place ships on the board
        if (theBoard.canPlaceShip(ship1)) {
            theBoard.placeShip(ship1);
            System.out.println("Placed: " + ship1);
        }
        
        if (theBoard.canPlaceShip(ship2)) {
            theBoard.placeShip(ship2);
            System.out.println("Placed: " + ship2);
        }

        // Print the board after ship placement
        System.out.println("\nBoard after placing ships:");
        theBoard.printBoard();

        // Example attacks
        System.out.println("\nAttacking A0: " + theBoard.attack(new Coordinate("A0")));
        System.out.println("Attacking A5: " + theBoard.attack(new Coordinate("A5")));
        System.out.println("Attacking E6: " + theBoard.attack(new Coordinate("E6")));

        // Print board after attacks
        System.out.println("\nBoard after attacks:");
        theBoard.printBoard();
        
        // Check if ships are sunk
        if (ship1.isSunk()) {
            System.out.println(ship1.getName() + " is sunk!");
        }

        if (ship2.isSunk()) {
            System.out.println(ship2.getName() + " is sunk!");
        }
    }

    // Helper method to create ships from string coordinates
    private static Ship createShip(String name, String[] coords) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (String coord : coords) {
            coordinates.add(new Coordinate(coord));
        }
        return new Ship(name, coordinates);
    }
}
