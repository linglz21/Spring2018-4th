import java.awt.Color;
import java.awt.Graphics;


public class Projectile {
	private double x,y,vX,vY,age;
	
	public Projectile(double x, double y, double vX, double vY){
		this.x = x;
		this.y = y;
		this.vX = vX;
		this.vY = vY;
		age = 0;
	}
	
	public double getX(){ return x; }
	public double getY(){ return y; }
	
	public boolean isDead(){
		return age > 40;
	}
	
	public void update(){
		x += vX;
		y += vY;
		age += 1.5;
	}
		
	public void paint(Graphics g){
		g.setColor(Color.red);
		g.fillOval((int)x,(int)y,14,14);
	}
}
