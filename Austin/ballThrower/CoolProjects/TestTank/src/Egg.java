import java.awt.Graphics;
import java.awt.*;

public class Egg implements Sprite
{
	public static enum Breed {GOLD_FISH, GUPPIE, SHARK, PIRANHA};
	private Breed myBreed;
	private int age; // 0-5000
	private Location position;

	public Egg(Breed myBreed, Location position)
	{
		this.myBreed = myBreed;
		this.position = position;
		age = 0;
	}

	public Fish hatch()
	{
		return new GoldFish(new Location(position));
	}

	public void update()
	{
		age++;
	}

	public int getAge(){
		return age;
	}

	public void draw(Graphics g)
	{
		g.setColor(new Color(0xBA, 0xB9, 0x5D));
		g.fillOval((int)position.x,(int)position.y,10,10);
	}
}
