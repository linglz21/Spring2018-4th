import java.awt.*;

public abstract class Sprite
{
	public static final double gravity = 0.1;
	protected Vector2 position, motion, shape;
	protected boolean isFalling, isDead;

	public Sprite(){
		position = new Vector2();
		motion = new Vector2();
		shape = new Vector2();
		isFalling = true;
		isDead = false;
	}

	public void update(){
		position.add(motion);
		if(position.x+shape.x > GameClient.width && motion.x>0) motion.x *= -1;
		if(position.x < 0 && motion.x<0) motion.x *= -1;
		motion.mult(0.97);
	}


	public abstract void draw(Graphics g);

}
