package edu.gonzaga;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private static final int SIZE = 10;  // 10x10 grid
    private int[][] grid;  // The grid of the board (0 = empty, 1 = ship, 2 = hit, 3 = miss)

    public Board() {
        grid = new int[SIZE][SIZE];
    }

    public int getBoardX() {
        return SIZE;
    }

    public int getBoardY() {
        return SIZE;
    }

    // Return the status of a specific field on the board
    public int getFieldStatus(int x, int y) {
        return grid[y][x];
    }

    // Check if a ship can be placed on the board at the given coordinates
    public boolean canPlaceShip(Ship ship) {
        for (Coordinate coord : ship.getCoordinates()) {
            if (coord.getRow() < 0 || coord.getRow() >= SIZE || coord.getCol() < 0 || coord.getCol() >= SIZE) {
                return false;  // Ship is out of bounds
            }
            if (grid[coord.getRow()][coord.getCol()] != 0) {
                return false;  // Overlap with existing ship
            }
        }
        return true;
    }

    // Place the ship on the board
    public void placeShip(Ship ship) {
        for (Coordinate coord : ship.getCoordinates()) {
            grid[coord.getRow()][coord.getCol()] = 1;  // Mark as part of a ship
        }
    }

    // Attack a coordinate on the board
    public String attack(Coordinate coord) {
        if (grid[coord.getRow()][coord.getCol()] == 1) {
            grid[coord.getRow()][coord.getCol()] = 2;  // Mark as hit
            return "Hit!";
        } else if (grid[coord.getRow()][coord.getCol()] == 0) {
            grid[coord.getRow()][coord.getCol()] = 3;  // Mark as miss
            return "Miss!";
        } else {
            return "Already attacked here!";
        }
    }

    // Print the board
    public void printBoard() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                System.out.print("[" + grid[y][x] + "]");
            }
            System.out.println();
        }
    }
}
