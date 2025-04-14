package edu.gonzaga;

import java.util.Random;

public class AIPlayer extends Player {

    // Constructor
    public AIPlayer(String name) {
        super(name);
    }

    // AI decides to place a ship randomly (horizontal or vertical)
    public boolean placeRandomShip(Board board, Ship ship) {
        Random rand = new Random();
        boolean isHorizontal = rand.nextBoolean();  // Randomly choose horizontal or vertical
        int row = rand.nextInt(board.getBoardY());  // Random starting row
        int col = rand.nextInt(board.getBoardX());  // Random starting column

        Coordinate startCoordinate = new Coordinate(row, col);
        
        return placeShip(board, ship, isHorizontal, startCoordinate);
    }
}
