import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;


public class TheGame extends JFrame implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1500;
	public static final int HEIGHT = 500;
	
	private BufferedImage offscreen;
	private Ship myShip;
	private boolean canShoot;
	private Water myWater;
	private LockableList<CannonBall> myCannonBalls;
	private long timeOfLastShoot;
	
	public TheGame(){
		timeOfLastShoot = System.currentTimeMillis();
		canShoot = true;
		myShip = new Ship();
		myWater = new Water();
		myCannonBalls = new LockableList<CannonBall>();
		offscreen = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		new Thread(this).start();
		//this.addMouseListener(myWater);
		this.addKeyListener(myShip);
		this.addKeyListener(this);
	}
	
	public static void main(String[] args) {
		TheGame tg = new TheGame();		
		tg.setSize(WIDTH, HEIGHT);
		tg.setResizable(false);
		tg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tg.setVisible(true);
	}
	
	public void paint (Graphics g){
		Graphics bg = offscreen.getGraphics();
		bg.setColor(Color.white);
		bg.fillRect(0, 0, WIDTH, HEIGHT);
		myShip.draw(bg, myWater);
		myCannonBalls.acquire();
		for(CannonBall cb : myCannonBalls)cb.draw(bg);
		myCannonBalls.release();
		myWater.draw(bg);
		g.drawImage(offscreen, 0, 0, null);
	}

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// update
			myShip.update(myWater);
			myWater.update();
			myWater.update();
			myWater.update();
			
			myCannonBalls.acquire();
			for(int i=0 ; i<myCannonBalls.size() ; i++){
				CannonBall cb = myCannonBalls.get(i);
				cb.update();
				if(cb.getY()> myWater.getHeight((int)cb.getX())){
					myCannonBalls.remove(cb);
					myWater.splash((int)cb.getX());
				}
			}
			myCannonBalls.release();
			
			repaint();
			// repaint
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		long timeOfThisShoot = System.currentTimeMillis();
		if(timeOfThisShoot - timeOfLastShoot > 50 ) canShoot = true;
		timeOfLastShoot = timeOfThisShoot;
		if(e.getKeyCode() == KeyEvent.VK_SPACE && canShoot){
			canShoot = false;
			// add new cannonball
			double x = myShip.getX();
			double y = myWater.getHeight((int)x);
			myCannonBalls.acquire();
			if(myShip.isFacingLeft()){
				myCannonBalls.add(new CannonBall(x,y,-13,-13));
			}else{
				myCannonBalls.add(new CannonBall(x,y,13,-13));
			}
			myCannonBalls.release();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
