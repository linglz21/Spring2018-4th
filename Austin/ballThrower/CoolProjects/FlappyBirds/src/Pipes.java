import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class Pipes {

	private static BufferedImage PipeBottom;
	private static BufferedImage PipeUp;
	private static double x,x2;
	private static int random, random2;
	private static int randomp, randomp2;
	
	public void pipeset(){
		
		x= -70;
		x2 = -100;
		random = (int)(Math.random()*3);
		random2 = (int)(Math.random()*3);
		randomp = (int)(Math.random()*40);
		randomp2 = (int)(Math.random()*40);

		
	}
	
	
	public Pipes(){
		try {
			PipeBottom = ImageIO.read(getClass().getResource("/resources/pipe-bottom.png"));
			PipeUp = ImageIO.read(getClass().getResource("/resources/pipe-top.png"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		pipeset();
	}
	
	// || -504+random*100 + 604 + 100+randomp< 504+random*100+randomp - lift + 604
	public void update(){		
		x += 0.9;
		x2 += 0.9;
		
		
		if(x>Game.WIDTH){
			x = -70;
			random = (int)(Math.random()*3);
			randomp = (int)(Math.random()*40);
			
		}if(x2>Game.WIDTH/2+Game.WIDTH){
			x2 = -70 + Game.WIDTH/2;
			random2 = (int)(Math.random()*3);
			randomp2 = (int)(Math.random()*40);
			
		}
	}
	
	
	public void Paint(Graphics g){
		
		g.drawImage(PipeUp, Game.WIDTH-(int)(x)-70, -504+random*100+randomp , null);
		g.drawImage(PipeBottom, Game.WIDTH-(int)(x)-70, -504+random*100 + 604 + 100+randomp, null);
		
		g.drawImage(PipeUp, Game.WIDTH+Game.WIDTH/2-(int)(x2)-70, -504+random2*100 + randomp2, null);
		g.drawImage(PipeBottom, Game.WIDTH+Game.WIDTH/2-(int)(x2)-70, -504+random2*100 + 604 + 100 + randomp2, null);
		}
	
	

	public static int getX(int a){
		if (a == 1){
			a =  Game.WIDTH-(int)(x)-70;
		}if (a== 2){
			a =  Game.WIDTH+Game.WIDTH/2-(int)(x2)-70;
		}
		return a;
	}
	
	public static int getY (int a){
		if (a == 1){
			a = -504+random*100 + randomp;
		}if (a == 2){
			a = -504+random2*100 + randomp2;
		}
		return a;
	}
}




