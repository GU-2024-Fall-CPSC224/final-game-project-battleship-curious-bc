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

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Welcome to Battleship ===");
            System.out.println("1. Start Game");
            System.out.println("2. View Instructions");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    GameLoop.main(args);  // Start the game!
                    break;
                case "2":
                    printInstructions();
                    break;
                case "3":
                    exit = true;
                    System.out.println("Thanks for playing Battleship!");
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }

        scanner.close();
    }

    private static void printInstructions() {
        System.out.println("\n=== How to Play Battleship ===");
        System.out.println("- Choose between 1-player (vs AI) or 2-player mode.");
        System.out.println("- Players place ships on their own 10x10 board.");
        System.out.println("- On each turn, choose a coordinate to fire at (e.g., A5).");
        System.out.println("- The goal is to sink all enemy ships.");
        System.out.println("- Smart AI mode targets around hits to sink ships faster.");
        System.out.println("- First to eliminate all opponent ships wins!\n");
    }
}
