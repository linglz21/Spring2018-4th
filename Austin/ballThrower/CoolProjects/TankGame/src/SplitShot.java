
public class SplitShot extends Shot{

	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param v velocity
	 * @param a angle
	 * @param r radius
	 * @param m map
	 * @param c creator
	 */
	SplitShot(int x, int y, double v, double a, double r, Map m, Tank c) {
		super(x, y, v, a, r, m, c);
		this.damage=10;
	}
	
	/* (non-Javadoc)
	 * @see Shot#explode()
	 */
	public void explode(){
		super.explode();
		for(int i=0; i<4;i++){
			Shot temp= new Shot(this);
			GameRunner.shots.add(temp);
			GameRunner.sprites.add(temp);
		}
		
	}

}
