import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainClass extends JFrame implements Runnable {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	BufferedImage offscreen, loadingscreen;
	Graphics bg;
	Snake mySnake;
	Food myFood;
	boolean gameOver;
	int score, highScore;
	int loading = 30;

	static MainClass mc = new MainClass();

	public MainClass() {
		try {
			loadingscreen = ImageIO.read(MainClass.class
					.getResourceAsStream("/resources/loadingscreen.png"));
			Scanner in = new Scanner(new File("scores.txt"));
			highScore = in.nextInt();
			in.close();
		} catch (Exception e) {
		}
	
			
		score = 0;
		gameOver = false;
		myFood = new Food();
		mySnake = new Snake();
		offscreen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		bg = offscreen.getGraphics();
		Font f = (new Font("Impact", Font.PLAIN, 20));
		bg.setFont(f);
		this.addKeyListener(mySnake);
		new Thread(this).start();
	}

	public static void restart() {
		mc.reset();
	}

	public void reset() {
		this.removeKeyListener(mySnake);
		score = 0;
		gameOver = false;
		myFood = new Food();
		mySnake = new Snake();
		this.addKeyListener(mySnake);
		new Thread(this).start();
	}

	public static void main(String[] args) {
		mc.setSize(WIDTH, HEIGHT);
		mc.setResizable(false);
		mc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mc.setVisible(true);
	}

	public void paint(Graphics g) {
		if(loading>0){
			g.drawImage(loadingscreen, 0, 0, null);
			g.setColor(new Color(0xb6ffae));
			g.fillRect(WIDTH/2-220, HEIGHT/2+120, 15*loading, 20);
			return;
		}
		bg.setColor(new Color(0x6E6E6E));
		bg.fillRect(0, 0, WIDTH, HEIGHT);
		mySnake.draw(bg);
		myFood.draw(bg);

		bg.setColor(Color.white);
		bg.drawString("Score: " + score, WIDTH / 65, HEIGHT / 13);
		bg.drawString("High Score: " + highScore, WIDTH - WIDTH / 6 + 10,
				HEIGHT / 13);
		if (gameOver) {
			bg.setColor(Color.yellow);
			bg.drawString("GAME OVER", WIDTH / 2 - 46, HEIGHT / 2);

			if (gameOver) {
				bg.setColor(new Color(0xFA5F34));
				bg.drawString("RESTART (R)", WIDTH / 2 + 295, HEIGHT / 2 + 290);

			}
		}
		g.drawImage(offscreen, 0, 0, null);

	}

	@Override
	public void run() {
		while (!gameOver) {
			try {
				int sleepTime = 1000 / (10 + mySnake.length());
				if (sleepTime < 0)
					sleepTime = 0;
				Thread.sleep(sleepTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(loading>0){
				loading--;
				repaint();
				continue;
			}
			mySnake.update();
			if (mySnake.isOffScreen())
				gameOver = true;
			if (mySnake.hitSelf())
				gameOver = true;

			if (mySnake.eat(myFood)) {
				score++;
				myFood = new Food();

			}
			repaint();
		}
		if (score > highScore) {
			highScore = score;
			try {
				PrintWriter writer = new PrintWriter("scores.txt", "UTF-8");
				writer.println(highScore);
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
// make loading screen with image on desktop
