import java.awt.Graphics;
import java.awt.*;

public class Food implements Sprite
{
	private Location position;
	private Location velocity;
	private long age;

	public Food(Location position, Location velocity)
	{
		this.position = position;
		this.velocity = velocity;
		age = 0;
	}

	public void draw(Graphics g)
	{
		g.setColor(new Color(0x78, 0x58, 0x14));
		g.fillOval((int)position.x,(int)position.y,4,4);
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
	}

}
