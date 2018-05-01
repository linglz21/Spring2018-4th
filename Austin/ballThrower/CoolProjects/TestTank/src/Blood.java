import java.awt.Graphics;
import java.awt.*;

public class Blood implements Sprite
{
	private Location position;
	private Location velocity;
	private double age;

	public Blood(Location position, Location velocity)
	{
		this.position = position;
		this.velocity = velocity;
		age = Math.random()+0.75;
	}

	public void draw(Graphics g)
	{
		g.setColor(new Color(255, 0, 0, (int)(75*(3-age))));
		g.fillOval((int)position.x,(int)position.y,(int)(3*age),(int)(3*age));
	}

	public Location getLocation()
	{
		return position;
	}

	public void update()
	{
		position.x += velocity.x;
		position.y += velocity.y;
		velocity.x *= 0.98;
		velocity.y *= 0.98;
		age += .01;
		System.out.println(age);
	}

	public double getAge()
	{
		return age;
	}
}
