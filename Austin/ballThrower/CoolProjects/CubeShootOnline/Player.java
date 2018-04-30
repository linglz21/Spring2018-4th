import java.awt.*;

public class Player extends Sprite
{
	protected double health;
	protected Color c;
	protected Vector2 oldPosition;
	protected int myID;

	public String toString(){
		String output = "" + myID;
		output += " " + health;
		output += " " + position;
		return output;
	}

	public void update(double h, Vector2 p){
		health = h;
		position = p;
	}

	public Player(int ID){
		health = 100;
		float r = (float)Math.random();
		float g = (float)Math.random();
		float b = (float)Math.random();
		c = new Color(r,g,b);
		position.x = Math.random()*(GameClient.width-shape.x);
		position.y = Math.random()*(GameClient.height-shape.y);
		shape.x = 10;
		shape.y = 10;
		oldPosition = position.clone();
		myID = ID;
	}

	// called by client

	// called by server
	public void update(){
		oldPosition = position.clone();
		motion.y += gravity;
		super.update();
	}

	public void hitTest(Platform p){
		if(oldPosition.y + shape.y > p.position.y)return;
		if(position.y + shape.y < p.position.y) return;
		if(position.x + shape.x < p.position.x) return;
		if(position.x > p.position.x + p.shape.x) return;
		motion.y = 0;
		position.y = oldPosition.y;
	}

	public void draw(Graphics g){
		g.setColor(Color.white);
		g.fillRect((int)position.x,(int)position.y,(int)shape.x,(int)shape.y);
		g.setColor(c);
		g.fillRect((int)position.x+1,(int)position.y+1,(int)shape.x-2,(int)shape.y-2);
	}
}
