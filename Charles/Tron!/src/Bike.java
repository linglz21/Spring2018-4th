import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
public class Bike implements KeyListener {
	private double vX,vY,x,y;
	private boolean isOne; // true if player one otherwise player two
	public boolean rightPressed, leftPressed, upPressed, downPressed;
	private static final int SPEED=5;

	public Bike(double x, double y, boolean isOne){
		this.vX = 0;
		this.vY = 0;
		this.x = x;
		this.y = y;
		this.isOne = isOne;
	}
	public void draw(Graphics g){
		if(isOne){
			g.setColor(new Color(0xff304c));
		}else{
			g.setColor(new Color(0xf5cc00));
		}
		g.fillRect((int)x, (int)y, 10, 10);
	}
	
	public void update(){
		x += vX;
		y += vY;
		if(upPressed){
			vX = 0;
			vY = -SPEED;
		}
		if(downPressed){
			vX = 0;
			vY = SPEED;
		}
		if(leftPressed){
			vX = -SPEED;
			vY = 0;
		}
		if(rightPressed){
			vX = SPEED;
			vY = 0;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		}

		if (key == KeyEvent.VK_LEFT) {
			leftPressed = true;
		}

		if (key == KeyEvent.VK_UP) {
			upPressed = true;
		}

		if (key == KeyEvent.VK_DOWN) {
			downPressed = true;
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}

		if (key == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}

		if (key == KeyEvent.VK_UP) {
			upPressed = false;
		}

		if (key == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}