import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Stone {
	
	private int dX,dY;
	private int myID;
	private static int nextID = 0;
	public static final int SIZE = 20;
	private static final int SCATTER = 30;
	private BufferedImage img;
	
	private double myX,myY;
	
	public Stone(int x, int y){
		movesLeft = 0;
		myX = x;
		myY = y;
		myID = nextID++;
		dX = (int)(Math.random()*2*SCATTER-SCATTER);
		dY = (int)(Math.random()*2*SCATTER-SCATTER);
		double distance = Math.sqrt(dX*dX + dY*dY);
		while(distance > SCATTER){
			dX = (int)(Math.random()*2*SCATTER-SCATTER);
			dY = (int)(Math.random()*2*SCATTER-SCATTER);
			distance = Math.sqrt(dX*dX + dY*dY);
		}
		img = randomImage();
	}
	
	public void spreadY(){
		dY *= 3;
	}
	
	public BufferedImage randomImage(){
		BufferedImage temp = null;
		int choice = (int)(Math.random()*5);	
		try { 
			temp = ImageIO.read(getClass().getResource("/resources/bead" + choice + ".png"));
		} catch (IOException e) {
		}
		return temp;
	}
	
	private int movesLeft;
	private double shiftX,shiftY;
	
	public void paint(Graphics g, int x, int y){
		double diffX = (x-myX);
		double diffY = (y-myY);
		if(movesLeft>1){
			myX += shiftX;
			myY += shiftY;
			movesLeft--;
		}else if(movesLeft==1){
			myX = x;
			myY = y;
			movesLeft--;			
		}else if(diffX!=0 || diffY!=0){

			movesLeft = 15;
			shiftX = diffX/15;
			shiftY = diffY/15;
		}
		g.drawImage(img,(int)(myX+dX-20),(int)(myY+dY-20),null);
	}
	
	boolean isStopped(){
		return movesLeft == 0;
	}

	@Override
	public String toString() {
		return "Stone " + myID ;
	}


}
