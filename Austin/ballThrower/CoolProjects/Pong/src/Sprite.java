import java.awt.Graphics;

// These are the things that we need to do with any sort of game object
public interface Sprite {

	public void update(); // We need to be able to update it
	public void draw(Graphics g); // We need to be able to draw it
	
	public void doHit(Ball p); // We need to handle collisions with the Puck
}
