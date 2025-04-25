package edu.gonzaga.BattleShipGame;

import java.awt.Color;
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
            GameLoop.startGame(); // Call the startGame method from GameLoop
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
}
