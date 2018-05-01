import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Basketball{
	protected double x,y; 
	protected double vX, vY;
	private BufferedImage basketballImage;

	public Basketball(int x, int y, double vX, double vY, boolean isSmiley){
		this.x = x;
		this.y = y;
		this.vX = vX;
		this.vY = vY;
		try {
			if(isSmiley)basketballImage = ImageIO.read(getClass().getResource("/resources/SmileyFace.png"));
			else basketballImage = ImageIO.read(getClass().getResource("/resources/basketballImage.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	public void setLocation(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	
	public void push(double dX, double dY){
		vX += dX;
		vY += dY;
	}
	
	public void update(){
			x += vX;
			y += vY;
			if(y<Game.HEIGHT-40)vY += 0.1;
			if((x < 0 && vX<0) || (x > 840 && vX>0)){
				vX*=-1;
			}
			if(y > Game.HEIGHT-60 && vY>0){
				vY*=-0.5;
				if(Math.abs(vY)<1)vY = 0;
				vX*= 0.5;
			}
		
	}
	
	public boolean doHit(Hoop hoop, double hX, double hY) { 
		if((this.getX()+30)>hX && Math.abs(hY-this.getY())<20 && vY>=0){
			this.vX = 0;
			this.vY = 0;
			return true;
		}
		return false;
	}
	
	public void paint(Graphics g){
		g.drawImage(basketballImage, (int)x, (int)y, 60, 60, null);
	}
	
	

}
