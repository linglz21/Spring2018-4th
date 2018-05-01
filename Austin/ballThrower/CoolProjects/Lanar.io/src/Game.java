import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

//Responsible for displaying items received from client
public class Game extends JFrame {
	public BufferedImage offscreen;
	private static final long serialVersionUID = 1L;
	public static int WIDTH;
	public static int HEIGHT;
	Client client;
	public static Game self;
	private GraphicsDevice gd;
	Game(Client client, int w, int h) {
		this.setVisible(true);
		WIDTH = w;
		HEIGHT = h;
		this.setSize(WIDTH, HEIGHT);
		offscreen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		this.client = client;
		self = this;
		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    if (gd.isFullScreenSupported() && Menu.fullScreen.isSelected()) {
	    	gd.setFullScreenWindow(this);
	    }
	}
	
	public void paint(Graphics g) {
		//System.out.println("[Game] Painting graphics");
		Graphics bg = offscreen.getGraphics();
		Graphics2D bg2 = (Graphics2D)bg;
		//bg2.scale(2, 2);
		client.draw(bg);
		g.drawImage(offscreen, /*client.userPlayer.getCenter().x-WIDTH/2*/0, /*client.userPlayer.getCenter().y-HEIGHT/2*/0, null);

	}

}
