import java.awt.Color;
import java.awt.Graphics;


public class Chip {
	private int r;
	private int X;
	private int Y;
	Color myColor;
	private final int space_X=20;
	
	
	Chip(int x, int y, Color c){
		r = 70;
		X = x;
		X += space_X;
		Y = y;
		myColor = c;
		
	}
	public void draw(Graphics g)
	{
		
		g.setColor(Color.black);
		g.fillOval(X,Y,r+10,r+10);
		g.setColor(Color.white);
		g.fillOval(X+2,Y+2,r+6,r+6);
		g.setColor(myColor);
		g.fillOval(X+5, Y+5, r, r);
	}
	
	
}
