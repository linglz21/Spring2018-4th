import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BackgroundImage {
	private static BufferedImage courtImage = null;
	private static BufferedImage rules = null;

	public static boolean gameStarted;
	
	public BackgroundImage(){	
		gameStarted = false;
		if(courtImage == null){
			try {
				courtImage = ImageIO.read(getClass().getResource("/resources/courtImage2.jpg"));
				rules = ImageIO.read(getClass().getResource("/resources/rules.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void startGame(){
		gameStarted = true;
	
	}

	public void paint(Graphics g) {
		if(gameStarted)g.drawImage(courtImage, 0,25, null);
		else g.drawImage(rules, 0,40, null);
	}
	
}
