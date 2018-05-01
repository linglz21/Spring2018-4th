import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class Bird implements KeyListener{
	
	private double y,x;
	private double Vy;
	private static final int WIDTH = 24;
	private static final int HEIGHT = 17;
	private static BufferedImage Birdie = null;
	private static BufferedImage Birdie2 = null;
	private boolean disabled;
	public boolean start; // used for signaling whether game has started or not
	private int change;
	
	public void set(){
		
		y= Game.HEIGHT/2;	
		Vy = 1;
		x = Game.WIDTH/2-100;
		disabled = false;
		start = false;
		
	}
	
	
	public Bird(){
		
		if (Birdie == null || Birdie2 == null){
			try {
				Birdie = ImageIO.read(getClass().getResource("/resources/Bird1.png"));
				Birdie2 = ImageIO.read(getClass().getResource("/resources/Bird2.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		set();
		
	}
	
	
	public void update(){
	
		
		
		if(y+25 >= Game.HEIGHT){
			Game.g.Over = true;
			return;
		}
		
		y += Vy;
		Vy += 0.03;	 
		
		change++;
		int pY = Pipes.getY(1);
		int pX = Pipes.getX(1);
		int pY2 = Pipes.getY(2);//upper pipe
		int pX2 = Pipes.getX(2);
		
		
		if(pY+604< y&& pY+604+ 105> y + HEIGHT && x == pX+ 25){
			Game.g.scoreint ++;
		}if(pY2+604< y&& pY2+604+ 105> y + HEIGHT && x == pX2+ 25){
			Game.g.scoreint ++;
		}
		
		if((x+ WIDTH < pX+10 || y > pY+604 || x> pX + 50) && (x+ WIDTH < pX +10|| y+ HEIGHT < pY + 604+ 100 || x> pX + 50)) {
			if((x+ WIDTH < pX2+10 || y > pY2+604 || x> pX2 + 50) && (x+ WIDTH < pX2 +10|| y+ HEIGHT < pY2 + 604+100|| x> pX2 + 50))return;
			if(!(disabled)) {
				disabled = true;
				try { 
					Thread.sleep(200); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				Vy = 10;
			}
			
		}if(!(disabled)){
			System.out.println(y);
			disabled = true;
			try { 
				Thread.sleep(200); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Vy = 10;
		}
			
	}
	
	
	
	public void Over(){
			Vy *= 0;
		
	}
	
	
	public void Paint(Graphics g){
		if(change %30 < 15){
			g.drawImage(Birdie, (int) x,(int)y, null);	
			
		}if(change %30 >14 ){
			g.drawImage(Birdie2, (int) x,(int)y, null);
		
		}
		
	}




	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void keyPressed(KeyEvent e) {
		if(!disabled){
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
				Vy = -2;
				start = true;
			}
		}
		
		
	}




	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
