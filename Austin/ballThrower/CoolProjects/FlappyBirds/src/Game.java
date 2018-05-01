
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;


public class Game extends JFrame implements Runnable, KeyListener{

	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 500;
	public static Game g;
	private static final int SLEEP_TIME = 5;
	
	

	private Background Backimg;
	private Bird Birdy;
	private Pipes p;
	public boolean Over;
	
	BufferedImage buffer;
	static Graphics offscreen;
	
	public int scoreint = 0;



	public static void init(){
		g = new Game();
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
		
		addKeyListener(this);
		
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
		
		Backimg = new Background();
		Birdy = new Bird();
		this.addKeyListener(Birdy);
		
		p = new Pipes();
		Over= false;
		
	}
	
	
	public void paint(Graphics g){
		
	
		Backimg.Paint(offscreen);
		p.Paint(offscreen);
		Birdy.Paint(offscreen);
		
		offscreen.setColor(Color.black);
		Font f = offscreen.getFont().deriveFont(40f);
		offscreen.setFont(f);
		offscreen.drawString(""+scoreint,WIDTH/2-15, 100);
		
		if(!Birdy.start || Over){
			Font f2 = offscreen.getFont().deriveFont(10f);
			offscreen.setFont(f2);
			offscreen.setColor(Color.black);
			offscreen.drawString("Press SpaceBar", WIDTH/2-40, HEIGHT/2);
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
			if (!Over && Birdy.start){
				Birdy.update();
				p.update();	
			}if(Over){
				Birdy.Over();
			}
			repaint();	
			
		}
		
	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyPressed(KeyEvent e) {
		if(Over){
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
				Birdy.set();
				Over = false;
				p.pipeset();
				scoreint = 0;
				//reset pipes
				
				
			}
		}
		
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	
	
	
}
