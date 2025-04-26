package edu.gonzaga.BattleShipGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI {
    private static boolean isAI = false; //Flag for AI component
    private static String playerFaction; // name of faction

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
        JFrame frame = new JFrame("Choose Your Island");
        frame.setSize(800,600);
        frame.setLayout(null);

        // Upload PNG image
        ImageIcon orginalIcon = new ImageIcon(GUI.class.getResource("/3.PNG"));
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

        // Faction buttons
        JButton isabelleButton = createFactionButton(
            "Isabelle of Bell Haven", buttonFont, buttonSize);
        isabelleButton.addActionListener(e -> {
            playerFaction = "Isabelle of Bell Haven";
            frame.dispose();
        });

        JButton tomButton = createFactionButton(
            "Tom Nook of Nook Isle", buttonFont, buttonSize);
        tomButton.addActionListener(e -> {
            playerFaction = "Tom Nook of Nook Isle";
            frame.dispose();
        });
        

        JButton neutralButton = createFactionButton(
            "Neutral Village", buttonFont, buttonSize);
        
        neutralButton.addActionListener(e -> {
            playerFaction = "Neutral Village";
            frame.dispose();
        });

        JButton antiButton = createFactionButton(
            "Anti-Fossil Abolitionist", buttonFont, buttonSize);
        antiButton.addActionListener(e-> {
            playerFaction = "Anti-Fossil Abolitionist";
            frame.dispose();
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


        // Add component to panel
        buttonPanel.add(isabelleButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(tomButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(neutralButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(antiButton);

        // Add component to frame
        frame.add(buttonPanel);
        frame.add(backButton);

        // Ensure the image is behind the buttons
        frame.getContentPane().setComponentZOrder(imageLabel, frame.getContentPane().getComponentCount() - 1);

        // Make the frame visible
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
}
