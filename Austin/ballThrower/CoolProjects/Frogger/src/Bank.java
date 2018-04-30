import java.awt.Color;
import java.awt.Graphics;


public class Bank {
	public double bankX, bankY; 
	public static final int logWidth = 500	;
	public static final int logHeight = 600;
	
	
	
	public void paint(Graphics g){
		g.setColor(Color.green);
		g.fillRect(300, 300, 600, 600);
	}
}
