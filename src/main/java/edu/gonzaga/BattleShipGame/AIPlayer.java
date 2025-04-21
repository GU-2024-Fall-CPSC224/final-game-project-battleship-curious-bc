package edu.gonzaga.BattleShipGame;

import java.util.*;

public class AIPlayer extends Player {
    private Set<Coordinate> firedShots;           // Memory of fired shots
    private Queue<Coordinate> targetQueue;        // Smart targeting queue
    private Random rand = new Random();
    private boolean smartMode;

    public AIPlayer(String name, boolean smartMode) {
        super(name);
        this.smartMode = smartMode;
        firedShots = new HashSet<>();
        targetQueue = new LinkedList<>();
    }

    // Place ships randomly
    public void placeShips(Board board, int numShips) {
        for (int i = 0; i < numShips; i++) {
            while (true) {
                int length = rand.nextInt(4) + 2; // ship size 2–5
                int row = rand.nextInt(10);
                int col = rand.nextInt(10);
                boolean horizontal = rand.nextBoolean();

                List<Coordinate> coords = new ArrayList<>();
                for (int j = 0; j < length; j++) {
                    int r = row + (horizontal ? 0 : j);
                    int c = col + (horizontal ? j : 0);

                    if (r >= 10 || c >= 10) {
                        coords = null;
                        break;
                    }
                    coords.add(new Coordinate(r, c));
                }

                if (coords != null && coords.size() == length) {
                    Ship ship = new Ship("AIShip" + i, coords);
                    if (board.canPlaceShip(ship)) {
                        board.placeShip(ship);
                        break;
                    }
                }
            }
        }
    }

    // Decide where to attack next
    public Coordinate chooseAttack(Board board) {
        Coordinate choice;

        // Smart mode: Use targetQueue if possible
        if (smartMode && !targetQueue.isEmpty()) {
            choice = targetQueue.poll();
            while (firedShots.contains(choice) || !board.canPlaceShot(choice)) {
                if (targetQueue.isEmpty()) {
                    return chooseRandomShot(board);
                }
                choice = targetQueue.poll();
            }
        } else {
            choice = chooseRandomShot(board);
        }

        firedShots.add(choice);
        return choice;
    }

    // If a hit, enqueue adjacent positions
    public void handleHit(Coordinate hitCoord, Board board) {
        if (!smartMode) return;

        int[][] directions = { {0,1}, {1,0}, {0,-1}, {-1,0} };
        for (int[] dir : directions) {
            int newRow = hitCoord.getRow() + dir[0];
            int newCol = hitCoord.getCol() + dir[1];
            Coordinate adj = new Coordinate(newRow, newCol);
            if (newRow >= 0 && newRow < 10 && newCol >= 0 && newCol < 10
                && !firedShots.contains(adj) && board.canPlaceShot(adj)) {
                targetQueue.add(adj);
            }
        }
    }

    private Coordinate chooseRandomShot(Board board) {
        Coordinate coord;
        do {
            int row = rand.nextInt(10);
            int col = rand.nextInt(10);
            coord = new Coordinate(row, col);
        } while (firedShots.contains(coord) || !board.canPlaceShot(coord));
        return coord;
    }
}
