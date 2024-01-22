	package TankGame;
	
	
	import java.awt.*;  
	import java.awt.event.*;
	import java.util.*;
	import javax.swing.*;
	
	import java.util.List;
	import java.util.Timer;
	import java.util.concurrent.TimeUnit;
	
	
	public class GamePanel extends JPanel implements Runnable {
		
		static final int GAME_WIDTH = 1000;
		static final int GAME_HEIGHT = (int)(GAME_WIDTH *0.5);
		static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
		static final int GUN_HEIGHT= 10;
		static final int GUN_WIDTH = 10;
		static final int TANK_WIDTH = 70;
		static final int TANK_HEIGHT=50;
		static final int AI_SPEED=100;
		static final int BULLET_DAMAGE = 15;
		Thread gameThread;
		Image image;
		Image background;
		Graphics graphics;
		
		
		Tank tank1;
		Tank tank2;
		Score score;
		
		ArrayList<Bullet> bullet1;
		ArrayList<Bullet> bullet2;
		
		long t1=0;
		int delayB=300;
		
		boolean shouldRemove1,shouldRemove2;
		
		double x1Val=0;
		double x2Val=0;
		
		boolean tank1Hit = false;
		boolean tank2Hit = false;
		boolean tank1Die = false;
		boolean tank2Die = false;
		boolean gameOver = false;
		
		boolean vsAI = false;
		private long aiStartMove = System.currentTimeMillis();	
		
		private boolean aiFollowPlayer = true;
		
		
		//exploded image 
		
		
	    GamePanel(boolean vsAI){ // start
	        newTank();
	        this.vsAI=vsAI;
	       
	        
	        score = new Score(GAME_WIDTH,GAME_HEIGHT);
	        this.setFocusable(true);
	        this.addKeyListener(new AL());
	        this.setPreferredSize(SCREEN_SIZE);
	        
	        gameThread = new Thread(this);
	        gameThread.start();
	        
	        bullet1 = new ArrayList<Bullet>();
	        bullet2 = new ArrayList<Bullet>();
	        
	        //image 
	        
	        ImageIcon bg = new ImageIcon(getClass().getResource("/Image/Background.png"));
			background = bg.getImage();
			
	    }
	    
	    public void newTank(){   //tank position
	    	tank1 = new Tank(0,(GAME_HEIGHT/2)-(TANK_HEIGHT/2),TANK_WIDTH,TANK_HEIGHT,1);
			tank2 = new Tank(GAME_WIDTH-TANK_WIDTH,(GAME_HEIGHT/2)-(TANK_HEIGHT/2),TANK_WIDTH,TANK_HEIGHT,2); }
	    
	  
	    public void move(){
	        tank1.move();
	        tank2.move();
	        
	       
	    }
	    public void run(){ //gameLoop
	    	
	      
	        		while(true) {
	        		 try {
	        			 repaint();
	        			 checkBulletOutOfBounds();
	        			 move();
	        			 checkCollision();
	        			 if(vsAI) {
	        			 if (!gameOver) {
	                         handleAI();
	        			 }
	        			 }
	        			 gameThread.sleep(10);           
	                    
	                } catch (Exception e) {}
	        		
	        	}
	        }
	  
	  
	    public void checkCollision(){
	    	
	    	//tank collision
	    	if(tank1.y<=TANK_HEIGHT/2)
		        tank1.y=TANK_HEIGHT/2;
	        if(tank1.y >= GAME_HEIGHT-65)
	        	tank1.y = GAME_HEIGHT-65;
	        if(tank1.x<=0)
	        	tank1.x=0;
	        if(tank1.x>=(GAME_WIDTH/2-2*TANK_HEIGHT))
	        	tank1.x=(GAME_WIDTH/2-2*TANK_HEIGHT);
	        
	        if(tank2.y<=TANK_HEIGHT/2)
	        	tank2.y=TANK_HEIGHT/2;
	        if(tank2.y >= GAME_HEIGHT-65)
	        	tank2.y = GAME_HEIGHT-65;
	        if(tank2.x>=GAME_WIDTH-85)
	        	tank2.x=GAME_WIDTH-85;
	        if(tank2.x<=GAME_WIDTH/2+10)
	        	tank2.x=GAME_WIDTH/2+10;
	        
	        //bullet collision      
	        Iterator<Bullet> iterator1 = bullet1.iterator();
	        while (iterator1.hasNext()) {
	            Bullet aBullet = iterator1.next();
	            if (aBullet.getX() > GAME_WIDTH || aBullet.getX() < 0 || aBullet.getY() > GAME_HEIGHT || aBullet.getY() < 0) {
	                iterator1.remove();
	            } else if (checkBulletCollision(aBullet, bullet2)) {
	                iterator1.remove();
	            }
	        }
	
	        Iterator<Bullet> iterator2 = bullet2.iterator();
	        while (iterator2.hasNext()) {
	            Bullet bBullet = iterator2.next();
	            if (bBullet.getX() > GAME_WIDTH || bBullet.getX() < 0 || bBullet.getY() > GAME_HEIGHT || bBullet.getY() < 0) {
	                iterator2.remove();
	            } else if (checkBulletCollision(bBullet, bullet1)) {
	                iterator2.remove();
	            }
	        }
	        if (checkTankBulletCollision(tank1, bullet2)) {
	            tank1Hit = true;
	        }
	
	        if (checkTankBulletCollision(tank2, bullet1)) {
	            tank2Hit = true;
	        }
	    }
	    private boolean checkTankBulletCollision(Tank tank, List<Bullet> bullets) {
	        for (Bullet bullet : bullets) {
	            if (tank.getBounds().intersects(bullet.getBounds())) {
	                bullets.remove(bullet); // Remove the bullet
	                return true; // Tank is hit
	            }
	        }
	        return false; // No collision
	    }
	    
	    
	    private boolean checkBulletCollision(Bullet bullet, List<Bullet> otherBullets) {
	        Iterator<Bullet> iterator = otherBullets.iterator();
	        while (iterator.hasNext()) {
	            Bullet otherBullet = iterator.next();
	            if (bullet != otherBullet && bullet.getBounds().intersects(otherBullet.getBounds())) {
	                iterator.remove(); // Remove the otherBullet
	                return true; // Collision detected
	            }
	        }
	        return false; // No collision
	    }
	    

	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        // Draw the background image
	        g.drawImage(background, 0, 0, GAME_WIDTH, GAME_HEIGHT, this);

	        // Draw tanks
	        if(!tank1Die)
	        tank1.draw(g);
	        if(!tank2Die)
	        tank2.draw(g);

	        // Draw bullets
	        for (Bullet aBullet : bullet1) {
	            aBullet.render(g);
	            aBullet.shot();
	        }

	        for (Bullet bBullet : bullet2) {
	            bBullet.render(g);
	            bBullet.shot();
	        }

	        // Draw score, etc.
	        score.draw(g, gameOver);

	        if (tank1Hit) {
	            tank1.Cblood1 -= BULLET_DAMAGE;
	            tank1Hit = false;
	            if (tank1.Cblood1 <= 0) {
	                if (!gameOver)
	                    score.player2++;
	                tank1Die = true;
	                gameOver = true;
	            }
	        } else if (tank2Hit) {
	            tank2.Cblood2 -= BULLET_DAMAGE;
	            tank2Hit = false;
	            if (tank2.Cblood2 <= 0) {
	                if (!gameOver)
	                    score.player1++;
	                tank2Die = true;
	                gameOver = true;
	            }
	        }

	        if (gameOver) {
	            g.setColor(Color.WHITE);
	            g.setFont(new Font("Arial", Font.BOLD, 20));
	            
	            if(tank1Die)
	            g.drawString(" Blue Tank Won !!! Press 'R' to replay", GAME_WIDTH / 3, GAME_HEIGHT -50);
	            if(tank2Die)
		        g.drawString(" Red Tank Won !!! Press 'R' to replay", GAME_WIDTH / 3, GAME_HEIGHT -50);
	        
	            
	       }
	    }
	
	  
		    public void checkBulletOutOfBounds() {
		        Iterator<Bullet> iterator1 = bullet1.iterator();
		        while (iterator1.hasNext()) {
		            Bullet aBullet = iterator1.next();
		            if (aBullet.getX() > GAME_WIDTH || aBullet.getX() < 0 || aBullet.getY() > GAME_HEIGHT || aBullet.getY() < 0 ) {
		                iterator1.remove();
		            }
		        }
	
		        Iterator<Bullet> iterator2 = bullet2.iterator();
		        while (iterator2.hasNext()) {
		            Bullet bBullet = iterator2.next();
		            if (bBullet.getX() > GAME_WIDTH || bBullet.getX() < 0 || bBullet.getY() > GAME_HEIGHT || bBullet.getY() < 0) {
		                iterator2.remove();
		            }
		        }
		    }
		    private void handleAI() {
		    	long currentTime = System.currentTimeMillis();
		    	
		    	
		    	  if (currentTime - aiStartMove < 1500) {
		    		
		    	        // Follow player's position
		    	        if (tank1.y < tank2.y) {
		    	            tank2.setYDirection(-tank2.speed);
		    	        } else if (tank1.y > tank2.y) {
		    	            tank2.setYDirection(tank2.speed);
		    	            tank2.setXDirection(tank2.speed);
		    	        } else {
		    	            tank2.setYDirection(0); // Stand still if already at the player's position
		    	        }
		    	        
		    	    } else {
		    	       
		    	        if (currentTime - aiStartMove < 2000) {
		    	        	tank2.setYDirection(tank2.speed);
		    	        	tank2.setXDirection(-tank2.speed);	
		    	        }else {
		    	        if (currentTime - aiStartMove < 2500) 
		    	        	 tank2.setYDirection(-tank2.speed);
		    	        else {
		    	        if (currentTime - aiStartMove < 3000) 
			    	        tank2.setYDirection(-tank2.speed);
		    	        else {
		    	        	 if (currentTime - aiStartMove < 3500) 
			    	        tank2.setYDirection(tank2.speed);
		    	        else {
		    	        	if (currentTime - aiStartMove < 4000) 
			    	        tank2.setYDirection(tank2.speed);
		    	        
		    	        else {
		    	        	if (currentTime - aiStartMove < 4500) 
		    	        	tank2.setXDirection(tank2.speed);
		    	        else{
		    	        	 if (currentTime - aiStartMove < 5000) 
		 		    	    tank2.setYDirection(-tank2.speed);
		    	        else{
		    	        	 if (currentTime - aiStartMove < 5500) 
			 		    	tank2.setYDirection(-tank2.speed);
		    	        else{
		    	        	if (currentTime - aiStartMove < 6000) 
			    	        	tank2.setXDirection(-tank2.speed);
		    	        		
		    	        }
		    	        }
		    	        }
		    	        }
		    	        }
		    	        }
		    	        }
		    	        }
		    	    }
		    	  if (currentTime - aiStartMove >= 6500) {
		    	        aiStartMove = currentTime;
		    	        aiFollowPlayer = !aiFollowPlayer; // Toggle between following player and moving randomly
		    	    }
		    	
		    	  
		    	
		        int randomFire = (int) (Math.random() * 100);
		        
		        if (randomFire<=5){
		        	
		        	if(!gameOver)
		            bullet2.add(new Bullet(tank2.x, tank2.y, 2));
		        }
		  
		    }
	   public class AL extends KeyAdapter{
	       public void keyPressed(KeyEvent e) {
	    	   
	    	   if(!tank1Die)//if it got hit by a  bullet it can not move 
	    	   tank1.KeyPressed(e);
	    	   if(!tank2Die)
	    	   tank2.KeyPressed(e);
	    	   
	    	   
	    	//add new bullet to arraylist    
	    	  if (e.getKeyCode()==KeyEvent.VK_SPACE) {
	    		  
	    		  //delay the time to shot a bullet
	    		  if(!tank1Die) {
	    			  if(System.currentTimeMillis()-t1>=delayB) {
	    		  bullet1.add(new Bullet(tank1.x,tank1.y,1));
	    		  t1=System.currentTimeMillis();	    	
	  	        }
	    		  }
	    		  
	    	  }
	    	  if (e.getKeyCode()==KeyEvent.VK_NUMPAD0) {
	    		  if(!tank2Die) {
	    			    if(System.currentTimeMillis()-t1>=delayB) {
	    		  bullet2.add(new Bullet(tank2.x,tank2.y,2));
	    		  t1=System.currentTimeMillis();	    	
		        }
	    		  }
	    		
	    	  }
	    	  if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
	              resetGame();
	          }
	    	 
	       }
	       private void resetGame() {
	    	    tank1Hit = false;
	    	    tank2Hit = false;
	    	    gameOver = false; 
	    	    tank2Die = false;
	    	    tank1Die = false;
	    	    newTank(); // Reset tank positions
	    	    bullet1.clear(); // Clear bullets
	    	    bullet2.clear();
	    	    
	    	    repaint();
	    	}
	       
	       
	       public void keyReleased(KeyEvent e) {
	    	   tank1.KeyReleased(e);
	    	   tank2.KeyReleased(e);
	    	   
	  		 }
	       
	       
	   }
	
	
	}
	  
	
	
	    
	    
	    
	    
	    
