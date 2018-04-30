import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.Random;


public class Orb implements Serializable {
	private static final long serialVersionUID = 1L;
	public Color color;
	protected double velocityX;
	protected double velocityY;
	protected double mapPosX, mapPosY;
	protected double screenX, screenY;
	protected double mass;
	
	Orb(Color color, int mass) {
		this.color = color;
		this.mass = mass;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g.setColor(color);
		//TODO: add zoom
		//System.out.println("[Orb] Drawing orb @ "+mapPosX+", "+mapPosY);
		g.fillOval((int)(mapPosX),(int)(mapPosY),(int)(getRadius()*2), (int)(getRadius()*2));
	}
	public Point getCenter() {
		return new Point((int)(mapPosX+getRadius()), (int)(mapPosY+getRadius()));
	}
	public int getRadius() {
		return (int)Math.sqrt(mass);
	}
	
	public boolean isColliding(Orb o) {
		double rad = this.getRadius()+o.getRadius();
		double dX = o.mapPosX-this.mapPosX; if(dX>rad)return false;
		double dY = o.mapPosY-this.mapPosY; if(dY>rad)return false;
		if (dX*dX + dY*dY < rad*rad) return true;
		return false;
	}
	
	public void update() {
		mapPosX += velocityX;
		mapPosY += velocityY;
	}
	public double getSqrDistTo(Orb o){
		double dX = o.mapPosX-this.mapPosX;
		double dY = o.mapPosY-this.mapPosY;
		return dX*dX+dY*dY;
	}
	
	public double getDistanceTo(Orb o) {
		double dX = o.mapPosX-this.mapPosX;
		double dY = o.mapPosY-this.mapPosY;
		return Math.sqrt(dX*dX+dY*dY);
	}
	
	public static Color randomColor() {
		Random rand = new Random();
		int r = rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);
		return new Color(r, g, b);
	}
	
	public double getDistanceTo(Point p) {
		return Math.sqrt(Math.pow((p.getX()-this.mapPosX), 2.0)+Math.pow((p.getY()-this.mapPosY), 2.0));
	}
}
