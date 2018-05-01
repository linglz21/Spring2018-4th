import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Paddle implements Sprite, KeyListener{ // The paddle is controlled  by the keyboard so it listens to key strokes
	protected double x,y,w,h,vY; // x=left edge    y=top edge   w=width  h=height  vX=horizontal velocity
	
	private boolean upPressed,downPressed; // will be true if the left or right arrow buttons are pressed down
	private Color myColor;
	
	public Paddle(){ // initialize all non static variables in the constructor
		myColor = new Color(0x8BC7D9); // give it a color
		w = 20; // give it a size
		h = 80;
		x = 0; // put it at the bottom center of the screen
		y = Pong.HEIGHT/2;
	}
	
	@Override
	public void update() {
		y+=vY; // adjust the position based upon the velocity
		vY *= 0.94; // slow it down a little. Think of this number like friction
		            // Friction close to 1.0 and the paddle acts like it is on ice. Multiplying by 1.0 has no effect "no friction"
		            // Friction close to 0.0 and the paddle stops the instant you take your finger off the key. Multiplying by 0.0 makes vX 0.0 
		if(y<0 && vY<0) vY *=-.5; // If the paddle touches the left edge and is going left make it bounce 
		if(y+w>Pong.HEIGHT && vY>0) vY *= -.5; // If the paddle touches the right edge and is going right make it bounce
		if(upPressed)vY -= 1; // If the left button is pressed push the paddle left (decrease its horizontal velocity)
		if(downPressed)vY += 1; // if the right button is pressed push the paddle right (increase its horizontal velocity)
	}

	@Override
	public void draw(Graphics g) { // draw the paddle (appropriate color size and location)
		g.setColor(myColor);
		g.fillRect((int)x, (int)y, (int)w, (int)h); // Note: our variables are doubles to reduce rounding error
		                                            //       but fillRect expects int values
		                                            //       Java needs a cast to convert double to int
	}

	@Override
	public void keyTyped(KeyEvent e) { // required by the KeyListener interface but we don't need it so leave empty
	}

	@Override
	public void keyPressed(KeyEvent e) { // Called when a key is pressed, may repeat if key is held down
		if(e.getKeyCode() == KeyEvent.VK_UP)upPressed = true; // If the left arrow key is pressed set leftPressed to true
		if(e.getKeyCode() == KeyEvent.VK_DOWN)downPressed = true; // if the right arrow key is pressed set rightPressed to true
	}

	@Override
	public void keyReleased(KeyEvent e) { // Called when a key is released
		if(e.getKeyCode() == KeyEvent.VK_UP)upPressed = false; // If the left arrow key is released set rightPressed to false
		if(e.getKeyCode() == KeyEvent.VK_DOWN)downPressed = false; // If the right arrow key is released set rightPressed to false
	}
	
	public double getYmid(){return y+h/2;} // return horizontal middle of paddle. Used by the Puck when bouncing off the paddle
	public double getX(){return x+w;} // return the location of the top edge of the paddle. Used by the Puck when bouncing of the paddle

	@Override
	public void doHit(Ball p) { // Used to figure out if the puck hit the paddle
		double pX = p.getX(); // first find the size and location of the puck
		double pY = p.getY();
		double pR = p.getR();
		
		// If too far left right up or down then not a hit
		if(pY<y-pR || pY>y+h+pR || pX<x || pX>x+w) return;
		// otherwise the puck hit the brick
		p.bounce(this);
		// TODO Auto-generated method stub
		
	}

}
