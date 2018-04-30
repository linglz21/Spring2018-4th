import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;


public class Game extends JFrame implements Runnable{

	
	private static final long serialVersionUID = 1L;
	static final int WIDTH = 350;
	static final int HEIGHT = 500;
	private static final int SLEEP_TIME = 30;
	public static final int GROUND = 40;
	
	public static int scoreint;
	
	 
	
	
	BufferedImage buffer;
	static Graphics offscreen;
	
	public static Dude d;
	ArrayList<Brick> allBrick;

	private static void init(){
		Game g = new Game();
		g.setSize(WIDTH, HEIGHT); 
		g.setResizable(false); 
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		new Thread(g).start(); 
		g.setVisible(true);
		
		
	}
	
	public static void main(String[] args) {
		init();	
	}
	
	
	private Game(){
		
		buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		offscreen = buffer.getGraphics();
		
		Graphics2D g2 = (Graphics2D)offscreen;
	    RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    RenderingHints rh2 = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
	   g2.setRenderingHints(rh);
	    g2.setRenderingHints(rh2);
		
		d = new Dude();
		
		this.addKeyListener(d);
		allBrick = new ArrayList<Brick>();
		scoreint = 0;

		for (int i = 0; i <9 ; i++){
			allBrick.add(new Brick(HEIGHT - GROUND -55*(i+1)));
		
		}
		

	}
	
	
	public void paint(Graphics g){
		//setUndecorated(true);
		offscreen.setColor(new Color(1.0f,1.0f,1.0f,0.6f));
		//offscreen.setColor(Color.white);
		offscreen.fillRect(0, 0, WIDTH, HEIGHT);
		offscreen.setColor(Color.GREEN);
		offscreen.fillRect(0, HEIGHT - GROUND, WIDTH, GROUND);

		
		

		for (int i = 0; i <allBrick.size() ; i++){
		allBrick.get(i).draw(offscreen);
		}
		offscreen.setColor(Color.blue);
		Font f2 = offscreen.getFont().deriveFont(30f);
		offscreen.setFont(f2);
		offscreen.drawString("" + scoreint, 30, 70);
		d.paint(offscreen);
		
		if(Dude.over){
			offscreen.setColor(Color.black);
			Font f= offscreen.getFont().deriveFont(20f);
			offscreen.setFont(f);
			offscreen.drawString("Press SpaceBar to RESTART", WIDTH/2-150, HEIGHT/2);
			
		}
		
		g.drawImage(buffer, 0, 0, null);
		
		
	}

	@Override
	public void run() {
		while(true){
			try { 
				Thread.sleep(SLEEP_TIME); // Wait a short time so that the game doesn't run too fast
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			for (int i = 0; i<allBrick.size() ; i++){
				Brick b = allBrick.get(i);
				b.Jump();
				b.Falling();
				b.Roll();
		}
		
		
			
		d.update();
		repaint();
		
		
		
		}
	}
	
	
	
	
	
	
	
}
