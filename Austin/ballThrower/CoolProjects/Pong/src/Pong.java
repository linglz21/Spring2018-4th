import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Pong extends JFrame implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int TOP_SPACE = 23; // the graphics window has a little bar at the top. Make space for it.
	private final int SLEEP_TIME = 20; // Time between updates. Larger values slow the game down.
	
	public static Pong g;
	private int score = 0;
	private int opponentscore = 0;
	
	BufferedImage myImage;
	Graphics offscreen;
	
	LockableList<Sprite> allSprites; // Holds all of the game objects
	Ball myPuck;
	Paddle p;
	
	private Pong(){
		myImage = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
	    try {
			myImage = ImageIO.read(getClass().getResource("/resources/tennis!.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Create the offscreen image used with double buffering
		//buffer = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
		offscreen = myImage.getGraphics();
		Graphics2D g2 = (Graphics2D)offscreen;
		// These rendering hints make things look better.
		// They also make the computer work harder.
		// So on an old slow machine you might need to remove these
	    RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    RenderingHints rh2 = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
	    g2.setRenderingHints(rh2);
	    addKeyListener(this);
	
	    // Create the paddle and set it to listen to the keyboard
		p = new Paddle();
		this.addKeyListener(p);
		
		// Create the list of game objects
		allSprites = new LockableList<Sprite>();		
		allSprites.acquire(); // Always lock the list before messing with the contents
		allSprites.add(p); // Put the paddle in the list
		allSprites.add(myPuck = new Ball()); // Create the puck and put it in the list
		allSprites.release(); // When done with the list remember to release the lock.	
			
	}
	
	// normal JFrame initialization stuff.
	public static void main(String[] args) {
		g = new Pong(); // Create the game
		g.setSize(WIDTH, HEIGHT+TOP_SPACE); // Set the size of the window
		g.setResizable(false); 
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Make sure the program closes up cleanly
		new Thread(g).start(); // Start the main game loop
		g.setVisible(true); // Make it all visible on the screen
	}
	
	// Draw all sprites on the screen. Use double buffering
	public void paint(Graphics g){
		offscreen.setColor(new Color(0f,0f,0f,1f)); // transparent black
		offscreen.fillRect(0,0, WIDTH, HEIGHT); // filling the background
		///////////////////////
		offscreen.setColor(Color.white);
		offscreen.drawString("Your Score = " + score, 30, 30);
		offscreen.drawString("Opponent's Score = " + opponentscore, 630, 30);
		///////////////////////
		if(allSprites != null){ // draw everything onto the off screen image
			allSprites.acquire();
			for(Sprite s : allSprites)s.draw(offscreen);
			allSprites.release();
		}
		offscreen.setColor(Color.white);
		offscreen.fillRect(WIDTH/2-42, 0, 25, HEIGHT);
		g.drawImage(myImage, 0, TOP_SPACE, null); // put the off screen image on the screen
		
	}
	public void increaseScore(){
		score++;
	}
	public void increaseOScore(){
		opponentscore++;
	}
	
	@Override
	public void run() {
		while(true){ // this is the main game loop. It updates and repaints everything on regular intervals.
			try { 
				Thread.sleep(SLEEP_TIME); // Wait a short time so that the game doesn't run too fast
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			allSprites.acquire(); // Get a lock before messing with the list of game objects
			for(int i=0 ; i<allSprites.size() ; i++){ // Go through the entire list
				Sprite s = allSprites.get(i); // Access items in the list one by one
				s.update(); // tell each item to update itself
				s.doHit(myPuck); // check for collisions with the puck
			}
			allSprites.release(); // Let go of the lock as soon as done with the list
			repaint(); // Make sure everything is drawn to the screen
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			g = new Pong(); // Create the game
			g.setSize(WIDTH, HEIGHT+TOP_SPACE); // Set the size of the window
			g.setResizable(false); 
			g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Make sure the program closes up cleanly
			new Thread(g).start(); // Start the main game loop
			g.setVisible(true); // Make it all visible on the screen
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
