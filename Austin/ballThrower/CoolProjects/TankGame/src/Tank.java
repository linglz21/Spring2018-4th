import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tank extends Sprite {
	Map m;
	Color c;
	double x, y;
	int number = 0;
	String name;
	int hillGradient = 10;
	double cannonAngle = -Math.PI / 4;
	double power = 2.5;
	double health = 100;
	double fallDamage = 0;
	double fallSpeed = 0;
	int shot = 0;

	/**
	 * @param m map for it to go on
	 */
	Tank(Map m) {
		this.m = m;
		c = new Color((int) (Math.random() * 255), (int) (Math.random() * 255),
				(int) (Math.random() * 255));
		y = 10;
		x = (int) (Math.random() * GameRunner.WIDTH);
		onGround();
	}

	/**
	 * @param m map for it to go on
	 * @param n what number it is
	 * @param na name of the tank
	 */
	Tank(Map m, int n, String na) {
		name=na;
		this.m = m;
		number = n;
		c = new Color((int) (Math.random() * 255), (int) (Math.random() * 255),
				(int) (Math.random() * 255));
		y = 10;
		x = (int) (Math.random() * GameRunner.WIDTH);
		onGround();
	}

	Tank(Map m, Color c) {

	}

	/**
	 * @param i changes what type of shot
	 */
	public void changeShot(int i) {
		shot+=i;
		if(shot>3)shot=0;
		if(shot<0)shot=3;
	}

	/* (non-Javadoc)
	 * @see Sprite#update()
	 */
	public void update() {
		isFalling();
		if (m.getMaterial(x, y - 1) != 0)
			y--;
		checkPower();
	}

	/**
	 * @param deltaAngle change angle of cannon
	 */
	public void moveC(double deltaAngle) {
		cannonAngle += deltaAngle;
		if (cannonAngle < -Math.PI)
			cannonAngle = -Math.PI;
		if (cannonAngle > 0)
			cannonAngle = 0;
	}

	/**
	 * 
	 */
	public void isFalling() {
		if (m.getMaterial(x, y) == 0) {
			moveY(fallSpeed);
			fallDamage += .3;
			fallSpeed += .1;
			if (fallSpeed > 3)
				fallSpeed = 3;
		}
		if (m.getMaterial(x, y) != 0) {
			if (fallDamage > 5)
				health -= (int) (fallDamage);
			fallDamage = 0;
			fallSpeed = 0;
		}

	}

	/**
	 * 
	 */
	public void onGround() {
		while (m.getMaterial(x, y) == 0) {
			moveY(1);
		}
	}

	/**
	 * @param deltaPow change power of shot
	 */
	public void changePower(double deltaPow) {
		power += deltaPow;
		if (power > 5 * health / 100)
			power = 5 * health / 100;
		if (power < 0)
			power = 0;
	}

	/**
	 * 
	 */
	public void checkPower() {
		if (health < 0)
			health = 0;
		if (power > 5 * health / 100)
			power = 5 * health / 100;
	}

	/**
	 * @param deltaX how far to move in the x direction
	 */
	public void moveX(double deltaX) {
		boolean tooHigh = true;
		for (int i = 0; i < hillGradient; i++) {
			////////////Start Requirement:2 Logic Example: 5

			if (m.getMaterial(x + deltaX, y - i) == 0) {
				tooHigh = false;
			}
			////////////End Requirement:2 Logic Example: 5

		}
		if (!tooHigh) {
			if (x + deltaX > 0 && x + deltaX < 800)
				x += deltaX;
		}
	}

	/**
	 * @return returns name of the current shot
	 */
	public String getShotName() {
		////////////Start Requirement:3 if-else statements

		if (shot == 0) {
			return "Standard";
		}else 
		if (shot == 1) {
			return "Split Shot";
		}else
		if (shot == 2) {
			return "Sniper";
		}else
		if (shot == 3) {
			return "Missle";
		}
		return null;
////////////End Requirement:3 if-else statements
	}

	/**
	 * @return shot with the current settings
	 */
	public Shot shoot() {
////////////Start Requirement:8 polymorphism
		if (shot == 0) {
			return new Shot((int) (x + 10 * Math.cos(cannonAngle)),
					(int) (y - 12 + 10 * Math.sin(cannonAngle)), power,
					cannonAngle, 10, m,this);
		}
		if (shot == 1) {
			return new SplitShot((int) (x + 10 * Math.cos(cannonAngle)),
					(int) (y - 12 + 10 * Math.sin(cannonAngle)), power,
					cannonAngle, 10, m,this);
		}
		if (shot == 2) {
			return new Sniper((int) (x + 10 * Math.cos(cannonAngle)),
					(int) (y - 12 + 10 * Math.sin(cannonAngle)), power,
					cannonAngle, 10, m,this);
		}
		if (shot == 3) {
			return new Missle((int) (x + 10 * Math.cos(cannonAngle)),
					(int) (y - 12 + 10 * Math.sin(cannonAngle)), power,
					cannonAngle, 10, m,this);
		}
		return null;
////////////End Requirement:8 polymorphism


	}

	/**
	 * @param deltaY how far to move in the y
	 */
	public void moveY(double deltaY) {
		if (y > 790)
			health = 0;
		if (y + deltaY > 0 && y + deltaY < 800)
			y += deltaY;
		/*
		 * if (y < 0){ y = 0; } if (y > 799){ y=799; health=0; }
		 */
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return ""+number;
	}

	/* (non-Javadoc)
	 * @see Sprite#paint(java.awt.Graphics)
	 */
	@Override
	void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine((int) x, (int) (y - 12),
				(int) (x + 10 * Math.cos(cannonAngle)),
				(int) (y - 12 + 10 * Math.sin(cannonAngle)));
		g.setColor(c);
		g.fillRect((int) (x - 10), (int) (y - 10), 20, 10);
		g.fillOval((int) (x - 7), (int) (y - 15), 14, 8);
		g.setColor(Color.black);
		g.drawString(name + "", (int) x, (int) (y - 20));
		g.drawString((int) health + "", (int) (x - 7), (int) (y - 30));
	}

}
