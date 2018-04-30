import java.awt.Color;
import java.awt.Graphics;

public class EPaddle {
	private double pY = 0;
	public boolean UPPressed, downPressed;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 600;
	private int x = 760;
	
	public EPaddle() {
		x = 760;
		pY = 0;
	}

	public void draw(Graphics bg) {
		bg.setColor(new Color(0xFFDACC));
		bg.fillRect(x, (int)pY, WIDTH, HEIGHT);

	}


	public double getY() {
		// TODO Auto-generated method stub
		return pY;
	}


	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}
}

