import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;


public class MyMain extends JFrame implements Runnable, KeyListener{
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 750;
	
	//LOCATION AND SIZE OF THE JOGGER DUDE
	public static final int x = WIDTH/2;
	public static final int y = HEIGHT - 50;
	public static final int h = 20;
	public static final int w = 20;
	
	private ArrayList<Square> mySquares;
	private ArrayList<Sparky> mySparks;
	
	private BufferedImage offscreen;
	private Graphics buffer;
	
	private boolean leftPressed, rightPressed;
	private double spawnRate;
	
	public MyMain(){
		spawnRate = 0.05;
		leftPressed = rightPressed = false;
		offscreen = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		buffer = offscreen.getGraphics();
		mySquares = new ArrayList<Square>();
		mySparks = new ArrayList<Sparky>();
		this.setSize(WIDTH, HEIGHT);
		addKeyListener(this);
		
	}
	private static final long serialVersionUID = 1L;
	private static final Square s = null;

	public static void main(String[] args) {
		MyMain theApplication = new MyMain();
		theApplication.setVisible(true);
		theApplication.setResizable(false);
		new Thread(theApplication).start();
		theApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void paint(Graphics g){
		buffer.setColor(Color.black);
		buffer.fillRect(0, 0, WIDTH, HEIGHT);
		buffer.setColor(Color.magenta); 
		int[] xLocs = new int[3];
		int[] yLocs = new int[3];
		xLocs[0] = x-w/2;
		xLocs[1] = x;
		xLocs[2] = x+w/2;
		yLocs[0] = y;
		yLocs[1] = y-h;
		yLocs[2] = y;
		buffer.fillPolygon(xLocs, yLocs, 3);
		for(Square s: mySquares)s.paint(buffer);
		for(int i=0 ; i<mySparks.size(); i++){
			Sparky s = mySparks.get(i);
			s.draw(buffer);
		}
		g.drawImage(offscreen, 0, 0, null);
	}
	
	public void run() {
		while(true){
			for(Square s: mySquares){
				s.update();
				if(s.didHitJogger()){
					mySparks.addAll(Sparky.explode(x, y));
					//make the jogger explode 
					//stop the game
				}
			}
			for(int i=0 ; i<spawnRate ; i++){
				if(Math.random()<spawnRate)mySquares.add(new Square() );
			}
			spawnRate += 0.0001;
			for(int i=0 ; i<mySquares.size() ; i++){
				Square s = mySquares.get(i);
				if(s.isOffScreen())mySquares.remove(s);
			}
			for(int i=0 ; i<mySparks.size() ; i++){
				Sparky s = mySparks.get(i);
				s.update();
				if(s.getAge() > 6)mySparks.remove(s);
			}
			if(Math.random()>0.9)mySquares.add(new Square() );
			if(leftPressed)Square.vX += 0.5;
			if(rightPressed)Square.vX -= 0.5;
			Square.vX *= 0.99;
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == e.VK_LEFT) leftPressed = true;
		if(e.getKeyCode() == e.VK_RIGHT) rightPressed = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == e.VK_LEFT) leftPressed = false;
		if(e.getKeyCode() == e.VK_RIGHT) rightPressed = false;
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
