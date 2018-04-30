
public abstract class Fish implements Sprite
{
	protected int size; // 1-50 one is smallest
	protected double hungerLevel; // 0-100 zero is most hungry
	protected boolean male;
	protected Location position;
	protected Location speed;
	protected static FishTank myTank;
	protected double maxSpeed;
	protected boolean rotateDirection;
	protected double rotation;

	public Fish(Location position)
	{
		rotateDirection = true;
		rotation = 0;
		size = 1;
		hungerLevel = 50;
		male = Math.random()<0.5;
		this.position = position;
		speed = new Location(0,0);
	}

	public static void putFishInTank(FishTank theTank)
	{
		myTank = theTank;
	}

	public Location getLocation()
	{
		return position;
	}

	public abstract void swim();
	public abstract void swim(Location loc);
	public abstract void eat();
	public abstract void die();
	public abstract void kill();

}
