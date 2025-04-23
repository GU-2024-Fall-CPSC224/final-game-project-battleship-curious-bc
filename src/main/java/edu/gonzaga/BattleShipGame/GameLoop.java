package edu.gonzaga.BattleShipGame;

import java.util.*;

public class GameLoop {
    private static final int BOARD_SIZE = 10;
    private static final int NUM_SHIPS = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Welcome to Animal Crossing: Last Island Standing ===");
            System.out.println("1. Start Game");
            System.out.println("2. Instructions");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    startGame(scanner);
                    break;
                case "2":
                    printInstructions();
                    break;
                case "3":
                    System.out.println("Thanks for playing!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid input. Please choose 1, 2, or 3.");
            }
        }

        scanner.close();
    }

    private static void startGame(Scanner scanner) {
        System.out.print("Enter number of players (1 or 2): ");
        int mode = getIntInput(scanner, "", 1, 2);

        Player player1 = new Player("Player 1");
        Player player2;
        Board board1 = new Board(BOARD_SIZE, BOARD_SIZE);
        Board board2 = new Board(BOARD_SIZE, BOARD_SIZE);

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
        Board opponentBoard = board2;

        while (!gameOver) {
            System.out.println("\n" + currentPlayer.getName() + "'s turn to attack!");

            if (!isAI || currentPlayer == player1) {
                opponentBoard.displayBoard(false); // Updated displayBoard handles coordinates
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
                opponentBoard = board1;
            } else {
                currentPlayer = player1;
                opponent = player2;
                opponentBoard = board2;
            }
        }
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

                Ship ship = new Ship("Ship" + i, coords);
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

    private static void printInstructions() {
        System.out.println("\n=== Game Instructions ===");
        System.out.println("1. Each player places ships on their board.");
        System.out.println("2. Players take turns firing at enemy coordinates.");
        System.out.println("3. The first to sink all enemy ships wins.");
        System.out.println("- Ship sizes range from 2-5.");
        System.out.println("- Use coordinates like A0, B4 to place and attack.");
        System.out.println( "Here is the board:\n" +
            "        0  1  2  3  4  5  6  7  8  9\n" +
            "    A   ~  ~  ~  ~  ~  ~  ~  ~  ~  ~\n" +
            "    B   ~  ~  ~  ~  ~  ~  ~  ~  ~  ~\n" +
            "    C   ~  ~  ~  ~  ~  ~  ~  ~  ~  ~\n" +
            "    D   ~  ~  ~  ~  ~  ~  ~  ~  ~  ~\n" +
            "    E   ~  ~  ~  ~  ~  ~  ~  ~  ~  ~\n" +
            "    F   ~  ~  ~  ~  ~  ~  ~  ~  ~  ~\n" +
            "    G   ~  ~  ~  ~  ~  ~  ~  ~  ~  ~\n" +
            "    H   ~  ~  ~  ~  ~  ~  ~  ~  ~  ~\n" +
            "    I   ~  ~  ~  ~  ~  ~  ~  ~  ~  ~\n" +
            "    J   ~  ~  ~  ~  ~  ~  ~  ~  ~  ~\n" +
            "        0  1  2  3  4  5  6  7  8  9"
        );
        

    }
}
