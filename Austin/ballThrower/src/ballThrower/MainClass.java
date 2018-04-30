package ballThrower;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class MainClass extends JFrame implements Runnable, MouseListener {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private int score = 0;
	public boolean gameOver;
	public boolean targetHit;
	
	private BufferedImage offscreen;
	private Graphics bg;
	private Target myTarget;
	
	

	
	private LockableList<Ball>allBalls;
	private LockableList<Ball>stoppedBalls;
	public static boolean testIntersection(){
		return true;
		
	}
	

	public MainClass(){
		myTarget = new Target();
		allBalls = new LockableList<Ball>()	;
		stoppedBalls = new LockableList<Ball>()	;
		offscreen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		bg = offscreen.getGraphics();
		this.addMouseListener(this);
		new Thread(this).start();
		
	}
	

		


	public static void main(String[] args) {
		MainClass mc = new MainClass();
		mc.setSize(WIDTH, HEIGHT);
		mc.setResizable(false);
		mc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mc.setVisible(true);
		
		
		
	}
	

	
		
	
	public void paint (Graphics g){
		bg.setColor(Color.BLACK);
		bg.fillRect(getX(), getY(), WIDTH, HEIGHT);
		bg.setColor(Color.yellow);
		bg.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		bg.drawString("Score: " + score, WIDTH/6, HEIGHT-500);
		myTarget.draw(bg);
		allBalls.acquire();
		for (Ball p : allBalls)
			p.draw(bg);
		for (Ball p : stoppedBalls)
			p.draw(bg);
		allBalls.release();	
		if(targetHit)bg.drawString("You Scored!", HEIGHT/2, WIDTH/2);	
		g.drawImage(offscreen, 0, 0, null);


	}
	
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(30);
				for (int i = 0; i < allBalls.size(); i++) {
					Ball p = allBalls.get(i);
					p.update();
					allBalls.acquire();
					if (p.isOffScreen())
						allBalls.remove(p);
					allBalls.release();
					if(myTarget.hit(p)){
						score += 1;
						allBalls.remove(p);
						stoppedBalls.add(p);
						targetHit = true;
						
					if (p.isOffScreen())
						allBalls.remove(p);
							
					
					}
					
				}
				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
	}

	}
	
	

	private MouseEvent press;

	@Override
	public void mousePressed(MouseEvent e) {
		press = e;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		double dX = press.getX()-x;
		double dY = press.getY()-y;
		
		allBalls.add(new Ball(x,y,-dX*.1,-dY*.1));
		
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

	
}

