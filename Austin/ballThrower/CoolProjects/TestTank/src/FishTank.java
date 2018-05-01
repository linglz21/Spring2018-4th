import java.util.*;
import java.awt.Graphics;

public class FishTank implements Sprite
{
	// water
	// eggs
	// fish
	// food
	private ArrayList<Sprite> objects;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 800;
	private Water myWater;

	public FishTank(Water theWater)
	{
		objects = new ArrayList<Sprite>();
		// puts water in tank
		// puts fish in tank
		myWater = theWater;

		for(int i=0 ; i<40 ; i++)
		{
			int x = (int)(Math.random()*WIDTH);
			int y = (int)(Math.random()*HEIGHT);
			Location loc = new Location(x, y);
			if(Math.random()<0.9)
			{
				objects.add(new GoldFish(loc));
			}else{
				objects.add(new PiranhaFish(loc));
			}
		}

		/*
		for(int i=0 ; i<5 ; i++)
		{
			int x = (int)(Math.random()*WIDTH);
			int y = (int)(Math.random()*HEIGHT);
			Location loc = new Location(x, y);
			objects.add(new PiranaFish(loc));
		}
		*/
		Fish.putFishInTank(this);
	}

	public void addObject(Sprite object)
	{
		objects.add(object);
		// places the object in the tank
	}

	public void update()
	{
		for(int i = objects.size() - 1; i >= 0 && i<objects.size(); i--)
		{
			Sprite s = objects.get(i);
			s.update();
			if(s instanceof Fish)
			{
				for(int j = objects.size() - 1; j >= 0; j--)
				{
					Sprite s2 = objects.get(j);
					if(s2 instanceof Food)
					{
						foodInteraction((Fish)s, (Food)s2);
					}

					if (s2 instanceof Fish)
					{
						fishInteraction((Fish)s, (Fish)s2);
					}
				}

				if(s instanceof GoldFish)
				{
					GoldFish f = (GoldFish)s;
					if (f.hungerLevel > 98)
					{
						/*
						int x = (int)(Math.random()*WIDTH);
						int y = (int)(Math.random()*HEIGHT);
						Location loc = new Location(x, y);
						objects.add(new GoldFish(loc));
						*/
						objects.add(new Egg(Egg.Breed.GOLD_FISH , f.getLocation()));
						f.hungerLevel = 60;
					}
				}

			}
			else if(s instanceof Egg)
			{
				Egg e = (Egg)s;
				if(e.getAge()>500){
					objects.remove(s);
					objects.remove(e);
					objects.add(e.hatch());

				}
			}
			else if(s instanceof Blood)
			{
				Blood b = (Blood)s;
				if(b.getAge() >3.0) objects.remove(s);
			}
		}
	}

	private void fishInteraction(Fish f1, Fish f2)
	{
		if(f1 instanceof PiranhaFish && !(f2 instanceof PiranhaFish))
		{
			f1.swim(f2.getLocation());

			//double fishLocX = f2.getLocation().x;
			//double fishLocY = f2.getLocation().y;

			if(Location.distance(f1.getLocation(), f2.getLocation()) < 5)
			{
				double fishLocX = f2.getLocation().x;
				double fishLocY = f2.getLocation().y;
				objects.remove(f2);
				myWater.disturb((int)f2.getLocation().x, (int)f2.getLocation().y);
				myWater.disturb((int)f2.getLocation().x, (int)f2.getLocation().y);
				myWater.disturb((int)f2.getLocation().x, (int)f2.getLocation().y);
				myWater.disturb((int)f2.getLocation().x, (int)f2.getLocation().y);

				for(int i = 0; i < 170; i++)
				{
					Location loc = new Location(fishLocX, fishLocY);
					double x = Math.random() - 0.5;
					double y = Math.random() - 0.5;
					if(x*x+y*y<0.25)
					{
						this.addObject(new Blood(loc, new Location(x, y)));
					}
				}
			}
		}
	}

	private void foodInteraction(Fish f1, Food f2)
	{
		if(f1 instanceof GoldFish)
		{
			f1.swim(f2.getLocation());
			if(Location.distance(f1.getLocation(), f2.getLocation()) < 5)
			{
				objects.remove(f2);
				myWater.disturb((int)f2.getLocation().x, (int)f2.getLocation().y);
				f1.eat();
			}
		}
	}

	public void draw(Graphics g)
	{
		for(int i = objects.size() - 1; i >= 0; i--)
		{
			Sprite s = objects.get(i);
			s.draw(g);
		}
	}

	public void removeObject(Sprite object)
	{
		objects.remove(object);
	}
}
