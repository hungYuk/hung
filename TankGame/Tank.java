	package TankGame;
	
	
	import java.awt.*;
	import java.awt.event.*;
	import java.util.*;
	import javax.swing.*;
	
	public class Tank extends Rectangle {
		
		int id;
		int xVelocity;
		int yVelocity;
		int speed=3;
		int blood1 = 200;
		int blood2 = 200;
		//blood flow percentage
		int Cblood1 = blood1;  //Current blood
		int Cblood2 = blood2;
		
		int dieAnimationStep=0;
		
		private boolean upPressed = false;
		private boolean downPressed = false;
		private boolean leftPressed = false;
		private boolean rightPressed = false;
		
		
		
		Image tankRed, tankBlue;
		
		
		long delay = System.currentTimeMillis();
		Tank(int x,int y,int TANK_HEIGHT ,int TANK_WIDTH ,int id){
			
			super(x,y,TANK_HEIGHT ,TANK_WIDTH);
			this.id=id;
			
			
			//upload images
			
			
			ImageIcon tank1 = new ImageIcon(getClass().getResource("/Image/tankRed.png"));
			tankRed = tank1.getImage();
			ImageIcon tank2 = new ImageIcon(getClass().getResource("/Image/tankBlue.png"));
			tankBlue = tank2.getImage();
			
		}
		
		private void handleTank1Keys(KeyEvent e) {
	        if (e.getKeyCode() == KeyEvent.VK_W) {
	            setYDirection(-speed);
	            upPressed = true;
	        }
	        if (e.getKeyCode() == KeyEvent.VK_S) {
	            setYDirection(speed);
	            downPressed = true;
	        }
	        if (e.getKeyCode() == KeyEvent.VK_D) {
	            setXDirection(speed);
	            rightPressed = true;
	        }
	        if (e.getKeyCode() == KeyEvent.VK_A) {
	            setXDirection(-speed);
	            leftPressed = true;
	        }
	    }
	
	    private void handleTank2Keys(KeyEvent e) {
	        if (e.getKeyCode() == KeyEvent.VK_UP) {
	            setYDirection(-speed);
	            upPressed = true;
	        }
	        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
	            setYDirection(speed);
	            downPressed = true;
	        }
	        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	            setXDirection(speed);
	            rightPressed = true;
	        }
	        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	            setXDirection(-speed);
	            leftPressed = true;
	        }
	    }
	
	    private void handleTank1KeyRelease(KeyEvent e) {
	        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
	        	 setYDirection(0);
	             upPressed = false;
	             downPressed = false;
	        }
	        if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A) {
	            setXDirection(0);
	            leftPressed = false;
	            rightPressed = false;
	        }
	    }
	    
	
	    private void handleTank2KeyRelease(KeyEvent e) {
	        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
	            setYDirection(0);
	            upPressed = false;
	            downPressed = false;
	        }
	        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
	            setXDirection(0);
	            leftPressed = false;
	            rightPressed = false;
	        }
	    }
	
		
		 public void KeyPressed(KeyEvent e) {
		        if (id == 1) {
		            handleTank1Keys(e);
		        } else if (id == 2) {
		            handleTank2Keys(e);
		        }
		    }
	    
		 public void KeyReleased(KeyEvent e) {
		        if (id == 1) {
		            handleTank1KeyRelease(e);
		        } else if (id == 2) {
		            handleTank2KeyRelease(e);
		        }
		    }
		 
		 
	    public void setXDirection(int xDirection) {
	    	xVelocity = xDirection;
	    	x+= xVelocity;
	    }
	    
	
	    public void setYDirection(int yDirection) {
	    	yVelocity = yDirection;
	    	y+=yVelocity;
	    	
	    }
	    
	    public void move() {
	    	 if (upPressed) {
	    	        setYDirection(-speed);
	    	    } else if (downPressed) {
	    	        setYDirection(speed);
	    	    } else {
	    	        setYDirection(0);
	    	    }
	
	    	 if (leftPressed) {
	    	        setXDirection(-speed);
	    	    } else if (rightPressed) {
	    	        setXDirection(speed);
	    	    } else {
	    	        setXDirection(0);
	    	    }
	        x += xVelocity;
	        y += yVelocity;
	    }
	
	   
	    

	    public void draw(Graphics g) {
	    	

			
	    	int bp1 = (int) ((Cblood1 / (float) blood1) * 100);
	    	int bp2 = (int) ((Cblood2 / (float) blood2) * 100);

	        // Draw tank normally
	       
	            if (id == 1)
	                g.drawImage(tankRed, x, y - height / 2+10, width, height, null);
	            else
	                g.drawImage(tankBlue, x, y - height / 2+10, width, height, null);

	            // Draw blood bar
	            
	            
	         
	            g.setColor(Color.GREEN);

	            if (id == 1) {
	            	
	                if (Cblood1 < 0.7*blood1) {
	                    g.setColor(Color.YELLOW);
	                    if (Cblood1 < 0.3*blood1)
	                        g.setColor(Color.RED);
	                }
	                
	                g.fillRect(x - 10, y - height, bp1, 10);
	            } else {
	            	
	                if (Cblood2 < 0.7*blood2) {
	                    g.setColor(Color.YELLOW);
	                    if (Cblood2 < 0.3*blood2)
	                        g.setColor(Color.RED);
	                }
	                g.fillRect(x - 10, y - height, bp2, 10);
	            }
	       
	    }

	    
	}
	    
	
	
