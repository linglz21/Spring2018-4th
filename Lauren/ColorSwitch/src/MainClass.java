import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class MainClass extends JFrame implements Runnable {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 900;

	Graphics bg;
	boolean gameOver;
	BufferedImage Offscreen;
	Ball myBall;
	ArrayList<Obstacle> allObstacles;

	public MainClass() {
		myBall = new Ball();
		allObstacles = new ArrayList<Obstacle>();

		for (int i = 0; i < 6; i++) {
			int x = (int) (Math.random() * WIDTH);
			allObstacles.add(new Obstacle(x + 0, 150 * i, 2,
					new Color(0xF20F5A)));// bright pink
			allObstacles.add(new Obstacle(x + 120, 150 * i, 2, new Color(
					0x7CFF70)));// green
			allObstacles.add(new Obstacle(x + 240, 150 * i, 2, new Color(
					0xF28A8A)));// pink,coral
			allObstacles.add(new Obstacle(x + 360, 150 * i, 2, new Color(
					0xA859F7)));// purple
			allObstacles.add(new Obstacle(x + 480, 150 * i, 2, new Color(
					0x32F0F0)));// blue
			allObstacles.add(new Obstacle(x + 600, 150 * i, 2, new Color(
					0xFFFF3D)));// yellow
		}
		gameOver = false;
		Offscreen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		bg = Offscreen.getGraphics();
		this.addKeyListener(myBall);
		new Thread(this).start();
	}

	public void reset() {
		gameOver = false;
		myBall = new Ball();
		
		new Thread(this).start();

	}

	public static void main(String[] args) {
		MainClass dj = new MainClass();
		dj.setSize(WIDTH, HEIGHT);
		dj.setResizable(false);
		dj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dj.setVisible(true);

	}

	public void paint(Graphics g) {
		if (bg == null)
			return;
		bg.setColor(Color.black);
		bg.fillRect(0, 0, WIDTH, HEIGHT);
		myBall.draw(bg);
		for (Obstacle ob : allObstacles)
			ob.draw(bg);
		if (gameOver) {
			bg.setColor(Color.yellow);
			bg.drawString("GameOver", WIDTH / 2, HEIGHT / 2);
		}
		g.drawImage(Offscreen, 0, 0, null);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			myBall.update();
			if(myBall.getY()<450){
				double dy = 450-myBall.getY();
				myBall.shift(dy);
				for(Obstacle ob : allObstacles) ob.shift(dy);
			}
			for (Obstacle ob : allObstacles){
				ob.update();
				if(myBall.hit(ob)){
					if(myBall.sameColor(ob)){
						myBall.changeColor();
					}else{
						return;
					}
				}
			}
		
			repaint();
		}
	}
}
