import java.awt.Color;
import java.awt.Graphics;


public class CannonBall {
	
	private double x,y,vX,vY;
	
	public CannonBall(double x, double y, double vX, double vY){
		this.x = x;
		this.y = y;
		this.vX = vX;
		this.vY = vY;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.black);
		g.fillOval((int)x, (int)y, 10, 10);
	}
	
	public void update(){
		x+=vX;
		y+=vY;
		
		if(x<0) x = TheGame.WIDTH;
		if(x>TheGame.WIDTH) x = 0;
		vY += 1;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}

}
