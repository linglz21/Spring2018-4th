import java.awt.Color;
import java.awt.Graphics;


public class Particle {
	private double x,y,vX,vY,size;
	private Color myColor;
	
	public Particle(double x, double y){
		myColor = randomColor();
		this.x = x;
		this.y = y;
		double dist;
		do{
			vX = Math.random()*6-3;
			vY = Math.random()*6-3;
			dist = Math.sqrt(vX*vX+vY*vY);
		}while(dist>3);
		
		size = Math.random()*6+3;
	}
	
	private Color randomColor(){
		float r = (float)Math.random();
		float g = (float)Math.random();
		float b = (float)Math.random();
		return new Color(r,g,b);
	}
	
	public boolean isDead(){
		return size<=0;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void update(){
		x += vX;
		y += vY;
		size -= 0.05;
	}
	
	public void push(double dX, double dY){
		if(dX > vX)System.out.println(dX);
		vX += dX;
		vY += dY;
	}
	
	public void paint(Graphics g){
		g.setColor(myColor);
		g.fillOval((int)x,(int)y,(int)size,(int)size);
	}
		

}
