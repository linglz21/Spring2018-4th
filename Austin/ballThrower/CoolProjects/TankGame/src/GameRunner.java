import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameRunner extends JFrame implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	Map currentMap = null;
	private Button b;
	public static ArrayList<Sprite> sprites;
	public static ArrayList<Shot> shots;
	public static ArrayList<Tank> tanks;
	Graphics bufferGraphics;
	BufferedImage offscreen;
	Dimension dim;
	Image explosion;
	ArrayList<String> explosions;
	int totalPlayers = 2;
	int currentPlayer = 0;
	double wind = 0;

	boolean leftPressed, rightPressed, aPressed, dPressed, spacePressed,
			spaceReleased, wPressed, sPressed, qPressed, qReleased,
			ePressed, eReleased;

	/**
	 * @param players how many players
	 * @param names names of each player
	 */
	public GameRunner(int players, String[] names) {
		totalPlayers=players;
		this.setLayout(null);
		this.addKeyListener(this);

		//FileDialog fd = new FileDialog(this);
		//fd.setVisible(true);

		setBackground(Color.white);
		sprites = new ArrayList<Sprite>();
		currentMap = new Map("Map.jpg");//fd.getFiles()[0]);
		tanks = new ArrayList<Tank>();
		shots = new ArrayList<Shot>();
		explosions = new ArrayList<String>();
		offscreen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		bufferGraphics = offscreen.getGraphics();

		try {
			explosion = ImageIO.read(getClass().getResource("/resources/Explosion.png"))
					.getScaledInstance(-1, 30, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		leftPressed = false;
		rightPressed = false;

		for (int i = 0; i < totalPlayers; i++) {
			tanks.add(new Tank(currentMap, i,names[i]));
		}
		for (Tank t : tanks) {
			sprites.add(t);
		}

	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// System.out.println("test");
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			aPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			dPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			wPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			sPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			qPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			ePressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_P) {
			Tank temp = new Tank(currentMap, totalPlayers++,"test");
			tanks.add(temp);
			sprites.add(temp);
		}

		if (e.getKeyCode() == KeyEvent.VK_M) {
			Shot temp = tanks.get(currentPlayer).shoot();
			temp.addWind(wind / 20);
			sprites.add(temp);
			shots.add(temp);
			wind = ((Math.random() - .5) * 20);
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			spacePressed = true;
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			aPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			dPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			wPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			sPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			qReleased = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			eReleased = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			spaceReleased = true;
		}
	}

	/**
	 * 
	 */
	public void nextPlayer() {
		currentPlayer += 1;
		if (currentPlayer > totalPlayers - 1) {
			currentPlayer = 0;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		while (true) {
			for (int i = 0; i < shots.size(); i++) {
				if (checkTankDamage(shots.get(i))) {
					shots.get(i).explode();
					shots.get(i).exploded = true;

				}
				if (shots.get(i).exploded) {
					Shot s = shots.get(i);
					explosions.add((int) (s.x) + "," + (int) (s.y)+ ","+(int)s.r + ","
							+ 0);
					sprites.remove(shots.get(i));
					shots.remove(i);
					i--;
				}
			}
			if (totalPlayers != 0) {
				if (leftPressed) {
					tanks.get(currentPlayer).moveX(-1);
				}

				if (rightPressed) {
					tanks.get(currentPlayer).moveX(1);

				}
				if (aPressed) {
					tanks.get(currentPlayer).moveC(-0.02);
				}
				if (dPressed) {
					tanks.get(currentPlayer).moveC(0.02);
				}
				if (wPressed) {
					tanks.get(currentPlayer).changePower(.01);
				}
				if (sPressed) {
					tanks.get(currentPlayer).changePower(-.01);
				}
				if (spacePressed && spaceReleased) {
					Shot temp = tanks.get(currentPlayer).shoot();
					temp.addWind(wind / 20);
					sprites.add(temp);
					shots.add(temp);
					wind = ((Math.random() - .5) * 20);
					nextPlayer();
					spacePressed = false;
					spaceReleased = false;
				}
				if (qPressed && qReleased) {
					tanks.get(currentPlayer).changeShot(-1);
					qPressed = false;
					qReleased = false;
					
				}
				if (ePressed && eReleased) {
					tanks.get(currentPlayer).changeShot(1);
					ePressed = false;
					eReleased = false;
					
				}
				
				for (int i = 0; i < tanks.size(); i++) {
					if (tanks.get(i).health <= 0) {
						totalPlayers--;
						if (currentPlayer > tanks.get(i).number)
							currentPlayer--;
						Tank temp = tanks.get(i);
						tanks.remove(temp);
						sprites.remove(temp);
						
					}
				}
			}
			for (int i = 0; i < sprites.size(); i++) {
				sprites.get(i).update();
			}
			

			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
////////////Start Requirement:11 Graphical Interface

	public void paint(Graphics g) {
		bufferGraphics.setColor(Color.cyan);
		bufferGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		currentMap.paint(bufferGraphics);
		for (int i = 0; i < sprites.size(); i++) {
			sprites.get(i).paint(bufferGraphics);
		}
		drawExplosions(bufferGraphics);
		
		bufferGraphics.drawString("Aim: A&D     Power:W&S     Change Shots: Q&E    Move: Left&Right Arrow", 10, 60);

		drawHeader(bufferGraphics);
		g.drawImage(offscreen, 0, 0, this);
		offscreen.flush();
	}
	
	/**
	 * @param g Graphics g
	 */
	
	////////////Start Requirement:1 Integer wrapper class
	public void drawExplosions(Graphics g) {
		for (int i = 0; i < explosions.size(); i++) {
			String numbers = explosions.get(i);
			String[] arr = numbers.split(",");
			// for(String s : arr) System.out.println(s + ",");
			int x = Integer.parseInt(arr[0]);
			int y = Integer.parseInt(arr[1]);
			int r = Integer.parseInt(arr[2]);
			int t = Integer.parseInt(arr[3]);
			// System.out.println(x+" "+y+" "+t);
			g.drawImage(explosion, x - (int)(r*1.5), y - (int)(r*1.5), x + (int)(r*1.5), y + (int)(r*1.5), 0 + t * 30,
					0, 29 + t * 30, 29, this);
			t++;
			explosions.remove(i);
			if (t < 40)
				explosions.add(i, x + "," + y + "," +(int)r + ","+ t);
		}

	}
	////////////End Requirement:1 Integer wrapper class

	/**
	 * @param s shot to check if it is overlapping
	 * @return if there is damage being done
	 */
	private boolean checkTankDamage(Shot s) {
		boolean didDamage = false;
		for (int i = 0; i < tanks.size(); i++) {
			double x = tanks.get(i).x;
			double y = tanks.get(i).y - 7;
			double sX = s.x;
			double sY = s.y;
			if (Math.sqrt((x - sX) * (x - sX) + (y - sY) * (y - sY)) < 10) {
				double damage=s.damage +(Math.random()-.5)*s.damage;
				tanks.get(i).health -= damage;
				didDamage = true;
			}
			
		}
		return didDamage;
	}

	/**
	 * @return if there are any players in game
	 */
	public boolean tanksExist() {
		return totalPlayers != 0;

	}

	/**
	 * @param g Graphics g
	 */
	public void drawHeader(Graphics g) {
		g.setColor(Color.black);
		if (tanksExist())
			g.drawString(
					"Angle:"
							+ (int) -(tanks.get(currentPlayer).cannonAngle
									/ Math.PI * 180), 10, 40);
		if (tanksExist())
			g.drawString(
					"Power:" + (int) (tanks.get(currentPlayer).power * 20), 75,
					40);
		if (tanksExist())
			g.drawString("Wind:" + (int) wind, 150, 40);
		if (tanksExist())
			g.drawString("Current Player:" + currentPlayer, 200, 40);
		if (tanksExist())
			g.drawString("Shot Type:" + tanks.get(currentPlayer).getShotName(),
					320, 40);

	}

}
