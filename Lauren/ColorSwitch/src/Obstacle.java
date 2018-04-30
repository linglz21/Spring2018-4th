import java.awt.Color;
import java.awt.Graphics;

public class Obstacle {
	private double x,y,vX;
	public static final int WIDTH=120;
	public static final int HEIGHT=20;
	private Color myColor;
	
	public Obstacle(double x, double y, double vX, Color c){
		this.x = x;
		this.y = y;
		this.vX = vX;
		this.myColor = c;
	}
	
	public void draw(Graphics g){
		g.setColor(myColor);
		g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
	}
	
	public void update(){
		if(x<=-WIDTH && vX<=0)x += MainClass.WIDTH+WIDTH;
		if(x>=MainClass.WIDTH && vX>=0)x -= MainClass.WIDTH+WIDTH;
		x += vX;
	}
	
	public void shift(double dY){
		y+=dY;
		if(y>MainClass.HEIGHT)y = 0;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Object getColor() {
		return myColor;
	}

}
