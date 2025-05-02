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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

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
    private static int shipsPlaced = 0; // Count of ships placed
    private static int islandsPlaced = 0; // Count of islands placed
    private static boolean horizontalPlacement = true;
    private static JButton rotateButton;
    private static JButton confirmButton;
    private static JLabel statusLabel;
    private static JFrame gameFrame;
    private static JPanel gamePanel;
    private static List<Coordinate> selectedShipCoordinates = new ArrayList<>();
    // Add this field to track the selected ship size dynamically
    private static JComboBox<Integer> sizeSelector;
    // Add a field to track whether the last attack was a hit
    private static boolean lastAttackHit = false;
    private static String playerName;
    private static String opponentName;
    private static boolean isPlacingIsland = false; // Flag to track if we're placing an Island

    // Color constants
    private static final Color WATER_COLOR = new Color(0, 105, 148); // Dark blue water
    private static final Color SHIP_COLOR = new Color(80, 80, 80); // Dark gray for ships
    private static final Color INVALID_COLOR = new Color(255, 100, 100); // Light red for invalid

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

        // Add "Story" button
        JButton storyButton = new JButton("Story");
        storyButton.setFont(buttonFont); // Set larger font
        storyButton.setPreferredSize(buttonSize); // Set fixed size
        storyButton.setMaximumSize(buttonSize); // Ensure consistent size
        storyButton.setAlignmentX(JButton.CENTER_ALIGNMENT); // Center the button horizontally
        storyButton.setBackground(new java.awt.Color(255, 165, 0)); // Set orange background
        storyButton.setForeground(Color.RED); // Set white text color
        storyButton.setFocusPainted(false); // Remove focus border
        storyButton.addActionListener(e -> {
            // Create a new JFrame for the story
            JFrame storyFrame = new JFrame("Story");
            storyFrame.setSize(400, 600);
            storyFrame.setLayout(new BorderLayout());
            storyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Create a JLabel with the story text
            JLabel storyLabel = new JLabel(
                "<html><div style='text-align: west;'>" +
                "The discovery of \"Fossilium,\" a rare and powerful mineral, disrupts the peaceful life of Animal Crossing, igniting a fierce battle for control over its transformative capabilities.<br><br>" +
                "Tom Nook, driven by ambition, commands a high-tech naval fleet to harness Fossilium and build luxurious resorts, while Isabelle, once a gentle bureaucrat, leads a defensive campaign to prevent the mineral’s misuse.<br><br>" +
                "As battleships clash and alliances crumble, a neutral faction arises, determined to destroy Fossilium and end the war, regardless of the cost.<br><br>" +
                "The islands, scared by missiles and destruction, force the villagers to grapple with the devastating consequences of their greed and the fragile bonds that once united their world." +
                "</div></html>"
            );
            storyLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font and size
            storyLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Wrap the storyLabel in a JPanel
            JPanel storyPanel = new JPanel(new BorderLayout());
            storyPanel.add(storyLabel, BorderLayout.CENTER);
            storyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the text

            // Add the storyPanel to the center of the frame
            storyFrame.add(storyPanel, BorderLayout.CENTER);

            JButton okButton = new JButton("OK");
            okButton.setFont(new Font("Arial", Font.BOLD, 14));
            okButton.addActionListener(closeEvent -> storyFrame.dispose());
            JPanel buttonPanel1 = new JPanel();
            buttonPanel1.add(okButton);
            storyFrame.add(buttonPanel1, BorderLayout.SOUTH);


            // Center the frame on the screen
            storyFrame.setLocationRelativeTo(frame);

            // Make the frame visible
            storyFrame.setVisible(true);
        });
        buttonPanel.add(storyButton);
        buttonPanel.add(Box.createVerticalStrut(12), BorderLayout.CENTER); // Add spacing between buttons

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
        ImageIcon orginalIcon = new ImageIcon(GUI.class.getResource("/1.PNG"));
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

        // Add button to panel
        buttonPanel.add(singlePlayerBtn);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(doublePlayerBtn);

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
            displaySplashMenu();// Go back to Player Selection Menu
        });

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
        // Prompt for player name
        playerName = JOptionPane.showInputDialog(null, "Enter Your Name:", "Player Name", JOptionPane.PLAIN_MESSAGE);
        if(playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player 1"; // Default name if input is empty
        }

        // Make the frame
        JFrame frame = new JFrame(playerName + " - Choose Your Faction");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Upload PNG image
        ImageIcon originalIcon = new ImageIcon(GUI.class.getResource("/1.PNG"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(
            frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH
        );
        ImageIcon splashImage = new ImageIcon(scaledImage);

        // Add scaled image to JLabel
        JLabel imageLabel = new JLabel(splashImage);
        imageLabel.setLayout(new BorderLayout());
        frame.setContentPane(imageLabel);

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(200, 0, 0, 20)); // Adjust padding to move buttons higher

        // Title label
        JLabel titleLabel = new JLabel(playerName + " - Choose Your Faction");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28)); // Larger font for the title
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add the title panel to the content panel
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20)); // Add spacing below the title

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create button with previous style
        Font buttonFont = new Font("Arial", Font.BOLD, 18); // Previous font size
        Dimension buttonSize = new Dimension(500, 60 ); // Previous button size

        // Faction buttons with previous size and spacing
        JButton isabelleButton = createFactionButton(
            "Isabelle of Bell Haven", buttonFont, buttonSize, new Color(255, 165, 0)); // Orange
        isabelleButton.addActionListener(e -> {
            playerFaction = "Isabelle of Bell Haven";
            frame.dispose();
            selectOpponentFaction();
        });

        JButton tomButton = createFactionButton(
            "Tom Nook of Nook Isle", buttonFont, buttonSize, new Color(0, 105, 255)); // Blue
        tomButton.addActionListener(e -> {
            playerFaction = "Tom Nook of Nook Isle";
            frame.dispose();
            selectOpponentFaction();
        });

        JButton neutralButton = createFactionButton(
            "Neutral Village", buttonFont, buttonSize, new Color(0, 200, 0)); // Green
        neutralButton.addActionListener(e -> {
            playerFaction = "Neutral Village";
            frame.dispose();
            selectOpponentFaction();
        });

        JButton antiButton = createFactionButton(
            "Anti-Fossil Abolitionist", buttonFont, buttonSize, new Color(128, 128, 128)); // Grey
        antiButton.addActionListener(e -> {
            playerFaction = "Anti-Fossil Abolitionist";
            frame.dispose();
            selectOpponentFaction();
        });

        // Add buttons to panel
        buttonPanel.add(isabelleButton);
        buttonPanel.add(Box.createVerticalStrut(12)); // Previous spacing between buttons
        buttonPanel.add(tomButton);
        buttonPanel.add(Box.createVerticalStrut(12));
        buttonPanel.add(neutralButton);
        buttonPanel.add(Box.createVerticalStrut(12));
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

        // Add the Back button to the frame
        imageLabel.add(backButton);

        // Add components to content panel
        contentPanel.add(buttonPanel);

        // Add components to frame
        imageLabel.setLayout(new BorderLayout());
        imageLabel.add(contentPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static JButton createFactionButton(String text, Font font, Dimension size, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(backgroundColor); // Set the background color
        button.setForeground(Color.RED); // Keep the text color consistent
        button.setFocusPainted(false);
        return button;
    }

    public static void selectOpponentFaction() {
        if (isAI) {
            // For AI opponent, randomly select a different faction
            String[] factions = {
                "Isabelle of Bell Haven", "Tom Nook of Nook Isle", "Neutral Village", "Anti-Fossil Abolitionist"
            };
            do {
                oppFaction = factions[(int) (Math.random() * factions.length)];
            } while (oppFaction.equals(playerFaction));
            initializeGame();
        } else {
            // Prompt for Player 2 name
            opponentName = JOptionPane.showInputDialog(null, "Player 2, Enter Your Name:", "Player Name", JOptionPane.PLAIN_MESSAGE);
            if (opponentName == null || opponentName.trim().isEmpty()) {
                opponentName = "Player 2"; // Default name if input is empty
            }

            // For human opponent, show faction selection for Player 2
            JFrame frame = new JFrame(opponentName + " - Choose Your Faction");
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());

            // Upload PNG image
            ImageIcon originalIcon = new ImageIcon(GUI.class.getResource("/1.PNG"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(
                frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH
            );
            ImageIcon splashImage = new ImageIcon(scaledImage);

            // Add scaled image to JLabel
            JLabel imageLabel = new JLabel(splashImage);
            imageLabel.setLayout(new BorderLayout());
            frame.setContentPane(imageLabel);

            // Main content panel
            JPanel contentPanel = new JPanel();
            contentPanel.setOpaque(false);
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(200, 0, 0, 20)); // Adjust padding to move buttons lower

            // Title label
            JLabel titleLabel = new JLabel(opponentName + " - Choose Your Faction");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            // Add the title label to the content panel
            contentPanel.add(titleLabel);
            contentPanel.add(Box.createVerticalStrut(20)); // Add spacing below the title

            // Create button panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setOpaque(false);
            buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Create button with same style as Player 1
            Font buttonFont = new Font("Arial", Font.BOLD, 18);
            Dimension buttonSize = new Dimension(500, 60);

            // Faction buttons
            JButton isabelleButton = createFactionButton(
                "Isabelle of Bell Haven", buttonFont, buttonSize, new Color(255, 165, 0));
            isabelleButton.addActionListener(e -> {
                oppFaction = "Isabelle of Bell Haven";
                frame.dispose();
                initializeGame();
            });

            JButton tomButton = createFactionButton(
                "Tom Nook of Nook Isle", buttonFont, buttonSize, new Color(0, 105, 255));
            tomButton.addActionListener(e -> {
                oppFaction = "Tom Nook of Nook Isle";
                frame.dispose();
                initializeGame();
            });

            JButton neutralButton = createFactionButton(
                "Neutral Village", buttonFont, buttonSize, new Color(0, 200, 0));
            neutralButton.addActionListener(e -> {
                oppFaction = "Neutral Village";
                frame.dispose();
                initializeGame();
            });

            JButton antiButton = createFactionButton(
                "Anti-Fossil Abolitionist", buttonFont, buttonSize, new Color(128, 128, 128));
            antiButton.addActionListener(e -> {
                oppFaction = "Anti-Fossil Abolitionist";
                frame.dispose();
                initializeGame();
            });

            // Add buttons to panel
            buttonPanel.add(isabelleButton);
            buttonPanel.add(Box.createVerticalStrut(12));
            buttonPanel.add(tomButton);
            buttonPanel.add(Box.createVerticalStrut(12));
            buttonPanel.add(neutralButton);
            buttonPanel.add(Box.createVerticalStrut(12));
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
                showFactionSelection();
            });

            // Add the Back button to the frame
            imageLabel.add(backButton);

            // Add components to content panel
            contentPanel.add(buttonPanel);

            // Add components to frame
            imageLabel.setLayout(new BorderLayout());
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

        // Initialize button arrays
        playerButtons = new JButton[10][10];
        oppButtons = new JButton[10][10];

        // Update player's name with their chosen island
        currentPlayer = new Player(playerName + " (" + playerFaction + ")");
        if(isAI) {
            opponent = new AIPlayer("Computer ("+ oppFaction +")", false);
            ((AIPlayer)opponent).placeShips(oppBoard, 3); // AI immediately place ship on the board
        }else {
            opponent = new Player (opponentName + " ("+ oppFaction + ")");
        }

        setupPlacement();
        gameFrame.setVisible(true);
    }

    // Set up ship placement phase
    private static void setupPlacement() {
        gamePanel = new JPanel(new BorderLayout());

        // Add background GIF first (this will be at the bottom)
        JLabel backgroundLabel = new JLabel(new ImageIcon(GUI.class.getResource("/bg.gif")));
        backgroundLabel.setLayout(new BorderLayout()); // Use layout for the background
        gamePanel.add(backgroundLabel, BorderLayout.CENTER);

        // Create a content panel that will hold everything else
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false); // Make it transparent

        // Create board panel
        JPanel boardPanel = new JPanel(new GridLayout(1,2,20,20));
        boardPanel.setOpaque(false); // Make the board transparent to show the GIF background
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // Player board set up
        JPanel playerBoardPanel = createBoardPanel(true);
        boardPanel.add(playerBoardPanel);

        // Opponent board set up (hidden during placement)
        JPanel oppBoardPanel = createBoardPanel(false);
        oppBoardPanel.setVisible(false);
        boardPanel.add(oppBoardPanel);

        // Add board to content panel
        contentPanel.add(boardPanel, BorderLayout.CENTER);

        // Control panel with buttons
        JPanel controlPanel = new JPanel();
        // controlPanel.setBounds(50, 470, 700, 50); // Adjust size and position
        controlPanel.setOpaque(false);

        // Dropdown for ship size selection
        sizeSelector = new JComboBox<>(new Integer[]{2,3,4,5}); // Size 2-5
        sizeSelector.setSelectedIndex(0); // Default to size 2
        sizeSelector.addActionListener(e -> {
            shipSize = (int) sizeSelector.getSelectedItem(); // Update ship size
            clearHighlights();
            if (!selectedShipCoordinates.isEmpty()) {
                // Try to place the ship of the new size
                Coordinate firstCoord = selectedShipCoordinates.get(0);
                new PlacementButtonListener(firstCoord.getRow(), firstCoord.getCol()).actionPerformed(null);
            }
        });
        controlPanel.add(new JLabel ("Select Ship Size:"));
        controlPanel.add(sizeSelector);

        // Dropdown for selecting placement type
        JComboBox<String> placementTypeSelector = new JComboBox<>(new String[]{"Ship", "Island"}); 
        placementTypeSelector.addActionListener(e -> {
            isPlacingIsland = placementTypeSelector.getSelectedItem().equals("Island");
            shipSize = isPlacingIsland ? 2 : (int) sizeSelector.getSelectedItem(); // Set ship size to 2 for island
            clearHighlights();
            statusLabel.setText(isPlacingIsland ? "Place your 2x2 Island" : "Place your " + shipSize + "-segment ship");
        }); 

        controlPanel.add(new JLabel("Place: "));
        controlPanel.add(placementTypeSelector);

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
        
        statusLabel = new JLabel("Place your " + shipSize + "-segment ship");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        controlPanel.add(rotateButton);
        controlPanel.add(confirmButton);

        // Add components to content panel
        contentPanel.add(controlPanel, BorderLayout.NORTH);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(statusLabel, BorderLayout.SOUTH);

        // Add the content panel to the background label
        backgroundLabel.add(contentPanel);
        
        // Set game panel to frame
        gameFrame.setContentPane(gamePanel);
        gameFrame.revalidate();
        gameFrame.repaint();
    }

    private static JPanel createBoardPanel(boolean isPlayerBoard) {
        JPanel boardPanel = new JPanel(new GridLayout(10,10,1,1));
        boardPanel.setBorder(BorderFactory.createTitledBorder(
            isPlayerBoard ? "Your Ship (" + currentPlayer.getName() +")" : 
            (isAI ? "Computer ("+ oppFaction + ")" : currentPlayer.getName() + "'s Waters")));

        JButton[][] buttons = new JButton[10][10];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JButton button = new JButton();
                button.setOpaque(true);
                button.setPreferredSize(new Dimension(40, 40));
                button.setBackground(WATER_COLOR);
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                
                // Set initial color based on board status
                int status = isPlayerBoard ? playerBoard.getFieldStatus(row, col) : oppBoard.getFieldStatus(row, col);
                if (status == 0) {
                    button.setBackground(WATER_COLOR); // Water
                } else if (status == 2) {
                    button.setBackground(SHIP_COLOR); // Ship
                } else if (status == 4) {
                    button.setBackground(Color.YELLOW); // Island
                } else {
                    button.setBackground(WATER_COLOR); // Default
                } 

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
            // If placing an island
            if(isPlacingIsland) {
                List<Coordinate> islandCoords = new ArrayList<>();
                boolean valid = true;

                // Calculate island coordinates 2x2
                for(int r = row; r < row + 2; r++) {
                    for(int c = col; c < col + 2; c++) {
                        // Check bounds and if the spot is already occupied by a confirmed ship
                        if(r >= 10 || c >= 10 || playerBoard.getFieldStatus(r, c) != 0) {
                            valid = false;
                            break;
                        }
                        islandCoords.add(new Coordinate(r, c));
                    }
                }
                
                if(valid) {
                    // Clear previous highlights
                    for (Coordinate coord : selectedShipCoordinates) {
                        playerButtons[coord.getRow()][coord.getCol()].setBackground(WATER_COLOR);
                        playerBoard.setFieldStatus(coord.getRow(), coord.getCol(), 0); // Reset to water
                    }

                    // Highlight the new island placement
                    for(Coordinate coord : islandCoords) {
                        playerButtons[coord.getRow()][coord.getCol()].setBackground(Color.YELLOW);
                    }

                    // Update the selected coordinates
                    selectedShipCoordinates.clear();
                    selectedShipCoordinates.addAll(islandCoords);
                    
                    // Set the field status to 2 (confirmed ship)
                    // selectedShipCoordinates = islandCoords;
                    confirmButton.setEnabled(true);
                    statusLabel.setText("Island placement looks good! Confirm when ready.");
                } else {
                    //Highlight invalid placement
                    for(Coordinate coord : islandCoords) {
                        if(coord.getRow() < 10 && coord.getCol() < 10 && 
                            playerBoard.getFieldStatus(coord.getRow(), coord.getCol()) != 2) {
                            playerButtons[coord.getRow()][coord.getCol()].setBackground(INVALID_COLOR);
                        }
                    }
                    statusLabel.setText("Invalid island placement! Try another position.");
                }
                return;
            }

            // Check if we're clicking on an already placed ship
            if (playerButtons[row][col].getBackground() == SHIP_COLOR) {
                // Check if this is part of a confirmed ship
                if (playerBoard.getFieldStatus(row, col) == 2) {
                    statusLabel.setText("Cannot modify confirmed ships! Place a new ship.");
                    return;
                }
            }
            
            List<Coordinate> coords = new ArrayList<>();
            boolean valid = true;
            
            for (int i = 0; i < shipSize; i++) {
                int r = row + (horizontalPlacement ? 0 : i);
                int c = col + (horizontalPlacement ? i : 0);
                
                // Check bounds and if the spot is already occupied by a confirmed ship
                if (r >= 10 || c >= 10 || (playerBoard.getFieldStatus(r, c) != 0 && playerBoard.getFieldStatus(r, c) != 4)) {
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
                    // Remove temporary ship
                    for (Coordinate coord : coords) {
                        playerButtons[coord.getRow()][coord.getCol()].setBackground(WATER_COLOR);
                    }
                    confirmButton.setEnabled(false);
                } else {
                    // Show temporary placement
                    for (Coordinate coord : coords) {
                        if (playerBoard.getFieldStatus(coord.getRow(), coord.getCol()) != 2) {
                            playerButtons[coord.getRow()][coord.getCol()].setBackground(SHIP_COLOR);
                        }
                    }
                    confirmButton.setEnabled(true);
                }

                selectedShipCoordinates = coords;
                statusLabel.setText("Placement looks good! Confirm when ready.");
            } else {
                // Show invalid placement
                for (Coordinate coord : coords) {
                    if (coord.getRow() < 10 && coord.getCol() < 10 && 
                        playerBoard.getFieldStatus(coord.getRow(), coord.getCol()) != 2) {
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
                // Only clear highlights that aren't confirmed ships or islands
                if (playerBoard.getFieldStatus(row, col) != 2 && playerBoard.getFieldStatus(row, col) != 4) {
                    Color current = playerButtons[row][col].getBackground();
                    if (current.equals(INVALID_COLOR) || current.equals(Color.YELLOW)) {
                        playerButtons[row][col].setBackground(WATER_COLOR);
                    } else if (current.equals(SHIP_COLOR)) {
                        // Only clear ship color if it's not a confirmed ship
                        playerButtons[row][col].setBackground(WATER_COLOR);
                    }
                }
            }
        }
        selectedShipCoordinates.clear(); // Clear the selected coordinates
        confirmButton.setEnabled(false); // Disable the confirm button
    }

    // Confirm ship and island placement
    private static void confirmPlacement() {
        if (isPlacingIsland) {
            // Confirm Island placement
            if (islandsPlaced >= 1) {
                statusLabel.setText("You can only place one island! Confirm or remove the existing one.");
                return;
            }

            if (selectedShipCoordinates.size() == 4) {
                // Validate that the island can be placed
                Island island = new Island(playerFaction + " Island", selectedShipCoordinates);
                if (playerBoard.canPlaceIsland(island)) {
                    playerBoard.placeIsland(island);
                    for (Coordinate coord : selectedShipCoordinates) {
                        playerButtons[coord.getRow()][coord.getCol()].setBackground(Color.YELLOW); // Mark as island
                        playerBoard.setFieldStatus(coord.getRow(), coord.getCol(), 4); // Set as confirmed island
                    }
                    islandsPlaced++; // Increment the island counter
                    selectedShipCoordinates.clear();
                    confirmButton.setEnabled(false);
                    statusLabel.setText("Island placed! Place your next ship or island.");

                    gamePanel.revalidate();
                    gamePanel.repaint();

                    if(shipsPlaced >= 3 && islandsPlaced >=1) {
                        if(!isAI) {
                            if (currentPlayer.getName().equals(playerName +" (" + playerFaction + ")")) {
                                switchToOpponentPlacement();
                            } else {
                                // Player 2 finished placement - start battle phase
                                prepareForBattlePhase();
                            }
                        } else {
                            // AI opponent - start battle immediately
                            prepareForBattlePhase();
                        }
                    }
                } else {
                    statusLabel.setText("Invalid island placement! Try again.");
                }
            } else {
                statusLabel.setText("Invalid island size! Island must be 2x2.");
            }
        } else {
            // Confirm Ship placement
            if(shipsPlaced >= 3) {
                statusLabel.setText("You can only place three ships! Confirm or remove the existing one.");
                return;
            }

            if (selectedShipCoordinates.size() == shipSize) {
                // Create a new ship object and validate placement
                Ship ship = new Ship(playerFaction + " Ship " + (shipsPlaced + 1), selectedShipCoordinates);
                if (playerBoard.canPlaceShip(ship)) {
                    playerBoard.placeShip(ship);
                    for (Coordinate coord : selectedShipCoordinates) {
                        if (playerBoard.getFieldStatus(coord.getRow(), coord.getCol()) != 4) { // Skip island cells
                            playerButtons[coord.getRow()][coord.getCol()].setBackground(SHIP_COLOR); // Mark as ship
                        }
                    }
                    shipsPlaced++;
                    selectedShipCoordinates.clear();
                    confirmButton.setEnabled(false);
    
                    // Check if all ships are placed
                    if (shipsPlaced < 3) {
                        statusLabel.setText("Place your next " + shipSize + "-segment ship.");
                    } else {
                        // If all ships and islands are placed, switch phases
                        if (islandsPlaced >= 1) {
                            if (!isAI) {
                                if (currentPlayer.getName().equals(playerName +" (" + playerFaction + ")")) {
                                    switchToOpponentPlacement();
                                } else {
                                    // Player 2 finished placement - start battle phase
                                    prepareForBattlePhase();
                                }
                            } else {
                                // AI opponent - start battle immediately
                                prepareForBattlePhase();
                            }
                        } else {
                            statusLabel.setText("You need to place 1 island before starting the game.");
                        }
                    }
                } else {
                    statusLabel.setText("Invalid ship placement! Try again.");
                }
            } else {
                statusLabel.setText("Invalid ship size! Ship must be " + shipSize + "-segments long.");
            }
        }
    }

    private static void prepareForBattlePhase() {
        // Ensure Player 1 starts the battle phase
        if (!currentPlayer.getName().equals(playerName + " (" + playerFaction + ")")) {
            Player tempPlayer = currentPlayer;
            currentPlayer = opponent;
            opponent = tempPlayer;
    
            Board tempBoard = playerBoard;
            playerBoard = oppBoard;
            oppBoard = tempBoard;
        }
    
        startBattlePhase();
    }
    

    private static void switchToOpponentPlacement() {
        // Save player 1's board and reset for player 2
        Board tempBoard = playerBoard;
        playerBoard = oppBoard;
        oppBoard = tempBoard;
        
        // // Update current player to player 2
        // currentPlayer = opponent;
        // opponent = new Player(opponentName + " (" + playerFaction + ")");

        // Update current player to opponent
        Player tempPlayer = currentPlayer;
        currentPlayer = opponent;
        opponent = tempPlayer;

        // Reset placement variables
        shipSize = 2;
        shipsPlaced = 0;
        islandsPlaced = 0;
        isPlacingIsland = false;
        selectedShipCoordinates.clear();
        
        // Update UI
        gamePanel.removeAll();
        statusLabel.setText(opponentName + " Place your " + shipSize + "-segment ship");

        // Create new panel with GIF background
        gamePanel = new JPanel(new BorderLayout());

        // Add background GIF first (this will be at the bottom)
        JLabel backgroundLabel = new JLabel(new ImageIcon(GUI.class.getResource("/bg.gif")));
        backgroundLabel.setLayout(new BorderLayout());
        gamePanel.add(backgroundLabel, BorderLayout.CENTER);

        // Create a content panel that will hold everything else
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false); // Make it transparent

        JPanel boardPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        boardPanel.setOpaque(false); // Make transparent
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Player board (now showing player 2's placement)
        JPanel playerBoardPanel = createBoardPanel(true);
        boardPanel.add(playerBoardPanel);

        // Hide opponent board (which is now player 1's board)
        JPanel oppBoardPanel = createBoardPanel(false);
        oppBoardPanel.setVisible(false);
        boardPanel.add(oppBoardPanel);

        // Add board to content panel
        contentPanel.add(boardPanel, BorderLayout.CENTER);

        // Control panel with buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);

        // Dropdown for ship size selection
        sizeSelector = new JComboBox<>(new Integer[]{2,3,4,5}); // Size 2-5
        sizeSelector.setSelectedIndex(0); // Default to size 2
        sizeSelector.addActionListener(e -> {
            shipSize = (int) sizeSelector.getSelectedItem(); // Update ship size
            clearHighlights();
            if (!selectedShipCoordinates.isEmpty()) {
                // Try to place the ship of the new size
                Coordinate firstCoord = selectedShipCoordinates.get(0);
                new PlacementButtonListener(firstCoord.getRow(), firstCoord.getCol()).actionPerformed(null);
            }
        });
        controlPanel.add(new JLabel("Select Ship Size: "));
        controlPanel.add(sizeSelector);

        JComboBox<String> placementTypeSelector = new JComboBox<>(new String[]{"Ship", "Island"}); 
        placementTypeSelector.addActionListener(e -> {
            isPlacingIsland = placementTypeSelector.getSelectedItem().equals("Island");
            shipSize = isPlacingIsland ? 2 : (int) sizeSelector.getSelectedItem(); // Set ship size to 2 for island
            clearHighlights();
            statusLabel.setText(isPlacingIsland ? "Place your 2x2 Island" : "Place your " + shipSize + "-segment ship");
        }); 

        controlPanel.add(new JLabel("Place: "));
        controlPanel.add(placementTypeSelector);

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
        
        statusLabel = new JLabel(opponentName + ": Place your " + shipSize + "-segment ship");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        controlPanel.add(rotateButton);
        controlPanel.add(confirmButton);

        // Add components to content panel
        contentPanel.add(controlPanel, BorderLayout.NORTH);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(statusLabel, BorderLayout.SOUTH);

        // Add the content panel to the background label
        backgroundLabel.add(contentPanel);
        
        // Set game panel to frame
        gameFrame.setContentPane(gamePanel);
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
        
        // Initialize player buttons if not already done
        if (playerButtons == null) {
            playerButtons = new JButton[10][10];
        }

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JButton button = new JButton();
                button.setOpaque(true);
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                button.setPreferredSize(new Dimension(40, 40));
                
                int status = playerBoard.getFieldStatus(row, col);
                if (status == 0) {
                    button.setBackground(WATER_COLOR); // Water
                } else if (status == 1) {
                    button.setBackground(Color.CYAN); // Miss
                } else if (status == 2) {
                    button.setBackground(SHIP_COLOR); // Ship (not hit)
                } else if (status == 3) {
                    button.setBackground(INVALID_COLOR); // Hit
                } else if (status == 4) {
                    button.setBackground(Color.YELLOW); // Island
                }
                
                playerBoardPanel.add(button);
                playerButtons[row][col] = button;
            }
        }
        // Enemy board (clickable for attacks)
        JPanel enemyBoardPanel = new JPanel(new GridLayout(10, 10, 1, 1));
        enemyBoardPanel.setBorder(BorderFactory.createTitledBorder(
            isAI ? "Computer (" + oppFaction + ")" : oppFaction + "'s Waters"));
        
        // Initialize opponent buttons if not already done
        if (oppButtons == null) {
            oppButtons = new JButton[10][10];
        }
            
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(40, 40));
                button.setBackground(Color.BLUE);
                button.setOpaque(true);
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                
                int status = oppBoard.getFieldStatus(row, col);
                if (status == 1) {
                    button.setBackground(Color.CYAN); // Miss
                    button.setEnabled(false);
                } else if (status == 3) {
                    button.setBackground(INVALID_COLOR); // Hit
                    button.setEnabled(false);
                } else {
                    // status == 0 or 2 → must be attackable
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
            // Disable all attack buttons during processing
            disableAllAttackButtons();

            Coordinate target = new Coordinate(row, col);
            lastAttackHit = oppBoard.attack(target); // Track if the attack was a hit
            
            // Update the button that was clicked
            JButton button = (JButton)e.getSource();
            button.setEnabled(false);
            button.setBackground(lastAttackHit ? INVALID_COLOR : Color.CYAN);

            // Notify the player of the attack result
            String message = lastAttackHit ? "You hit an enemy ship!" : "You missed!";
            JOptionPane.showMessageDialog(gameFrame, message, "Attack Result",JOptionPane.INFORMATION_MESSAGE);

            // Then check game over
            if (oppBoard.isGameOver()) {
                showGameOver(currentPlayer.getName() + " wins! " + playerFaction + " prevails!");
                return;
            }
            
            if (lastAttackHit) {
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
                // Delay AI turn slightly for better UX
                Timer timer = new Timer(1000, ev -> {
                    aiTurn();
                });
                timer.setRepeats(false);
                timer.start();
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
        // Disable all attack buttons during AI turn
        disableAllAttackButtons();

        // AI chooses a target and attacks
        Coordinate target = ((AIPlayer)opponent).chooseAttack(playerBoard);
        boolean isHit = playerBoard.attack(target);
        
        int row = target.getRow();
        int col = target.getCol();

        // // DEBUG
        // System.out.println("AI attacked row: " + row + ", col: " + col);
        // System.out.println("Result: " + (isHit ? "Hit" : "Miss"));

        // Update player's board display
        if (playerButtons[row][col] != null) {
            playerButtons[row][col].setBackground(isHit ? INVALID_COLOR : Color.CYAN);
            playerButtons[row][col].setEnabled(false); // Disable the button after the move
        }

        // Then check game over
        if (playerBoard.isGameOver()) {
            showGameOver(opponent.getName() + " wins! " + oppFaction + " prevails!");
            return;
        }
        
        // Force UI to refresh
        gamePanel.revalidate();
        gamePanel.repaint();

        // Notify the player of the attack result
        if (isHit) {
            opponent.setScore(opponent.getScore() + 1); // Increment AI's score
            String sunkShip = playerBoard.shipNameIfKill(target); // Check if a ship was sunk

            if (sunkShip != null) {
                opponent.setScore(opponent.getScore() + 2); // Bonus points for sinking ship
                statusLabel.setText("AI hit your " + sunkShip + "!");
            } else {
                statusLabel.setText("AI hit your ship at (" + row + ", " + col + ")!");
            }

            // AI handles the hit for smarter targeting
            ((AIPlayer) opponent).handleHit(target, playerBoard);
            
            // Check if player has lost
            if (playerBoard.isGameOver()) {
                showGameOver(opponent.getName() + " wins! " + oppFaction + " prevails!");
                return; // End the game
                }
            } else {
                statusLabel.setText("AI hit your ship! Your turn.");
            }  
            
        // Add delay before enabling player's turn
        Timer timer = new Timer(1500, ev -> {
            if (!playerBoard.isGameOver()) {
                statusLabel.setText("Your turn. Attack the enemy fleet!");
                enableAllAttackButtons(); // Allow player 1 to take their turn
            }
        });

        timer.setRepeats(false);
        timer.start();

    }
    private static void disableAllAttackButtons() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (oppButtons[row][col] != null) {
                    oppButtons[row][col].setEnabled(false);
                }
            }
        }
    }
    
    private static void enableAllAttackButtons() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (oppButtons[row][col] != null) {
                    int status = oppBoard.getFieldStatus(row, col);
                    if (status == 0 || status == 2) { // Only enable water tiles
                        oppButtons[row][col].setEnabled(true);
                    }
                }
            }
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
        // Hide both boards temporarily
        gamePanel.removeAll();
        gameFrame.revalidate();
        gameFrame.repaint();

        // Show transition screen
        showTransitionScreen(() -> {
            // Notify the next player if their board was hit
            String hitMessage = lastAttackHit
                ? "Your ship was hit during the last turn!"
                : "No hits during the last turn.";
            JOptionPane.showMessageDialog(gameFrame, hitMessage,"Turn Update", JOptionPane.INFORMATION_MESSAGE);

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

            // For 2-player mode, just refresh the boards with the correct perspective
            if (!isAI) {
                if (currentPlayer.getName().startsWith(playerName)) {
                    // Switching to Player 2's turn
                    currentPlayer = opponent;
                    opponent = new Player(playerName + " (" + playerFaction + ")");
                } else {
                    // Switching back to Player 1's turn
                    currentPlayer = new Player(playerName + " (" + playerFaction + ")");
                    opponent = new Player(opponentName + " ("+ oppFaction + ")");
                }
            }
            
            // Refresh the boards display
            refreshBoards();
        });
    }
    
    private static void showTransitionScreen(Runnable onTransitionComplete) {
        // Create a transition panel
        JPanel transitionPanel = new JPanel(new BorderLayout());
        JLabel messageLabel = new JLabel(
            "<html><div style='text-align: center;'>"
            + "<h1>Pass the device to " + opponent.getName() + "</h1>"
            + "<p>Click 'Continue' when ready</p>"
            + "</div></html>",
            SwingConstants.CENTER
        );
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        transitionPanel.add(messageLabel, BorderLayout.CENTER);

        JButton continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Arial", Font.BOLD, 16));
        continueButton.addActionListener(e -> {
            gamePanel.removeAll();
            onTransitionComplete.run();
            gameFrame.revalidate();
            gameFrame.repaint();
        });
        transitionPanel.add(continueButton, BorderLayout.SOUTH);

        // Add the transition panel to the game frame
        gamePanel.add(transitionPanel, BorderLayout.CENTER);
        gameFrame.add(gamePanel);
        gameFrame.revalidate();
        gameFrame.repaint();
    }

    // Add this helper method
    private static void refreshBoards() {
        gamePanel.removeAll();

        // Refresh player board (showing current player's own ships)
        JPanel boardsPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        boardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Player board (show ships)
        JPanel playerBoardPanel = new JPanel(new GridLayout(10, 10, 1, 1));
        // playerBoardPanel.setBorder(BorderFactory.createTitledBorder("Your Fleet (" + currentPlayer.getName() + ")"));

        String playerTitle = isAI ? "Your Fleet (" + playerFaction + ")" 
                             : currentPlayer.getName() + "'s Fleet";
        playerBoardPanel.setBorder(BorderFactory.createTitledBorder(playerTitle));

        Board currentPlayerBoard = currentPlayer.getName().startsWith(playerName) ? playerBoard : oppBoard;

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JLabel label = new JLabel();
                label.setOpaque(true);
                label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                label.setPreferredSize(new Dimension(40, 40));
                label.setBackground(WATER_COLOR); // Default to water color
                
                // Check the status of the field
                int status = currentPlayerBoard.getFieldStatus(row, col);
                if(status == 0) {
                    label.setBackground(WATER_COLOR); // Water
                } else if (status == 1) {
                    label.setBackground(new Color(173, 216, 230));
                } else if (status == 2) {
                    label.setBackground(SHIP_COLOR); // Ship
                } else if (status == 3) {
                    label.setBackground(INVALID_COLOR); // Hit
                } else if (status == 4) {
                    label.setBackground(Color.YELLOW); // Island
                }
                playerBoardPanel.add(label);
            }
        }
        boardsPanel.add(playerBoardPanel);

        // Opponent board (clickable for attacks)
        JPanel enemyBoardPanel = new JPanel(new GridLayout(10, 10, 1, 1));
        String enemyTitle = isAI ? "Computer's Waters" : opponent.getName() + "'s Waters";
        enemyBoardPanel.setBorder(BorderFactory.createTitledBorder(enemyTitle));
        
        Board enemyBoard = currentPlayer.getName().startsWith(playerName) ? oppBoard : playerBoard;

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(40, 40));
                button.setBackground(WATER_COLOR);
                button.setOpaque(true);
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                
                int status = enemyBoard.getFieldStatus(row, col);
                if (status == 1) {
                    button.setBackground(Color.CYAN); // Miss
                    button.setEnabled(false);
                } else if (status == 3) {
                    button.setBackground(INVALID_COLOR); // Hit
                    button.setEnabled(false);
                } else {
                    // status == 0 or 2 → must be attackable
                    button.addActionListener(new AttackButtonListener(row, col));
                }
                enemyBoardPanel.add(button);
                oppButtons[row][col] = button;
            }
        }
        boardsPanel.add(enemyBoardPanel);

        gamePanel.add(boardsPanel, BorderLayout.CENTER);
        gamePanel.add(statusLabel, BorderLayout.SOUTH);
        gameFrame.revalidate();
        gameFrame.repaint();
    }
}