import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Dice implements MouseListener{
	
	private static BufferedImage diceImage = null;
	private static BufferedImage selectedImage = null;
	private int myNumber;
	public static final int SIZE = 90;
	private int x,y;
	private boolean selected;
	
	public Dice(int x,int y){
		selected = true;
		this.x = x;
		this.y = y;
		if(diceImage == null){
			try {
				diceImage = ImageIO.read(getClass().getResource("/resources/Dice.png"));
				selectedImage = ImageIO.read(getClass().getResource("/resources/BlueDice.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		roll();
	}
	
	public int getValue(){ 
		return myNumber; 
	}
	
	public void reset(){
		selected = true;
		roll();
	}
	
	public void roll(){
		if(selected)myNumber = (int)(Math.random()*6)+1;
		selected = false;
	}

	public void paint(Graphics g) {
		int sx1=((myNumber-1)%3)*90;
		int sy1=((myNumber-1)/3)*90;
		BufferedImage drawMe = selected ? selectedImage : diceImage;
		g.drawImage(drawMe, x, y, x+SIZE, y+SIZE, sx1, sy1, sx1+SIZE, sy1+SIZE, null);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		if(mouseX>x && mouseX<x+SIZE && mouseY>y && mouseY< y+SIZE){
			selected = !selected;
			if(Game.g != null) Game.g.repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
