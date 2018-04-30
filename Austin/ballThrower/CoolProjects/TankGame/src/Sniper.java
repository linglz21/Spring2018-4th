
public class Sniper extends Shot{

	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param v velocity
	 * @param a angle
	 * @param r radius
	 * @param m map
	 * @param c creator
	 */
	Sniper(int x, int y, double v, double a, double r, Map m, Tank c) {
		super(x, y, v, a, r, m, c);
		this.damage=50;
		this.r=5;
		this.vX*=2;
	}

}
