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
        System.out.println("Hello Team Game");

        Board theBoard = new Board();
        
        Player player = new Player("Test Player");

        System.out.println("Player created: " + player.getName());
        
        System.out.println("Player score: " + player.getScore(0));

        System.out.println("Board created: " + theBoard.getBoardX() + " x " + theBoard.getBoardY());
        System.out.println("");
        System.out.println("Board Status Designations:");
        System.out.println("0 = empty, 1 = ship, 2 = hit, 3 = miss");
        System.out.println("");
        System.out.println("Board status: ");
        // Print the board status
        for (int y = 0; y < theBoard.getBoardY(); y++){
            for (int x = 0; x < theBoard.getBoardX(); x++){
                int tmp = theBoard.getFieldStatus(x, y);
                System.out.print("[" + tmp + "]");
            }
            System.out.println();
        }

        System.out.println("Test Coordinates: ");
        System.out.println("A0 = " + new Coordinate("A0").getRow() + ", " + new Coordinate("A0").getCol());
        assert new Coordinate("A0").getRow() == 0 : "Row should be 0 for A0";
        assert new Coordinate("A0").getCol() == 0 : "Column should be 0 for A0";
        
        System.out.println("E5 = " + new Coordinate("E5").getRow() + ", " + new Coordinate("E5").getCol());
        assert new Coordinate ("E5").getRow() == 4 : "Row should be 4 for E5";
        assert new Coordinate ("E5").getCol() == 5 : "Column should be 5 for E5";


        System.out.println("J9 = " + new Coordinate("J9").getRow() + ", " + new Coordinate("J9").getCol());
        assert new Coordinate("J9").getRow() == 9 : "Row should be 9 for J9";
        assert new Coordinate("J9").getCol() == 9 : "Column should be 9 for J9";

        System.out.println("");
        System.out.println("Test Fleet: ");
        System.out.println("Creating a fleet of 1 ship.");
        System.out.println("Creating a ship with coordinates A0, adding to fleet.");

        // Create a fleet to hold ships
                List<Ship> testFleet = new ArrayList<>();

        // Create a list of coordinates for the ship
                List<Coordinate> shipCoordinates = new ArrayList<>();

        // Create the ship with the correct constructor
                Ship testShip = new Ship("Test Ship 1", shipCoordinates);

        // Add coordinates to the ship
                shipCoordinates.add(new Coordinate("A0"));

        // Check if the ship can be placed on the board
               if (theBoard.canPlaceShip(testShip)) {
                theBoard.placeShip(testShip); // Place the ship on the board
                System.out.println("Ship placed successfully at coordinates: " + shipCoordinates);
                } else {
                System.out.println("Ship cannot be placed on the board.");
            }
    
        // Add the ship to the fleet
                testFleet.add(testShip); 

        // Print ship details
        //        System.out.println("Ship created: " + testShip.getName() + " with coordinates: " + shipCoordinates);


        // Check the board status after placing the ship
                System.out.println("");
                System.out.println("Board status after placing the ship: ");
                for (int y = 0; y < theBoard.getBoardY(); y++){
                    for (int x = 0; x < theBoard.getBoardX(); x++){
                        int tmp = theBoard.getFieldStatus(x, y);
                        System.out.print("[" + tmp + "]");
                    }
                    System.out.println();
                }
        // Check if the ship is in the fleet
                if (testFleet.contains(testShip)) {
                    System.out.println(testShip.getName() + " is in the fleet.");
                } else {
                    System.out.println(testShip.getName() + " is not in the fleet.");
                }


        // Read and print details of ships in the fleet
        System.out.println("Fleet details:");
        for (Ship ship : testFleet) {
            System.out.println("Ship name: " + ship.getName());
        }
    }
    
}
