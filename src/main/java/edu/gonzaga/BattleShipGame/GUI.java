package edu.gonzaga.BattleShipGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUI {
    private static boolean isAI = false; //Flag for AI component
    private static String playerFaction; // Player 1 faction
    private static String oppFaction;    // Player 2 or AI faction
    private static Player currentPlayer;
    private static Player opponent;
    private static Board oppBoard;
    private static Board playerBoard;
    private static JButton [][] playerButtons;
    private static JButton [][] oppButtons;
    private static int shipSize = 2;
    private static int shipsPlaced = 0;
    private static boolean horizontalPlacement = true;
    private static JButton rotateButton;
    private static JButton confirmButton;
    private static JLabel statusLabel;
    private static JFrame gameFrame;
    private static JPanel gamePanel;
    private static List<Coordinate> selectedShipCoordinates = new ArrayList<>();

    // Color constants
    private static final Color WATER_COLOR = new Color(0, 105, 148); // Dark blue water
    private static final Color SHIP_COLOR = new Color(80, 80, 80); // Dark gray for ships
    private static final Color INVALID_COLOR = new Color(255, 100, 100); // Light red for invalid
    private static final Color HIGHLIGHT_COLOR = new Color(255, 200, 100); // Yellow for highlights

    public static void displaySplashMenu() {
        // Create the JFrame
        JFrame frame = new JFrame("Battleship Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Set the desired frame size
        frame.setLayout(null); // Use absolute positioning for layering

        // Load the PNG image
        ImageIcon originalImage = new ImageIcon(GUI.class.getResource("/1.png"));
        Image scaledImage = originalImage.getImage().getScaledInstance(
            frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH // Scale to fit the frame
        );
        ImageIcon splashImage = new ImageIcon(scaledImage);

        // Add the scaled image to a JLabel
        JLabel imageLabel = new JLabel(splashImage);
        imageLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight()); // Set bounds to cover the frame
        frame.add(imageLabel);

        // Create a transparent panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Align buttons vertically
        buttonPanel.setOpaque(false); // Make the panel transparent
        buttonPanel.setBounds(frame.getWidth() / 2 - 100, frame.getHeight() / 2 - 35, 205, 200); // Center the panel

        // Create a larger font for the buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        // Define a fixed size for all buttons
        Dimension buttonSize = new Dimension(180, 50);

        // Add "Play" button
        JButton playButton = new JButton("Play");
        playButton.setFont(buttonFont); // Set larger font
        playButton.setPreferredSize(buttonSize); // Set fixed size
        playButton.setMaximumSize(buttonSize); // Ensure consistent size
        playButton.setAlignmentX(JButton.CENTER_ALIGNMENT); // Center the button horizontally
        playButton.setBackground(new java.awt.Color(255, 165, 0)); // Set orange background
        playButton.setForeground(Color.RED); // Set white text color
        playButton.setFocusPainted(false); // Remove focus border
        playButton.addActionListener(e -> {
            frame.dispose(); // Close the splash menu
            disPlayerSelection(); // Show the player selection
            // GameLoop.startGame(); // Call the startGame method from GameLoop
        });
        buttonPanel.add(playButton);
        buttonPanel.add(Box.createVerticalStrut(12)); // Add spacing between buttons

        // Add "Instructions" button
        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.setFont(buttonFont); // Set larger font
        instructionsButton.setPreferredSize(buttonSize); // Set fixed size
        instructionsButton.setMaximumSize(buttonSize); // Ensure consistent size
        instructionsButton.setAlignmentX(JButton.CENTER_ALIGNMENT); // Center the button horizontally
        instructionsButton.setBackground(new java.awt.Color(255, 165, 0)); // Set orange background
        instructionsButton.setForeground(Color.RED); // Set white text color
        instructionsButton.setFocusPainted(false); // Remove focus border
        instructionsButton.addActionListener(e -> {
            // Create a JLabel with custom font for the instructions
            JLabel instructionsLabel = new JLabel(
                "<html>Instructions:<br>" +
                "1. Each player places ships on their board.<br>" +
                "2. Players take turns firing at enemy coordinates.<br>" +
                "3. The first to sink all enemy ships wins.<br>" +
                "- Ship sizes range from 2-5.<br>" +
                "- Set each ship's orientation as horizontal or vertical.<br>" +
                "- Use coordinates like A0, B4 to place and attack.</html>"
            );
            instructionsLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set custom font and size
            JOptionPane.showMessageDialog(frame, instructionsLabel, "Instructions", JOptionPane.INFORMATION_MESSAGE);
        });
        buttonPanel.add(instructionsButton);
        buttonPanel.add(Box.createVerticalStrut(12)); // Add spacing between buttons

        // Add "Exit Game" button
        JButton exitButton = new JButton("Exit Game");
        exitButton.setFont(buttonFont); // Set larger font
        exitButton.setPreferredSize(buttonSize); // Set fixed size
        exitButton.setMaximumSize(buttonSize); // Ensure consistent size
        exitButton.setAlignmentX(JButton.CENTER_ALIGNMENT); // Center the button horizontally
        exitButton.setBackground(new java.awt.Color(255, 165, 0)); // Set orange background
        exitButton.setForeground(Color.RED); // Set white text color
        exitButton.setFocusPainted(false); // Remove focus border
        exitButton.addActionListener(e -> System.exit(0)); // Exit the application
        buttonPanel.add(exitButton);

        // Add the button panel to the frame
        frame.add(buttonPanel);

        // Ensure the image is behind the buttons
        frame.getContentPane().setComponentZOrder(imageLabel, frame.getContentPane().getComponentCount() - 1);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Show the player selection frame
    public static void disPlayerSelection() {
        // Make the frame
        JFrame frame = new JFrame("Select Players");
        frame.setSize(800,600);
        frame.setLayout(null);

        // Upload PNG image
        ImageIcon orginalIcon = new ImageIcon(GUI.class.getResource("/2.PNG"));
        Image scaledImage = orginalIcon.getImage().getScaledInstance(
            frame.getWidth(),frame.getHeight(),Image.SCALE_SMOOTH
        );
        ImageIcon splashImage = new ImageIcon(scaledImage);

        // Add scale image to JLable
        JLabel imageLabel = new JLabel(splashImage);
        imageLabel.setBounds(0,0,frame.getWidth(),frame.getHeight());
        frame.add(imageLabel);

        // Create transparent button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Align buttons vertically
        buttonPanel.setOpaque(false); // Make the panel transparent
        buttonPanel.setBounds(frame.getWidth() / 2 - 100, frame.getHeight() / 2 - 35, 205, 200); // Center the panel

        // Create button with same style as the splash menu
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        Dimension buttonSize = new Dimension(180, 50);

        // Single Player button
        JButton singlePlayerBtn = new JButton("1 Player");
        singlePlayerBtn.setFont(buttonFont);
        singlePlayerBtn.setPreferredSize(buttonSize);
        singlePlayerBtn.setMaximumSize(buttonSize);
        singlePlayerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        singlePlayerBtn.setBackground(new Color(255,165,0));
        singlePlayerBtn.setForeground(Color.RED);
        singlePlayerBtn.setFocusPainted(false);
        singlePlayerBtn.addActionListener(e -> {
            frame.dispose();
            isAI = true; 
            showFactionSelection();
        });

        // Double Player button
        JButton doublePlayerBtn = new JButton("2 Players");
        doublePlayerBtn.setFont(buttonFont);
        doublePlayerBtn.setPreferredSize(buttonSize);
        doublePlayerBtn.setMaximumSize(buttonSize);
        doublePlayerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        doublePlayerBtn.setBackground(new Color(255,165,0));
        doublePlayerBtn.setForeground(Color.RED);
        doublePlayerBtn.setFocusPainted(false);
        doublePlayerBtn.addActionListener(e -> {
            frame.dispose();
            isAI = false;
            showFactionSelection();
        });

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.setPreferredSize(buttonSize);
        backButton.setMaximumSize(buttonSize);
        backButton.setAlignmentX(Component.TOP_ALIGNMENT);
        backButton.setBackground(new Color(255,165,0));
        backButton.setForeground(Color.RED);
        backButton.setFocusPainted(false);
        // Set position manually (top left corner)
        backButton.setBounds(10,10,100,40);
        backButton.addActionListener(e -> {
            frame.dispose();
            displaySplashMenu();
        });

        // Add button to panel
        buttonPanel.add(singlePlayerBtn);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(doublePlayerBtn);

        // Add component to frame
        frame.add(backButton);
        frame.add(buttonPanel);

        // Ensure the image is behind the buttons
        frame.getContentPane().setComponentZOrder(imageLabel, frame.getContentPane().getComponentCount() - 1);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Show the faction selection frame
    public static void showFactionSelection() {
        // Make the frame
        JFrame frame = new JFrame("Player 1 - Choose Your Island");
        frame.setSize(800,600);
        frame.setLayout(new BorderLayout());

        // Upload PNG image
        ImageIcon orginalIcon = new ImageIcon(GUI.class.getResource("/3.PNG"));
        Image scaledImage = orginalIcon.getImage().getScaledInstance(
            frame.getWidth(),frame.getHeight(),Image.SCALE_SMOOTH
        );
        ImageIcon splashImage = new ImageIcon(scaledImage);

        // Add scaled image to JLable
        JLabel imageLabel = new JLabel(splashImage);
        imageLabel.setLayout(new BorderLayout());
        frame.setContentPane(imageLabel);

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        // Title label
        JLabel titleLabel = new JLabel("Player 1 - Choose Your Island");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(50));

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create button with same style as the splash menu
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        Dimension buttonSize = new Dimension(180, 50);

        // Faction buttons
        JButton isabelleButton = createFactionButton(
            "Isabelle of Bell Haven", buttonFont, buttonSize);
        isabelleButton.addActionListener(e -> {
            playerFaction = "Isabelle of Bell Haven";
            frame.dispose();
            selectOpponentFaction();
        });

        JButton tomButton = createFactionButton(
            "Tom Nook of Nook Isle", buttonFont, buttonSize);
        tomButton.addActionListener(e -> {
            playerFaction = "Tom Nook of Nook Isle";
            frame.dispose();
            selectOpponentFaction();
        });

        JButton neutralButton = createFactionButton(
            "Neutral Village", buttonFont, buttonSize);
        neutralButton.addActionListener(e -> {
            playerFaction = "Neutral Village";
            frame.dispose();
            selectOpponentFaction();
        });

        JButton antiButton = createFactionButton(
            "Anti-Fossil Abolitionist", buttonFont, buttonSize);
        antiButton.addActionListener(e-> {
            playerFaction = "Anti-Fossil Abolitionist";
            frame.dispose();
            selectOpponentFaction();
        });

        // Add buttons to panel
        buttonPanel.add(isabelleButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(tomButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(neutralButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(antiButton);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.setPreferredSize(buttonSize);
        backButton.setMaximumSize(buttonSize);
        backButton.setAlignmentX(Component.TOP_ALIGNMENT);
        backButton.setBackground(new Color(255,165,0));
        backButton.setForeground(Color.RED);
        backButton.setFocusPainted(false);
        // Set position manually (top left corner)
        backButton.setBounds(10,10,100,40);
        backButton.addActionListener(e -> {
            frame.dispose();
            displaySplashMenu();
        });

        // Add components to content panel
        contentPanel.add(buttonPanel);

        // Add components to frame
        imageLabel.setLayout(new BorderLayout());
        imageLabel.add(backButton);
        imageLabel.add(contentPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static JButton createFactionButton(String text, Font font, Dimension size) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(255,165,0));
        button.setForeground(Color.RED);
        button.setFocusPainted(false);
        return button;
    }

    public static void selectOpponentFaction() {
        if(isAI) {
            // for AI opponent randomly select a different faction
            String[] faction = {
                "Isabelle of Bell Haven", "Tom Nook of Nook Isle","Neutral Village","Anti-Fossil Abolitionist"};
            do {
                oppFaction = faction[(int)(Math.random() * faction.length)];
            } while(oppFaction.equals(playerFaction));
            initializeGame();
        } else {
            // For human opponent, show faction selection for player 2 with same 3.PNG background
            JFrame frame = new JFrame("Player 2 - Choose your island!");
            frame.setSize(800,600);
            frame.setLayout(new BorderLayout());
    
            // Upload PNG image
            ImageIcon orginalIcon = new ImageIcon(GUI.class.getResource("/3.PNG"));
            Image scaledImage = orginalIcon.getImage().getScaledInstance(
                frame.getWidth(),frame.getHeight(),Image.SCALE_SMOOTH
            );
            ImageIcon splashImage = new ImageIcon(scaledImage);
    
            // Add scale image to JLable
            JLabel imageLabel = new JLabel(splashImage);
            imageLabel.setLayout(new BorderLayout()); // Add layout to the label
            frame.setContentPane(imageLabel);
    
            // Main content panel
            JPanel contentPanel = new JPanel();
            contentPanel.setOpaque(false);
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
    
            // Title label
            JLabel titleLabel = new JLabel("Player 2 - Choose Your Island");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(titleLabel);
            contentPanel.add(Box.createVerticalStrut(50));
    
            // Create button panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setOpaque(false);
            buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
            // Create button with same style as the splash menu
            Font buttonFont = new Font("Arial", Font.BOLD, 18);
            Dimension buttonSize = new Dimension(180, 50);
    
            // Faction buttons
            JButton isabelleButton = createFactionButton(
                "Isabelle of Bell Haven", buttonFont, buttonSize);
            isabelleButton.addActionListener(e -> {
                oppFaction = "Isabelle of Bell Haven";
                frame.dispose();
                initializeGame();
            });
    
            JButton tomButton = createFactionButton(
                "Tom Nook of Nook Isle", buttonFont, buttonSize);
            tomButton.addActionListener(e -> {
                oppFaction = "Tom Nook of Nook Isle";
                frame.dispose();
                initializeGame();
            });
    
            JButton neutralButton = createFactionButton(
                "Neutral Village", buttonFont, buttonSize);
            neutralButton.addActionListener(e -> {
                oppFaction = "Neutral Village";
                frame.dispose();
                initializeGame();
            });
    
            JButton antiButton = createFactionButton(
                "Anti-Fossil Abolitionist", buttonFont, buttonSize);
            antiButton.addActionListener(e-> {
                oppFaction = "Anti-Fossil Abolitionist";
                frame.dispose();
                initializeGame();
            });
    
            // Add buttons to panel
            buttonPanel.add(isabelleButton);
            buttonPanel.add(Box.createVerticalStrut(15));
            buttonPanel.add(tomButton);
            buttonPanel.add(Box.createVerticalStrut(15));
            buttonPanel.add(neutralButton);
            buttonPanel.add(Box.createVerticalStrut(15));
            buttonPanel.add(antiButton);
    
            // Back button
            JButton backButton = new JButton("Back");
            backButton.setFont(buttonFont);
            backButton.setPreferredSize(buttonSize);
            backButton.setMaximumSize(buttonSize);
            backButton.setAlignmentX(Component.TOP_ALIGNMENT);
            backButton.setBackground(new Color(255,165,0));
            backButton.setForeground(Color.RED);
            backButton.setFocusPainted(false);
            // Set position manually (top left corner)
            backButton.setBounds(10,10,100,40);
            backButton.addActionListener(e -> {
                frame.dispose();
                displaySplashMenu();
            });

            // Add components to content panel
            contentPanel.add(buttonPanel);
    
            // Add components to frame
            imageLabel.setLayout(new BorderLayout());
            imageLabel.add(backButton);
            imageLabel.add(contentPanel, BorderLayout.CENTER);
    
            frame.setVisible(true);
        }
    }

    private static void initializeGame() { 
        gameFrame = new JFrame("Animal Crossing: Last island standing - " + 
                                  playerFaction + " vs " + (isAI ? "Computer (" + oppFaction + ")" : oppFaction));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(800,600);
        gameFrame.setLayout(new BorderLayout());

        // Initialize board
        playerBoard = new Board(10,10);
        oppBoard = new Board(10,10);

        // Update player's name with their chosen island
        currentPlayer = new Player("Player 1 (" + playerFaction + ")");
        if(isAI) {
            opponent = new AIPlayer("Computer ("+ oppFaction +")", false);
            ((AIPlayer)opponent).placeShips(oppBoard, 3); // AI immediately place ship on the board
        }else {
            opponent = new Player ("Player 2 ("+ oppFaction + ")");
        }

        setupPlacement();
        gameFrame.setVisible(true);
    }

    // Set up ship placement phase
    private static void setupPlacement() {
        gamePanel = new JPanel(new BorderLayout());

        // Create board panel
        JPanel boardPanel = new JPanel(new GridLayout(1,2,20,20));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // Player board set up
        JPanel playerBoardPanel = createBoardPanel(true);
        boardPanel.add(playerBoardPanel);

        // Opponent board set up (hidden during placement)
        JPanel oppBoardPanel = createBoardPanel(false);
        oppBoardPanel.setVisible(false);
        boardPanel.add(oppBoardPanel);

        // Control panel with buttons
        JPanel controlPanel = new JPanel();
        rotateButton = new JButton("Rotate Ship");
        rotateButton.addActionListener(e -> {
            horizontalPlacement = !horizontalPlacement;
            clearHighlights();
            if (!selectedShipCoordinates.isEmpty()) {
                // Try to place the rotated ship
                Coordinate firstCoord = selectedShipCoordinates.get(0);
                new PlacementButtonListener(firstCoord.getRow(), firstCoord.getCol()).actionPerformed(null);
            }
        });
        
        confirmButton = new JButton("Confirm Placement");
        confirmButton.setEnabled(false);
        confirmButton.addActionListener(e -> confirmPlacement());
        
        statusLabel = new JLabel("Place your 2-segment ship");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        controlPanel.add(rotateButton);
        controlPanel.add(confirmButton);

        // Add components to main panel
        gamePanel.add(boardPanel, BorderLayout.CENTER);
        gamePanel.add(controlPanel, BorderLayout.NORTH);
        gamePanel.add(statusLabel, BorderLayout.SOUTH);

        // Add panel to frame
        gameFrame.add(gamePanel);
        gameFrame.revalidate();
        gameFrame.repaint();
    }

    private static JPanel createBoardPanel(boolean isPlayerBoard) {
        JPanel boardPanel = new JPanel(new GridLayout(10,10,1,1));
        boardPanel.setBorder(BorderFactory.createTitledBorder(
            isPlayerBoard ? "Your Ship (" + playerFaction +")" : 
            (isAI ? "Computer ("+ oppFaction + ")" : oppFaction + "'s Waters")));

        JButton[][] buttons = new JButton[10][10];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(40, 40));
                button.setBackground(WATER_COLOR);
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                
                if (isPlayerBoard) {
                    button.addActionListener(new PlacementButtonListener(row, col));
                }
                
                boardPanel.add(button);
                buttons[row][col] = button;
            }
        }
        
        if (isPlayerBoard) {
            playerButtons = buttons;
        } else {
            oppButtons = buttons;
        }
        return boardPanel;
    }

    private static class PlacementButtonListener implements ActionListener {
        private int row;
        private int col;

        public PlacementButtonListener(int row, int col) {
            this.col = col;
            this.row = row;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            clearHighlights();
            
            List<Coordinate> coords = new ArrayList<>();
            boolean valid = true;
            
            for (int i = 0; i < shipSize; i++) {
                int r = row + (horizontalPlacement ? 0 : i);
                int c = col + (horizontalPlacement ? i : 0);
                
                if (r >= 10 || c >= 10 || playerBoard.getFieldStatus(r, c) != 0) {
                    valid = false;
                    break;
                }
                coords.add(new Coordinate(r, c));
            }
            
            if (valid) {
                 // Check if clicking on existing ship to remove it
                boolean isRemoving = true;
                for (Coordinate coord : coords) {
                    if (playerButtons[coord.getRow()][coord.getCol()].getBackground() != SHIP_COLOR) {
                        isRemoving = false;
                        break;
                    }
                }
                if (isRemoving) {
                    // Remove ship
                    for (Coordinate coord : coords) {
                        playerButtons[coord.getRow()][coord.getCol()].setBackground(WATER_COLOR);
                    }
                    confirmButton.setEnabled(false);
                } else {
                    // Place ship
                    for (Coordinate coord : coords) {
                        playerButtons[coord.getRow()][coord.getCol()].setBackground(SHIP_COLOR);
                    }
                    confirmButton.setEnabled(true);
                }
                selectedShipCoordinates = coords;
                statusLabel.setText("Placement looks good! Confirm when ready.");
            } else {
                // Show invalid placement
                for (Coordinate coord : coords) {
                    if (coord.getRow() < 10 && coord.getCol() < 10) {
                        playerButtons[coord.getRow()][coord.getCol()].setBackground(INVALID_COLOR);
                    }
                }
                statusLabel.setText("Invalid placement! Try another position.");
            }
        }
    }

    // Clear placement highlights
    private static void clearHighlights() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Color current = playerButtons[row][col].getBackground();
                if (current.equals(INVALID_COLOR) || current.equals(SHIP_COLOR)) {
                    playerButtons[row][col].setBackground(WATER_COLOR);
                }
            }
        }
        selectedShipCoordinates.clear(); // Clear the selected coordinates
        confirmButton.setEnabled(false); // Disable the confirm button
    }

    // Confirm ship placement
    private static void confirmPlacement() {
        if (selectedShipCoordinates.size() == shipSize) {
            Ship ship = new Ship(playerFaction + " Ship " + (shipsPlaced + 1), selectedShipCoordinates);
            if (playerBoard.canPlaceShip(ship)) {
                playerBoard.placeShip(ship);
                shipsPlaced++;
                
                // Mark all confirmed ship positions as gray
                for (Coordinate coord : selectedShipCoordinates) {
                    playerButtons[coord.getRow()][coord.getCol()].setBackground(SHIP_COLOR);
                }
                
                if (shipsPlaced < 3) { // Place 3 ships (sizes 2, 3, 4)
                    shipSize++;
                    statusLabel.setText("Place your " + shipSize + "-segment ship");
                    confirmButton.setEnabled(false);
                    selectedShipCoordinates.clear();
                } else {
                    // If human opponent, switch to their placement phase
                    if (!isAI) {
                        // switchToOpponentPlacement();
                    } else { 
                        startBattlePhase();
                    }
                }
            }
        }
    }

    private static void switchToOpponentPlacement() {
        // Save player 1's board and reset for player 2
        Board tempBoard = playerBoard;
        playerBoard = oppBoard;
        oppBoard = tempBoard;
        
        // Reset placement variables
        shipSize = 2;
        shipsPlaced = 0;
        selectedShipCoordinates.clear();
        
        // Update UI
        gamePanel.removeAll();
        JPanel boardPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Player board (now showing player 2's placement)
        JPanel playerBoardPanel = createBoardPanel(true);
        boardPanel.add(playerBoardPanel);

        // Hide opponent board (which is now player 1's board)
        JPanel oppBoardPanel = createBoardPanel(false);
        oppBoardPanel.setVisible(false);
        boardPanel.add(oppBoardPanel);

        // Update controls
        statusLabel.setText("Player 2 - Place your 2-segment ship");
        confirmButton.setEnabled(false);
        
        gamePanel.add(boardPanel, BorderLayout.CENTER);
        gamePanel.add(rotateButton, BorderLayout.NORTH);
        gamePanel.add(confirmButton, BorderLayout.NORTH);
        gamePanel.add(statusLabel, BorderLayout.SOUTH);
        
        gameFrame.revalidate();
        gameFrame.repaint();
    }
    
    public static void startBattlePhase() {
        gamePanel.removeAll();

        // Create boards panel
        JPanel boardsPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        boardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Player board (show ships)
        JPanel playerBoardPanel = new JPanel(new GridLayout(10, 10, 1, 1));
        playerBoardPanel.setBorder(BorderFactory.createTitledBorder("Your Fleet (" + playerFaction + ")"));
        
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JLabel label = new JLabel();
                label.setOpaque(true);
                label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                label.setPreferredSize(new Dimension(40, 40));
                
                int status = playerBoard.getFieldStatus(row, col);
                if (status == 0) {
                    label.setBackground(Color.BLUE); // Water
                } else if (status == 1) {
                    label.setBackground(Color.CYAN); // Miss
                } else if (status == 2) {
                    label.setBackground(Color.GRAY); // Ship (not hit)
                } else if (status == 3) {
                    label.setBackground(Color.RED); // Hit
                }
                
                playerBoardPanel.add(label);
            }
        }
        // Enemy board (clickable for attacks)
        JPanel enemyBoardPanel = new JPanel(new GridLayout(10, 10, 1, 1));
        enemyBoardPanel.setBorder(BorderFactory.createTitledBorder(
            isAI ? "Computer (" + oppFaction + ")" : oppFaction + "'s Waters"));
        
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(40, 40));
                button.setBackground(Color.BLUE);
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                
                int status = oppBoard.getFieldStatus(row, col);
                if (status == 1) {
                    button.setBackground(Color.CYAN); // Miss
                    button.setEnabled(false);
                } else if (status == 3) {
                    button.setBackground(Color.RED); // Hit
                    button.setEnabled(false);
                } else {
                    button.addActionListener(new AttackButtonListener(row, col));
                }
                
                enemyBoardPanel.add(button);
                oppButtons[row][col] = button;
            }
        }
        
        boardsPanel.add(playerBoardPanel);
        boardsPanel.add(enemyBoardPanel);
        
        statusLabel = new JLabel("Your turn. Attack the enemy fleet!");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        gamePanel.add(boardsPanel, BorderLayout.CENTER);
        gamePanel.add(statusLabel, BorderLayout.SOUTH);
        
        gameFrame.revalidate();
        gameFrame.repaint();
    }

    /**
     * Handles attack clicks
     */
    private static class AttackButtonListener implements ActionListener {
        private int row;
        private int col;
        
        public AttackButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Coordinate target = new Coordinate(row, col);
            boolean isHit = oppBoard.attack(target);
            
            JButton button = (JButton)e.getSource();
            button.setEnabled(false);
            button.setBackground(isHit ? Color.RED : Color.CYAN);
            
            if (isHit) {
                currentPlayer.setScore(currentPlayer.getScore() + 1);
                String sunkShip = oppBoard.shipNameIfKill(target);
                if (sunkShip != null) {
                    currentPlayer.setScore(currentPlayer.getScore() + 2);
                    statusLabel.setText("Hit! You sunk the " + sunkShip + "!");
                } else {
                    statusLabel.setText("Hit! Choose another target.");
                }
                
                if (oppBoard.isGameOver()) {
                    showGameOver(currentPlayer.getName() + " wins! " + playerFaction + " prevails!");
                    return;
                }
            } else {
                statusLabel.setText("Miss! " + (isAI ? "Computer's turn." : "Player 2's turn."));
            }
            
            if (isAI) {
                aiTurn();
            } else {
                // For 2-player mode, switch turns here
                switchTurns();
            }
        }
    }

    /**
     * AI's turn to attack
     */
    private static void aiTurn() {
        Coordinate target = ((AIPlayer)opponent).chooseAttack(playerBoard);
        boolean isHit = playerBoard.attack(target);
        
        int row = target.getRow();
        int col = target.getCol();
        
        if (isHit) {
            opponent.setScore(opponent.getScore() + 1);
            String sunkShip = playerBoard.shipNameIfKill(target);
            if (sunkShip != null) {
                opponent.setScore(opponent.getScore() + 2);
                statusLabel.setText("AI hit your " + sunkShip + "! Your turn.");
            } else {
                statusLabel.setText("AI hit your ship! Your turn.");
            }
            
            if (playerBoard.isGameOver()) {
                showGameOver(opponent.getName() + " wins! " + oppFaction + " prevails!");
                return;
            }
            
            ((AIPlayer)opponent).handleHit(target, playerBoard);
        } else {
            statusLabel.setText("AI missed! Your turn.");
        }
    }

    /**
     * Shows game over screen
     */
    private static void showGameOver(String message) {
        JPanel panel = new JPanel(new BorderLayout());
        
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" +
            "<h1>" + message + "</h1>" +
            "<p>Final Score: " + currentPlayer.getScore() + "</p></div></html>");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton playAgainBtn = new JButton("Play Again");
        playAgainBtn.addActionListener(e -> {
            gameFrame.dispose();
            displaySplashMenu();
        });
        
        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> System.exit(0));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playAgainBtn);
        buttonPanel.add(exitBtn);
        
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        JOptionPane.showMessageDialog(gameFrame, panel, "Game Over", JOptionPane.PLAIN_MESSAGE);
        gameFrame.dispose();
    }
    
    private static void switchTurns() {
        // Swap current player and opponent
        Player tempPlayer = currentPlayer;
        currentPlayer = opponent;
        opponent = tempPlayer;
        
        // Swap boards
        Board tempBoard = playerBoard;
        playerBoard = oppBoard;
        oppBoard = tempBoard;
        
        // Swap button references
        JButton[][] tempButtons = playerButtons;
        playerButtons = oppButtons;
        oppButtons = tempButtons;
        
        // Update UI
        statusLabel.setText(currentPlayer.getName() + "'s turn to attack!");
        
        // Refresh the boards display
        refreshBoards();
        
        // Show message dialog to pass control
        JOptionPane.showMessageDialog(gameFrame, 
            "Pass the device to " + currentPlayer.getName(), 
            "Switch Turns", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Add this helper method
    private static void refreshBoards() {
        // Refresh player board (showing current player's own ships)
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                int status = playerBoard.getFieldStatus(row, col);
                Color color = Color.BLUE; // Water
                if (status == 1) color = Color.CYAN; // Miss
                else if (status == 2) color = Color.GRAY; // Ship
                else if (status == 3) color = Color.RED; // Hit
                playerButtons[row][col].setBackground(color);
                playerButtons[row][col].setEnabled(false); // Disable clicking on own board
            }
        }
        
        // Refresh enemy board (showing attack results)
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                int status = oppBoard.getFieldStatus(row, col);
                if (status == 1 || status == 3) { // Show hits/misses
                    oppButtons[row][col].setBackground(status == 1 ? Color.CYAN : Color.RED);
                    oppButtons[row][col].setEnabled(false);
                } else {
                    oppButtons[row][col].setBackground(Color.BLUE);
                    oppButtons[row][col].setEnabled(true); // Allow attacks on empty spots
                }
            }
        }
    }
}