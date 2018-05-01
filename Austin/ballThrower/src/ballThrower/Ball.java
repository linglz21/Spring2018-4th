package ballThrower;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {
	public static final double GRAVITY = 0.1;
	private double vX, vY, x, y;
	private Color myColor;
	
	public Ball(double x, double y, double vX, double vY){
		this.vX = vX;
		this.vY = vY;
		this.x = x;
		this.y = y;
		myColor = randomColor();
	}
	

	public Ball(double x, double y){
		this.x = x;
		this.y = y;
		myColor = randomColor();
		
	}
	
	public Ball(Ball other){
		this.x = other.x;
		this.y = other.y;
		this.vX = Math.random() * 10 - 5;
		this.vY = Math.random() * 10 - 5;
		double distance = Math.sqrt(vX * vX + vY * vY);
		while (distance > 5){
			this.vX = Math.random()	* 10 - 5;
			this.vY = Math.random() * 10 - 5;
			distance = Math.sqrt(vX * vX + vY * vY); 
		}
		myColor = randomColor();
		
	}
	

	
	private Color randomColor() {
		float r = (float) Math.random();
		float g = (float) Math.random();
		float b = (float) Math.random();
		return new Color(r, g, b);

	}


	public void draw(Graphics g){
		g.setColor(Color.green);
		g.fillOval((int) x, (int) y, 20, 20);
	}
	
	public void update(){
		x += vX;
		y += vY;
		vY += GRAVITY;
	}

	public boolean isOffScreen() {
		return this.y > MainClass.HEIGHT; 
	}
	
	


	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}


	public void setLastModified(long timestamp) {
		// TODO Auto-generated method stub
		
	}


	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
