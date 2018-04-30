import java.awt.Color;
import java.awt.Graphics;


public class Platform {
	public static final int HEIGHT=10; 
	public static final int WIDTH=40;
	
	private static Color myColor;
	private static double dropSpeed;
	private static boolean firstTime = true;
	private double x,y;
	
	
	public Platform(){
		if(firstTime){
			firstTime = false;
			myColor = randomColor();
			dropSpeed = 2;
		}
		y = -HEIGHT;
		x = (int)(Math.random()*(TheGame.WIDTH-WIDTH));
	}
	
	public boolean hitTest(double x, double y){
		return x+Man.WIDTH>this.x && x<this.x+this.WIDTH && 
			   y+Man.HEIGHT>this.y && y<this.y+this.HEIGHT;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	private Color randomColor(){
		float r = (float)(Math.random()/2.0+0.5);
		float g = (float)(Math.random()/2.0+0.5);
		float b = (float)(Math.random()/2.0+0.5);
		return new Color(r,g,b);
	}
	
	public void update(){
		y += dropSpeed;
	}
	
	public boolean isDead(){
		return y>TheGame.HEIGHT;
	}
	
	public void paint(Graphics g){
		g.setColor(myColor);
		g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
	}

}
