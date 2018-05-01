import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

class TextBox extends Sprite implements KeyListener
{
	private String myText;
	private String myLabel;
	private TextBoxRunnable r;

	public TextBox(Vector2 position, String label, TextBoxRunnable thing){
		r = thing;
		myText = "";
		myLabel = label;
		this.position = position;
		isFalling = false;
		shape = new Vector2(150,20);
	}

	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == e.VK_BACK_SPACE){
			myText = myText.substring(0,myText.length()-1);
		}if(e.getKeyCode() == e.VK_ENTER){
			r.run(myText);
			myText = "";
		}else{
			char temp = e.getKeyChar();
			if(Character.isLetterOrDigit(temp) ||
			   (temp+"").equals(" ") ||
			   Pattern.matches("\\p{Punct}", temp+"")){
				myText = myText + e.getKeyChar();
			}
		}
	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}

	public void draw(Graphics g){
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
