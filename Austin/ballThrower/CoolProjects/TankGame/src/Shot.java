import java.awt.Color;
import java.awt.Graphics;


public class Shot extends Sprite{
	protected double x;
	protected double y;
	protected double vX;
	protected double vY;
	protected double a;
	protected double r;
	protected double damage=20;
	protected Map m;
	protected boolean exploded=false;
	protected Tank creator;
	
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param v velocity
	 * @param a angle
	 * @param r radius
	 * @param m map
	 * @param c creator
	 */
	Shot(int x, int y, double v, double a, double r, Map m, Tank c){
		this.x=x;
		this.y=y;
		vX=v*2*Math.cos(a)/2;
		vY=v*2*Math.sin(a)/2;
		this.m=m;
		this.r=r;
		creator=c;
	}

	/**
	 * @param s other shot to copy
	 */
	public Shot(Shot s) {
		this.x=s.x;
		this.y=s.y;
		vX=(Math.random()-.5)*2.5;
		vY=Math.random()*-1;
		this.m=s.m;
		this.r=s.r-5;
		this.damage=7;
	}

	/* (non-Javadoc)
	 * @see Sprite#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect((int)x,(int) y, 1, 1);
		
	}
	
	/**
	 * @return if a shot is in the ground
	 */
	private boolean isOverlapping(){
		if(checkBounds()){
			return m.isOverlapping((int)x,(int) y);

		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return("("+(int)x+","+(int)y+","+exploded+")");
	}
	
	/**
	 * 
	 */
	public void explode(){
		m.takeDamage((int)x,(int) y,(int) r);
	}
	
	/**
	 * @return if it is in bounds
	 */
	private boolean checkBounds(){
		if(x<0||x>799||y<0||y>799){
			return false;
		}
		return true;
	}
	
	/**
	 * @param delta add wind to velocity
	 */
	public void addWind(double delta){
		vX+=delta;
	}

	/* (non-Javadoc)
	 * @see Sprite#update()
	 */
	@Override
	public void update() {
		x+=vX;
		y+=vY;
		vY+=.05/2;
		if(isOverlapping()){
			explode();
			exploded=true;
		}
		if(!checkBounds()){
			GameRunner.shots.remove(this);
			GameRunner.sprites.remove(this);
		}
	}

}
