/*
 * Final project main driver class
 * 
 * 
 * Project Description:
 * 
 * 
 * Contributors:
 * 
 * 
 * Copyright: 2025
 */
package edu.gonzaga.BattleShipGame;

import java.util.ArrayList;
import java.util.List;


/** Main program class for launching your team's program. */
public class MainGameTest {
    public static void main(String[] args) {
        System.out.println("Hello Team Game\n");

        Board theBoard = new Board();
        
        Player player = new Player("Test Player");

        System.out.println("Player created: " + player.getName());
        System.out.println("Board created: " + theBoard.getBoardX() + " x " + theBoard.getBoardY());
    
        System.out.println("\nBoard Legend:");
        System.out.println("~ = empty, S = ship, X = hit, O = miss\n");
    
        // Print the board status
        theBoard.displayBoard(false);
        
        // Create a fleet to hold ships
        List<Ship> testFleet = new ArrayList<>();

        // Create a ship with coordinates A0, E5, J9
        List<Coordinate> shipCoordinates = new ArrayList<>();
        shipCoordinates.add(new Coordinate("A0"));
        shipCoordinates.add(new Coordinate("E5"));
        shipCoordinates.add(new Coordinate("J9"));

        Ship testShip = new Ship("Test Ship", shipCoordinates);

        
        if (theBoard.canPlaceShip(testShip)) {
            theBoard.placeShip(testShip);
            testFleet.add(testShip);
            System.out.println("\nTest ship placed successfully at: " + shipCoordinates);
        } else {
            System.out.println("\nShip cannot be placed due to conflict.");
        }

        System.out.println("\nBoard after placing ship (revealShips = true):");
        theBoard.displayBoard(true);
    }
}