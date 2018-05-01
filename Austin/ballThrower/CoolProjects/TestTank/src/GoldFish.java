import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.*;

public class GoldFish extends Fish
{
	private Location acceleration;
	private Location position2;
	private Location position3;
	private Location position4;
	private float scaleFactor;

	public GoldFish(Location position)
	{
		super(position);
		acceleration = new Location(0,0);
		position2 = new Location(position.x-10,position.y);
		position3 = new Location(position.x-15,position.y);
		position4 = new Location(position.x-25,position.y);
		speed.x = 1;
		maxSpeed = 5;
		scaleFactor = 0.5f;
	}
	public void swim()
	{
		position.x += speed.x;
		position.y += speed.y;
		speed.x += acceleration.x;
		speed.y += acceleration.y;
		speed.x *= 0.97;
		speed.y *= 0.97;
		acceleration.x *= 0.92;
		acceleration.y *= 0.92;
	}

	public void swim(Location loc)		//figure out how to have fish swim away from blood, ignoring food for some time
	{
		double deltaX = loc.x - position.x;
		double deltaY = loc.y - position.y;
		double prob = 1.0 / Location.distance(loc, this.getLocation());
		if(Math.random() < prob)
		{
			double speed = Math.random();
			acceleration.x += speed * deltaX * 0.001;
			acceleration.y += speed * deltaY * 0.001;
		}
	}
	public void eat()
	{
		scaleFactor += 0.01;
		if(hungerLevel < 100.0)
		{
			hungerLevel++;
		}
	}
	public void die()
	{
	}
	public void kill()
	{
	}

	public void update()
	{
		hungerLevel -= .01;
		//System.out.println(hungerLevel);
		swim();
		if(Math.random()< 0.05)
		{
			acceleration.x += Math.random()*0.5-0.25;
			acceleration.y += Math.random()*0.5-0.25;
			hungerLevel -= .2;
		}

		if(rotation > Math.PI / 14.0)
		{
			rotateDirection = false;
		}
		if(rotation < -Math.PI / 14.0)
		{
			rotateDirection = true;
		}
		if(rotateDirection)
		{
			rotation += ((Math.PI / 100)*(speed.length()));
		}else{
			rotation -= ((Math.PI / 100)*(speed.length()));
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

		if(hungerLevel>95)
		{

		}
	}

	public void draw(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;


		//Transform for body and eyes
		AffineTransform at = new AffineTransform();
		at.translate(position.x,position.y);
		at.scale(scaleFactor, scaleFactor);				//scale
		at.rotate(-rotation/2);
		at.rotate(Math.atan2(speed.y,speed.x));
		at.translate(-10,-6);
		g2d.setTransform(at);

		//Translation for side fins
		AffineTransform at2 = new AffineTransform(at);
		at2.translate(10, 6);


		//Translation for tail oval
		AffineTransform at3 = new AffineTransform();
		at3.translate((int)position.x,(int)position.y);
		at3.scale(scaleFactor, scaleFactor);			//scale
		at3.translate((int)position2.x-position.x,(int)position2.y-position.y);
		double deltaX = position2.x - position3.x;
		double deltaY = position2.y - position3.y;
		double angle = Math.atan2(-deltaY, -deltaX);
		//at3.rotate(rotation);
		at3.rotate(angle);

		//Translation for tail triangle
		AffineTransform at4 = new AffineTransform();
		at4.translate((int)position.x,(int)position.y);
		at4.scale(scaleFactor, scaleFactor);			//scale
		at4.translate((int)position3.x-position.x,(int)position3.y-position.y);
		deltaX = position3.x - position4.x;
		deltaY = position3.y - position4.y;
		angle = Math.atan2(-deltaY, -deltaX);
		at4.rotate(rotation);
		at4.rotate(angle);

		g2d.setTransform(at);
		g2d.setColor(Color.black);
		g2d.fillOval(-1, -1, 22, 14);

		g2d.setTransform(at3);
		g2d.setColor(Color.black);
		g2d.fillOval(-3, -4, 13, 8);

		int[] x2 = {-5, 13, 13};
		int[] y2 = {0, 6, -6};
		g2d.setTransform(at4);
		g2d.setColor(Color.black);
		g2d.fillPolygon(x2, y2, 3);


		g2d.setTransform(at);
		g2d.setColor(new Color(0xDB, 0xA7, 0x18));		//body color
		g2d.fillOval(0,0,20,12);
		g2d.setColor(new Color(0x00, 0x00, 0x00));		//eye color
		g2d.fillOval(15,0,4,4);
		g2d.fillOval(15,7,4,4);



		g2d.setTransform(at2);
		int[] fX = {0, -9, -11};
		int[] lfY = {-6, -12, -8};
		int[] rfY = {6, 12, 8};

		g2d.setColor(new Color(0xE8, 0x80, 0x3F));		//side fin color
		g2d.fillPolygon(fX, lfY, 3);
		g2d.fillPolygon(fX, rfY, 3);





		g2d.setTransform(at3);
		g2d.setColor(new Color(0xDB, 0xA7, 0x18));		//mid tail color
		g2d.fillOval(-2,-3,11,6);

		int[] x = {-3, 12, 12};
		int[] y = {0, 5, -5};




		g2d.setTransform(at4);



		g2d.setColor(new Color(0xDB, 0xA7, 0x18));		//tail color
		g2d.fillPolygon(x, y, 3);

		at.setToTranslation(0, 0);
		g2d.setTransform(at);
	}

	public boolean isFemale()
	{
		return (!male);
	}

	public double getHunger()
	{
		return hungerLevel;
	}
}
