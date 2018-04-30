import java.awt.event.*;
import java.awt.*;

class Button extends Sprite implements MouseListener
{
	//public static final double gravity = 0.1;
	//protected Vector2 position, motion, shape;
	//protected boolean isFalling, isDead;
	private String myText;
	private Runnable r;
	private Font f;

	public Button(Vector2 position, String text, Runnable thing){
		r = thing;
		myText = text;
		this.position = position;
		isFalling = false;
		shape = null;
	}

	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){
		if(e.getX()> position.x &&
		   e.getX()< position.x+shape.x &&
		   e.getY()> position.y &&
		   e.getY()< position.y+shape.y)
		{
		   	r.run();
		}
	}

	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}


	public void draw(Graphics g){
		if(shape==null){
			FontMetrics fm = g.getFontMetrics();
			shape = new Vector2(fm.stringWidth(myText)+8,fm.getHeight()+2);
		}
		g.setColor(Color.black);
		g.fillRect((int)position.x-1,
			       (int)position.y-1,
			       (int)shape.x+2,
			       (int)shape.y+2);
		g.setColor(Color.gray);
		g.fillRect((int)position.x,
			       (int)position.y,
			       (int)shape.x,
			       (int)shape.y);
		g.setColor(Color.white);
		g.drawString(myText,
		             (int)position.x+3,
					 (int)(position.y+shape.y-6));
	}
}
