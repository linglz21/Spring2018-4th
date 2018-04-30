import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.*;

public class PiranhaFish extends Fish
{
	private Location acceleration;
	private Location position2;
	private Location position3;
	private Location position4;

	public PiranhaFish(Location position)
	{
		super(position);
		acceleration = new Location(0,0);
		position2 = new Location(position.x-10,position.y);
		position3 = new Location(position.x-15,position.y);
		position4 = new Location(position.x-25,position.y);
		speed.x = 1;
		maxSpeed = 6;
	}
	public void swim()
	{
		position.x += speed.x;
		position.y += speed.y;
		speed.x += acceleration.x;
		speed.y += acceleration.y;
		speed.x *= 0.99;
		speed.y *= 0.99;
		acceleration.x *= 0.92;
		acceleration.y *= 0.92;
	}

	public void swim(Location loc)
	{
		double deltaX = loc.x - position.x;
		double deltaY = loc.y - position.y;
		double prob = .08;        //1.0 / Location.distance(loc, this.getLocation());
		if(Math.random() < prob)
		{
			double speed = Math.random();
			acceleration.x += speed * deltaX * 0.001;
			acceleration.y += speed * deltaY * 0.001;
		}
	}
	public void eat()
	{
	}
	public void die()
	{
	}
	public void kill()
	{
	}

	public void update()
	{
		swim();
		if(Math.random()< 0.05)
		{
			acceleration.x += Math.random()*0.5-0.25;
			acceleration.y += Math.random()*0.5-0.25;
		}

		if(position.x <0) speed.x+=5;
		if(position.x > myTank.WIDTH) speed.x-=5;
		if(position.y < 0) speed.y+=5;
		if(position.y > myTank.HEIGHT) speed.y -=5;

		//double deltaX = position.x - position2.x;
		//double deltaY = position.y - position2.y;
		double deltaX = speed.x;
		double deltaY = speed.y;
		double distance = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
		deltaX /= distance/7.0;
		deltaY /= distance/7.0;
		position2.x = position.x-deltaX;
		position2.y = position.y-deltaY;

		deltaX = position2.x - position3.x;
		deltaY = position2.y - position3.y;
		distance = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
		deltaX /= distance/5.0;
		deltaY /= distance/5.0;
		position3.x = position2.x-deltaX;
		position3.y = position2.y-deltaY;

		deltaX = position3.x - position4.x;
		deltaY = position3.y - position4.y;
		distance = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
		deltaX /= distance/10.0;
		deltaY /= distance/10.0;
		position4.x = position3.x-deltaX;
		position4.y = position3.y-deltaY;

		//if bump into edge of tank swim toward center

		if (speed.length() > maxSpeed)
		{
			speed.scale(maxSpeed);
		}
	}

	public void draw(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform at = new AffineTransform();
		at.translate(position.x,position.y);
		at.rotate(Math.atan2(speed.y,speed.x));
		at.translate(-10,-6);
		g2d.setTransform(at);
		g2d.setColor(new Color(0x3E, 0x4A, 0x2A));		//body color
		g2d.fillOval(0,0,20,12);
		g2d.setColor(new Color(0x59, 0x06, 0x08));		//eye color
		g2d.fillOval(15,0,4,4);
		g2d.fillOval(15,7,4,4);


		at.translate(10, 6);
		g2d.setTransform(at);
		int[] fX = {0, -9, -11};
		int[] lfY = {-6, -12, -8};
		int[] rfY = {6, 12, 8};

		g2d.setColor(new Color(0x6A, 0x73, 0x5C));		//side fin color
		g2d.fillPolygon(fX, lfY, 3);
		g2d.fillPolygon(fX, rfY, 3);


		at.setToTranslation((int)position2.x,(int)position2.y);
		double deltaX = position2.x - position3.x;
		double deltaY = position2.y - position3.y;
		double angle = Math.atan2(-deltaY, -deltaX);
		at.rotate(angle);
		g2d.setTransform(at);
		g2d.setColor(new Color(0x40, 0x4F, 0x27));		//mid tail color
		g2d.fillOval(-2,-3,11,6);

		int[] x = {-3, 15, 15};
		int[] y = {0, 5, -5};


		at.setToTranslation((int)position3.x,(int)position3.y);
		deltaX = position3.x - position4.x;
		deltaY = position3.y - position4.y;
		angle = Math.atan2(-deltaY, -deltaX);
		at.rotate(angle);
		g2d.setTransform(at);
		g2d.setColor(new Color(0x48, 0x5E, 0x25));		//tail color
		g2d.fillPolygon(x, y, 3);

		at.setToTranslation(0, 0);
		g2d.setTransform(at);



		/*
		g2d.setColor(Color.black);
		at.setToTranslation((int)position4.x,(int)position4.y);
		g2d.setTransform(at);
		g2d.fillOval(-1,-1,2,2);
		*/
	}
}