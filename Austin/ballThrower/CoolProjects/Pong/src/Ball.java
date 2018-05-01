import java.awt.Color;
import java.awt.Graphics;


public class Ball implements Sprite{
	private double x,y,r,vX,vY; // (x,y)=center  r = radius  (vX,vY)= the puck velocity
	private Color myColor; 
	private boolean gameOver; 
	
	// Initialize all of the non static variables
	public Ball(){
		gameOver = false; 
		myColor = new Color(0x60A783);
		r = 10; 
		x = Pong.WIDTH/2; 
		y = Pong.HEIGHT/2;                     
		vX = -8;
		vY = 0;
	}

	@Override
	public void update() {
		if(gameOver)return; // if the game is over don't update the puck
		x+=vX; // Update the position based upon the velocity
		y+=vY;
		if(x<r && vX<0){gameOver = true;}; // if the puck is touching the left edge of the screen and going left make it go right
		if(x>Pong.WIDTH-r && vX>0){
			vX*=-1;
			Pong.g.increaseScore(); // if the puck is touching the right edge and going right make if go left
			x = Pong.WIDTH/2; 
			y = Pong.HEIGHT/2;                     
			vX = -8;
			vY = 0;
		}
		
		if(y<r && vY<0)vY*=-1; // if the puck is touching the top edge and going up make it go down
		if(y>Pong.HEIGHT-r && vY>0)vY*=-1; // If the puck is touching the bottom then the game is over
		
		
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(myColor); // Draw a circle the correct color centered about (x,y)
		g.fillOval((int)(x-r),(int) (y-r),(int) (2*r),(int) (2*r));
		
	}


	@Override
	public void doHit(Ball p) {
		// This was required by the Sprite interface
		// and could be useful in a game with multiple pucks
		// but for now there is only one puck so nothing to do here
	}

	
	public double getX(){return x;} // The Brick and Paddle classes use these methods
	public double getY(){return y;} // to find the size and location of the puck
	public double getR() {return r;} // when trying to check for collisions 

	public void bounce(Paddle paddle) { // This method is called by the Paddle class when hit by the puck
		double pY = paddle.getYmid();// first find the locaiton of the paddle
		double pX = paddle.getX();
		double dY = y-pY; // find the horizontal distance between the center of the paddle and the center of the puck
		if(vX<0)vX*=-1; // if the puck is moving down bounce up.
		x = pX+r; // Put the puck above the paddle so we don't accidentally hit it multiple times. 

		vY += dY/20; // Adjust the horizontal velocity of the puck depending upon how far it was from the center of the paddle
		
	}


}
