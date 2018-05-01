import java.awt.*;

class Platform extends Sprite
{
	// from Sprite
	//public static final double gravity = 0.1;
	//protected Vector2 position, motion, shape;
	//protected boolean isFalling, isDead;
	private static Color c;
	private static Color c2;

	public Platform(){
		isFalling = false;
		shape.x = 100;
		shape.y = 20;

		position.x = (GameClient.width-shape.x) * Math.random();
		position.y = (GameClient.height-shape.y) * Math.random();
		float r = (float)(Math.random()/2+0.5);
		float g = (float)(Math.random()/2+0.5);
		float b = (float)(Math.random()/2+0.5);
		c = new Color(r,g,b);
		c2 = new Color(r/4,g/4,b/4);
	}
	public Platform(double x, double y, double width, double height){
		isFalling = false;
		shape.x = width;
		shape.y = height;
		position.x = x;
		position.y = y;
	}

	public Platform(double x, double y){
		isFalling = false;
		shape.x = 75;
		shape.y = 10;
		position.x = x;
		position.y = y;
		float r = (float)(Math.random()/2+0.5);
		float g = (float)(Math.random()/2+0.5);
		float b = (float)(Math.random()/2+0.5);
		c = new Color(r,g,b);
		c2 = new Color(r/4,g/4,b/4);
	}


	public Platform(String s){
		String[] split = s.split("%");
		if(split==null || s.length()<2){
			System.out.println("Trouble in Platform String constructor: " + s);
			return;
		}
		shape = new Vector2(split[0]);
		position = new Vector2(split[1]);
		isFalling = false;
		float r = (float)(Math.random()/2+0.5);
		float g = (float)(Math.random()/2+0.5);
		float b = (float)(Math.random()/2+0.5);
		c = new Color(r,g,b);
	}

	public String toString(){
		return  shape + "%" + position;
	}

	public void draw(Graphics g){
		g.setColor(c);
		g.fillRect((int)position.x,(int)position.y,(int)shape.x,(int)shape.y);
		g.setColor(c2);
		g.fillRect((int)position.x+1,(int)position.y+1,(int)shape.x-2,(int)shape.y-2);
	}

}
