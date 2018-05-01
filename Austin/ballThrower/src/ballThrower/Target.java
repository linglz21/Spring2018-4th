package ballThrower;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;

public class Target {
	int x,y;
	
	public Target(){
		x = (int)(Math.random()*MainClass.WIDTH/2) + MainClass.WIDTH/2;
		y = (int)(Math.random()*MainClass.HEIGHT);
	}
	
	public boolean hit(Ball b){
		double dX = x - b.getX()-7;
		double dY = y - b.getY()-7;
		return Math.sqrt(dX*dX+dY*dY)<30;

	}
	
	public void draw(Graphics g){
		g.setColor(Color.red);
		g.fillOval(x-30, y-30, 60,60);
		g.setColor(Color.black);
		g.fillOval(x-20, y-20, 40,40);
		g.setColor(Color.white);
		g.fillOval(x-10, y-10, 20,20);
	}
}
