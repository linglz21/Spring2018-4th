import java.awt.Color;
import java.awt.Graphics;

public class Ball {
	private double x, y, vX, vY; // puck position and velocity
	public static final int SIZE=20;

	public Ball() {
		x = MainClass.WIDTH / 2;
		y = MainClass.HEIGHT / 2;
		vX = -6;
		vY = 0;
	}

	public void draw(Graphics bg) {
		bg.setColor(Color.white);
		bg.fillOval((int)x,(int) y, SIZE,SIZE);

	}
	
	public void update(){
		x += vX;
		y += vY;
		//if (x < 0 || x > MainClass.WIDTH-20);
			//MainClass.gameOver = true;
		if (y < 0 || y > MainClass.HEIGHT-20) vY *= -1;
	}
	public void hit (Paddle p){
	    if(x+SIZE<p.getX())return;
	    if(x>p.getX()+p.WIDTH)return;
	    if(y+SIZE<p.getY())return;
	    if(y>p.getY()+p.HEIGHT)return;
	    double dY = y - (p.getY()+p.HEIGHT/2);
	    vY += dY*0.1;
		vX *=-1.02;
		vY *= 1.02;
	}
	public void hit (EPaddle ep){
	    if(x>ep.getX()+ep.WIDTH)return;
	    if(x+SIZE<ep.getX())return;
	    vX *= -1.05;

	    
	}
	
	public boolean isOffScreen(){
		return x < 0 || x > MainClass.WIDTH-20;
	}
}

