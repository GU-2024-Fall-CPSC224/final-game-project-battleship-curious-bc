package edu.gonzaga.BattleShipGame;

import java.util.*;

public class GameLoop {
    private static final int BOARD_SIZE = 10;
    private static final int NUM_SHIPS = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Welcome to Classic Battleship ===");
        System.out.print("Enter number of players (1 or 2): ");
        int mode = getIntInput(scanner, "", 1, 2);

        Player player1 = new Player("Player 1");
        Player player2;
        Board board1 = new Board(BOARD_SIZE, BOARD_SIZE);
        Board board2 = new Board(BOARD_SIZE, BOARD_SIZE);

        // Setup second player (human or AI)
        boolean isAI = false;
        boolean smartAI = false;

        if (mode == 2) {
            player2 = new Player("Player 2");
        } else {
            System.out.print("Enable smart AI mode? (Y/N): ");
            String aiMode = scanner.nextLine().trim().toUpperCase();
            smartAI = aiMode.equals("Y");
            player2 = new AIPlayer("AI", smartAI);
            isAI = true;
        }

        // Place ships
        System.out.println("\n" + player1.getName() + ", place your ships.");
        placeShips(scanner, board1, NUM_SHIPS);

        if (isAI) {
            ((AIPlayer) player2).placeShips(board2, NUM_SHIPS);
            System.out.println("AI has placed its ships.");
        } else {
            System.out.println("\n" + player2.getName() + ", place your ships.");
            placeShips(scanner, board2, NUM_SHIPS);
        }

        // Main game loop
        boolean gameOver = false;
        Player currentPlayer = player1;
        Player opponent = player2;
        Board currentBoard = board1;
        Board opponentBoard = board2;

        while (!gameOver) {
            System.out.println("\n" + currentPlayer.getName() + "'s turn to attack!");

            if (!isAI || currentPlayer == player1) {
                printHiddenBoard(opponentBoard);
            }

            Coordinate target;
            if (isAI && currentPlayer instanceof AIPlayer) {
                target = ((AIPlayer) currentPlayer).chooseAttack(opponentBoard);
                System.out.println("AI fires at: " + target);
            } else {
                target = getCoordinateInput(scanner, "Enter coordinate to fire at (e.g., B4): ");
                if (!opponentBoard.canPlaceShot(target)) {
                    System.out.println("Invalid or already fired upon. Try again.");
                    continue;
                }
            }

            boolean isHit = opponentBoard.attack(target);
            if (isHit) {
                System.out.println("Hit!");
                currentPlayer.setScore(currentPlayer.getScore() + 1);
                String sunk = opponentBoard.shipNameIfKill(target);
                if (sunk != null) {
                    System.out.println("You sunk the " + sunk + "!");
                    currentPlayer.setScore(currentPlayer.getScore() + 2);
                }
                if (currentPlayer instanceof AIPlayer) {
                    ((AIPlayer) currentPlayer).handleHit(target, opponentBoard);
                }
            } else {
                System.out.println("Miss.");
            }

            System.out.println("Score: " + currentPlayer.getScore());
            System.out.println("Remaining enemy ships: " + opponentBoard.getFleetSize());

            if (opponentBoard.isGameOver()) {
                System.out.println("\nGame Over! " + currentPlayer.getName() + " wins!");
                gameOver = true;
                break;
            }

            // Wait and clear screen (if human vs human)
            if (!isAI) {
                System.out.println("\nPass the computer to the next player. Press Enter to continue.");
                scanner.nextLine();
                for (int i = 0; i < 50; i++) System.out.println(); // screen clear
            }

            // Swap players and boards
            if (currentPlayer == player1) {
                currentPlayer = player2;
                opponent = player1;
                currentBoard = board2;
                opponentBoard = board1;
            } else {
                currentPlayer = player1;
                opponent = player2;
                currentBoard = board1;
                opponentBoard = board2;
            }
        }

        scanner.close();
    }

    // ========== Supporting Methods ==========

    private static void placeShips(Scanner scanner, Board board, int numShips) {
        for (int i = 1; i <= numShips; i++) {
        while (true) {
            System.out.println("\nPlacing Ship #" + i);

            int length = getIntInput(scanner, "Enter ship length (2-5): ", 2, 5);
            Coordinate start = getCoordinateInput(scanner, "Enter starting coordinate (e.g., A0): ");
            System.out.print("Enter direction (H for horizontal, V for vertical): ");
            String dir = scanner.nextLine().trim().toUpperCase();
            boolean horizontal = dir.equals("H");

            List<Coordinate> coords = new ArrayList<>();
            for (int j = 0; j < length; j++) {
                int row = start.getRow() + (horizontal ? 0 : j);
                int col = start.getCol() + (horizontal ? j : 0);

                if (row >= BOARD_SIZE || col >= BOARD_SIZE) {
                    coords = null;
                    break;
                }
                coords.add(new Coordinate(row, col));
            }

            if (coords == null || coords.size() != length) {
                System.out.println("Invalid ship placement. Try again.");
                continue;
            }

            Ship ship = new Ship("Ship" + i, coords); // Auto-named ships
            if (board.canPlaceShip(ship)) {
                board.placeShip(ship);
                System.out.println("Ship placed: " + coords);
                break;
            } else {
                System.out.println("Cannot place ship on those coordinates. Try again.");
            }
        }
    }
}

    private static Coordinate getCoordinateInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return new Coordinate(input);
            } catch (Exception e) {
                System.out.println("Invalid format. Try again (e.g., A4)." );
            }
        }
    }

    private static int getIntInput(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            if (!prompt.isEmpty()) System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (Exception ignored) {}
            System.out.println("Invalid number. Please enter a value between " + min + " and " + max + ".");
        }
    }

    private static void printHiddenBoard(Board board) {
        System.out.println("\nOpponent Board:");
        for (int row = 0; row < 10; row++) {
            System.out.print((char)('A' + row) + " ");
            for (int col = 0; col < 10; col++) {
                int val = board.getFieldStatus(row, col);
                if (val == 1) {
                    System.out.print(" O ");
                } else if (val == 3) {
                    System.out.print(" X ");
                } else {
                    System.out.print(" ~ ");
                }
            }
            System.out.println();
        }
        System.out.print("   ");
        for (int col = 0; col < 10; col++) {
            System.out.printf("%2d ", col);
        }
        System.out.println();
    }
}
