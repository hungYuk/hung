package TankGame;

import java.awt.*; 
import java.awt.event.*;
import java.util.*;

import javax.print.DocFlavor.URL;
import javax.swing.*;
public class GameFrame extends JFrame{
	
	GamePanel panel ;
	
	
	GameFrame(boolean vsAI) {
		panel = new GamePanel(vsAI);
		this.add(panel);
		this.setTitle("Tank Game");
		this.setResizable(false);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/Image/TankIcon.png"));
	    Image iconGame = icon.getImage();
	    this.setIconImage(iconGame);

	}
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            new GameFrame(false); // Change to true if you want vsAI
	        });
	    }
}
