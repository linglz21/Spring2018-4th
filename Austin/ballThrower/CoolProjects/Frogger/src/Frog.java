import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Frog implements KeyListener{
	public double frogX, frogY, V; 
	public static final int frogWidth = 39;
	public static final int frogHeight = 40;

	private BufferedImage myImage;
	private boolean shouldDie;

	public Frog(){
		shouldDie = true;
		frogX = 850;
		frogY = 845;
		V=5;
		try {
			myImage = ImageIO.read(getClass().getResource("/resources/frog.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private final int jumpDistance = 70;
	@Override
	public void keyPressed(KeyEvent e) {
		if(GameRunner.gameOver)return;
		switch(e.getKeyCode()){
			case KeyEvent.VK_UP    : frogY -= jumpDistance; break;
			case KeyEvent.VK_DOWN  : frogY += jumpDistance; break;
			case KeyEvent.VK_LEFT  : frogX -= jumpDistance; break;
			case KeyEvent.VK_RIGHT : frogX += jumpDistance; break;
		}
	}
	
	public void update(){
		shouldDie = true;
	}
	
	public boolean isDying(){return shouldDie;}
	
	
	
	public void isTouchingLog(Log someLog){
		if(this.frogX+this.frogWidth < someLog.logX)return;
		if(this.frogX > someLog.logX + someLog.logWidth)return;
		if(this.frogY+this.frogHeight < someLog.logY)return;
		if(this.frogY > someLog.logY + someLog.logHeight)return;
		someLog.myFrog = this;
		shouldDie = false;
	}

	
	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void paint(Graphics g){
		g.drawImage(myImage,(int) frogX,(int) frogY, null);
	}


	public boolean isOffScreen() {
		if(frogX<0)return true;
		//if(frogX+frogWidth>WIDTH)return true;
		if(frogY<0)return true;
		//if(frogY+frogHeight>HEIGHT)return true;
		return false;
	}

}
