package TankGame;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class Score extends Rectangle {

    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    int player1;
    int player2;
   
   
    
    Image VS;
    public Score(int GAME_WIDTH, int GAME_HEIGHT) {
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
      
        //
        ImageIcon vs = new ImageIcon(getClass().getResource("/Image/VS.png"));
		VS = vs.getImage();
    }

    public void draw(Graphics g, boolean gameOver) {
    	
    	//SCORE
        g.setColor(Color.black);
        g.setFont(new Font("Time New Roman", Font.PLAIN, 30));
        g.drawString(String.valueOf(player1 / 10) + String.valueOf(player1 % 10), (GAME_WIDTH / 2) - 85, 50);
        g.drawString(String.valueOf(player2 / 10) + String.valueOf(player2 % 10), (GAME_WIDTH / 2) + 30, 50);
       
        //VS
        g.drawImage(VS,  (GAME_WIDTH / 2) - 30, 20, 40, 40, null);
        
       
    }


}
