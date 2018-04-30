
public class Location
{
	public double x;
	public double y;

	public Location(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public Location(Location other){
		this.x = other.x;
		this.y = other.y;
	}

	public static double distance(Location a, Location b)
	{
		double deltaX = a.x - b.x;
		double deltaY = a.y - b.y;
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}

	public void scale(double sf)
	{
		double distance = length();
		x = x / distance;
		y = y / distance;
		x *= sf;
		y *= sf;

	}

	public double length()
	{
		return Math.sqrt(x * x + y * y);
	}
}
