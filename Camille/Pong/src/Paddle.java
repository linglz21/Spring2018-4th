import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Paddle implements KeyListener {
	private double pY, pV; // paddle position and velocity
	public boolean UPPressed, downPressed;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 80;
	private int x;

	public Paddle() {
		x = 70;
		UPPressed = downPressed = false;
		pY = MainClass.WIDTH / 2;
		pV = 0;
	}

	public void draw(Graphics bg) {
		bg.setColor(new Color(0xFFDACC));
		bg.fillRect(x, (int)pY, WIDTH, HEIGHT);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			UPPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			UPPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
		}

	}

	public void update() {
		if (UPPressed) pV -= 2;
		if (downPressed)pV += 2;
		pY +=pV;
		pV *= 0.9;
		if(pY<0 && pV<0)pV *=-1;
		if(pY>MainClass.HEIGHT-80 && pV>0)pV*=-1;

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public double getX() {
		return x;
	}

	public double getY() {
		return pY;
	}
}