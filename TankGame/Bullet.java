package TankGame;

import java.awt.*; 
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Bullet extends Rectangle{
	
	int w=30,h=10;
	int x,y;	
	int speed=10;
	int id;

	
	Image bulletRed,bulletBlue;
	public Bullet(int x,int y,int id) {
		this.x=x;
		this.y=y;
		this.id=id;
		
		ImageIcon bullet1 = new ImageIcon(getClass().getResource("/Image/RedBullet.png"));
		bulletRed = bullet1.getImage();
		ImageIcon bullet2 = new ImageIcon(getClass().getResource("/Image/BlueBullet.png"));
		bulletBlue = bullet2.getImage();
	}
	public void shot() {
		  if (id == 1) {
	            x += speed;
	        } else {
	            x -= speed;
	        }
	}
	public Rectangle getBounds() {
	    return new Rectangle(x, y + 10, w, h); // Adjust with your actual width and height
	}
	public void render(Graphics g) {
		//initial position of the bullet( at the tank)
		if(id==1) {
			g.drawImage(bulletRed, x+70, y+5, w, h, null);
		}else {
			g.drawImage(bulletBlue, x-25, y+5, w, h, null);
		}
			
	}
	
	//method that get x value of bulletx
	 public double getX() {
	        return id == 1 ? x + 50 : x;
	    }
	}

