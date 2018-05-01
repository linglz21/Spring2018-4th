import java.awt.Color;
import java.awt.Graphics;


public class LandingPad {
	private double x,y,width,height;

	public LandingPad(){
		width = 50;
		height = 20;
		x = TheGame.WIDTH/2 - width/2;
		y = TheGame.HEIGHT - height;
	}
	
	public boolean hitTest(double x, double y){
		return x+SpaceShip.WIDTH>this.x && x<this.x+this.width && y+SpaceShip.HEIGHT>this.y && y<this.y+this.height;
	}
	
	public void paint(Graphics g){
		g.setColor(Color.green);
		g.fillRect((int)x,(int)y,(int)width,(int)height);
	}
}
