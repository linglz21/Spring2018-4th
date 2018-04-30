import java.awt.Color;
import java.awt.Graphics;


public class Brick {
	double x, y, width, height;
	 
	public Brick(int x, int y) {
		this.x = x;
		this.y = y;
		width = 75;
		height = 20;			
	}
	
	public void draw (Graphics g) {
		g.setColor(Color.blue);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
	}
}
