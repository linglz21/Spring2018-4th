import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Paddle implements KeyListener{
	public double x, y, vX, width, height, midX;
	public Paddle() {
		x = 400;
		y = 600-20;
		height = 20;
		width = 150;
		vX = 0;
		midX = x + width/2; 
	}
	
	public void draw(Graphics g){
		g.setColor(Color.black);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			x -= 10;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			x += 10;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
