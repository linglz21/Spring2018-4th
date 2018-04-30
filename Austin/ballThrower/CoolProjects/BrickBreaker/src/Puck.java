import java.awt.Color;
import java.awt.Graphics;


public class Puck {
	double x, y, vX, vY, paddleX, midX, radius;
	public Puck() {
		x = 400;
		midX = 
		y = 300;
		vX = 3;
		vY = -3;
		radius = 20;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillOval((int)x, (int)y, (int)radius, (int)radius);
	}
	
	public void update() {
		x += vX;
		y += vY;
		if (x < 0 && vX < 0)vX*=-1;
		if (x > 780 && vX > 0)vX*=-1;
		if (y < 0 && vY < 0)vY*=-1;
		if (y > 580) {
			vY = 0;
			vX = 0;
		}
	}
	
	public void hitPaddle(Paddle p) {
		if (y < 560)return;
		if (x+20 < p.x)return;	
		if (x>p.x + p.width)return;			
		if (vY < 0)return;
		vY*=-1.1; //Slightly increase speed each time paddle is hit
		
		vX = (x - p.midX)/25;
		//TODO: adjust vX
		System.out.println("vX: "+vX);
				
	}
	
	public boolean hitBrick(Brick b) {
		if (x+20 < b.x)return false;
		if (x > b.x+b.width)return false;
		if (y+20 < b.y)return false;
		if (y > b.y+b.height)return false;
		// Bounce
		vY*=-1;
		return true;
	}
}
