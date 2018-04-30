import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainClass extends JFrame implements Runnable {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private int score = 0;
	

	BufferedImage offscreen, background;
	Graphics bg;
	Ball myBall;
	Paddle myPaddle;
	EPaddle myEPaddle;
	Score myScore;
	boolean gameOver;

	public static void main(String[] args) {
		MainClass mc = new MainClass();
		mc.setSize(WIDTH, HEIGHT+30);
		mc.setResizable(false);
		mc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mc.setVisible(true);
	}
	public void gameOver() {
		JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
		System.exit(ABORT);
	}

	public MainClass() {
		offscreen = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		bg = offscreen.getGraphics();
		gameOver = false;
		myBall = new Ball();
		myPaddle = new Paddle();
		myEPaddle = new EPaddle();
		myScore = new Score();
		this.addKeyListener(myPaddle);
	
		try{
			background = ImageIO.read(MainClass.class
					.getResourceAsStream("/Resources/WallPong.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		new Thread(this).start();
		
		}

	public void paint(Graphics g) {
		bg.drawImage(background, 0, 0, null);
		myBall.draw(bg);

		myPaddle.draw(bg);
		
		myEPaddle.draw(bg);
		
		myScore.draw(bg);

		g.drawImage(offscreen, 0, 30, null);
	}
	public void increaseScore(){
		score++;
	}


	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			myBall.update();
			if(myBall.isOffScreen()){
				reset();
			}
			myPaddle.update();
			myBall.hit(myPaddle);
			repaint();
			
			myEPaddle.update();
			myBall.hit(myEPaddle);
			repaint();
			}
		} 
	private void reset() {
		gameOver = false;
		myBall = new Ball();
	}

}



