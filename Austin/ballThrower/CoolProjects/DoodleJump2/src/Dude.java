import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class Dude implements KeyListener{
	
	
	private static BufferedImage dude = null;
	
	public static int x, y;
	public static int platform;
	private static double vY; 
	private static double vX;
	private static boolean left, right;
	public static boolean isFalling;
	
	public static boolean RollDown;
	public static int GoDown;
	
	public static boolean over;

	
	public static final int WIDTH = 24, HEIGHT = 40;
	
	public void start(){ // values only for the starting of the game
		y=  Game.HEIGHT - Game.GROUND-HEIGHT;
		x = Game.WIDTH/2 - WIDTH/2;
		platform = y;
		
	}
	
	public static void set(){
		vY = -10;
	}
	
	public static int getX(){return x;}
	public static int getY(){return y;}
	public static int getvY(){return (int)vY;}
	
	public Dude(){
		if (dude == null){
			try {
				dude = ImageIO.read(getClass().getResource("/Resources/gg.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		start();
		set();
		isFalling = false;
		RollDown = false;
		vX = 0;
		over= false;
		
	}
	
	public void bounce(){ //method for making it constantly go up and down
		if (vY < 0){
			y +=vY;
			if(vY == 0){
				vY =1;
			}
		}if (vY > 0){
			y += vY;
			if(vY == 11){
				vY = -10;
			}
		}
		vY += 0.85;
	}
	

	
	public static void falling(){
		vY = 10;
		vY += 2;
		y += vY;
	}
	
	
	public void update(){
		vX *= 0.9;//friction
		x += vX;
		if(left)vX -= 2;
		if (right)vX += 2;
		
		
		
		
		
		if(y+HEIGHT > Game.HEIGHT-10 && !over){
			isFalling = false;
			set();
			System.out.println("BOTTOM");
			over =true;
		}
		
		if(!isFalling && !over){bounce();}
		if(isFalling){falling();}
		
		if(y < Game.HEIGHT - Game.GROUND- 300 ){RollDown = true;}
		
		if(RollDown){
			GoDown++; 
			Game.scoreint +=GoDown*2;
		}if(GoDown == 15){
			RollDown = false;
			GoDown = 0;
		}
		
		// if too left or right pops from the other side
		if(x< 0 - WIDTH){x = Game.WIDTH;}
		if(x> Game.WIDTH){x= 0;}
		
		
	}
	
	
	
	
	public void paint(Graphics g){
		g.drawImage(dude, x, y, null);
	}

	
	
	public static void Jump(Brick b) {
		if(platform>b.y){
			Game.scoreint += b.y*2;
		}
		y = b.y-HEIGHT;
		set();
	}
	
	
	
	
	
	@Override
	public void keyTyped(KeyEvent e) {	}

	@Override
	public void keyPressed(KeyEvent e) {
	if(e.getKeyCode() == KeyEvent.VK_LEFT){
		if (!over){left = true;}
		}
	
	if(e.getKeyCode() == KeyEvent.VK_RIGHT){
		if (!over){
			System.out.println(over);
			right = true;}
	}
	if(e.getKeyCode() == KeyEvent.VK_SPACE){
		y = Game.HEIGHT-HEIGHT-10;
		over  =false;
		left = false;
		right = false;
		Game.scoreint = 0;
		start();
		set();
	}
	
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(!over){
				left = false;	
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(!over){
				right = false;	
			}
			
		}
		
		
		
	}

	
	
}
