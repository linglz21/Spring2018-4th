import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class MainClass extends JFrame implements Runnable {

	public static final int WIDTH = 800;
	public static final int SHIFT = 25;
	public static final int HEIGHT = WIDTH + SHIFT;
	private Color myColor;
	BufferedImage offscreen, background;
	Graphics bg;
	Bike playerOne, playerTwo;

	public MainClass() {
		playerOne = new Bike(125, 420, true);
		playerTwo = new Bike(665, 420, false);
		offscreen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		try {
			background = ImageIO.read(MainClass.class
					.getResourceAsStream("/resources/tronarena.png"));
		} catch (IOException e) {
			// e.printStackTrace();
		}
		this.addKeyListener(playerOne);
		new Thread(this).start();
	}

	public static void main(String[] args) {
		MainClass mc = new MainClass();
		mc.setSize(WIDTH, HEIGHT);
		mc.setResizable(false);
		mc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mc.setVisible(true);

	}

	public void paint(Graphics g) {
		g.drawImage(offscreen, 0, 0, null);
		g.drawImage(background, 0, SHIFT, null);
		playerOne.draw(g);
		playerTwo.draw(g);

	}


	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			playerOne.update();
			repaint();
		}

	}
}