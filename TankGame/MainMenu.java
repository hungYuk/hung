package TankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu() {
    	
    	ImageIcon icon = new ImageIcon(getClass().getResource("/Image/TankIcon.png"));
	    Image iconGame = icon.getImage();
	    this.setIconImage(iconGame);
	    
	    
        setTitle("Tank Game");
        setSize(300, 200); // main menu size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        JButton playButton = new JButton("Play");
        JButton exitButton = new JButton("Exit");

        // Set preferred button size (increased size)
        Dimension buttonSize = new Dimension(300, 60);
        playButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        // Add action listeners to the buttons
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPlayOptions();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                System.exit(0);
            }
        });

        // Create a panel to hold the buttons
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        
        panel.add(playButton);
        panel2.add(exitButton);

        // Set layout for the main frame
        setLayout(new BorderLayout());

        // Add the button panel to the top
        add(panel, BorderLayout.NORTH);
        add(panel2, BorderLayout.CENTER);

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    private void showPlayOptions() {
        Object[] options = {"2 Player", "AI"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Select a play mode:",
                "Play Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            startGame(false); // 2 Player
        } else if (choice == 1) {
            startGame(true); // AI
        }
    }
    
    private void startGame(boolean vsAI) {
        // Create and show the game frame
        JFrame gameFrame = new JFrame("Tank Game");
        GamePanel gamePanel = new GamePanel(vsAI);
        gameFrame.getContentPane().add(gamePanel);
        gameFrame.setSize(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        

        // Close the main menu frame
	    
        dispose();
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainMenu mainMenu = new MainMenu();
                mainMenu.setVisible(true);
            }
        });
    }
}
