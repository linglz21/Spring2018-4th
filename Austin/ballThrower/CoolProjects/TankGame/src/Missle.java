import java.util.ArrayList;

public class Missle extends Shot {
	Tank target;

	/**
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @param v
	 *            velocity
	 * @param a
	 *            angle
	 * @param r
	 *            radius
	 * @param m
	 *            map
	 * @param c
	 *            creator
	 */
	Missle(int x, int y, double v, double a, double r, Map m, Tank c) {
		super(x, y, v, a, r, m, c);
		this.damage = 15;
		this.r = 7;
		ArrayList<Tank> temp = (ArrayList<Tank>) GameRunner.tanks.clone();
		temp.remove(c);
		if (temp.size() > 0) {
			target = temp.get(0);
			// //////////Start Requirement:9 ArrayList
			for (Tank t : temp) {
				// //////////End Requirement:9 ArrayList
				// //////////Start Requirement:2 Logic Example: 1
				if (getDistance(t) < getDistance(target) && t != creator) {
					// //////////End Requirement:2 Logic Example: 1
					target = t;
				}
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Shot#update()
	 */
	public void update() {
		super.update();
		
		if (target != creator&&target!=null) {
			this.vX += (target.x - x) / 500;
			this.vY += (target.y - y) / 500;
		}

	}

	/**
	 * @param t
	 *            tank to check distance
	 * @return how far away it is
	 */
	public double getDistance(Tank t) {
		return Math.sqrt((x - t.x) * (x - t.x) + (y - t.y) * (y - t.y));

	}
}
