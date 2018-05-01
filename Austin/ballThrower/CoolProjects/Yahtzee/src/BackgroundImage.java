import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class BackgroundImage {
	private static BufferedImage scoreCardImg = null;
	private static BufferedImage rules = null;
	
	private boolean gameStarted;
	
	public BackgroundImage(){	
		gameStarted = false;
		if(scoreCardImg == null){
			try {
				scoreCardImg = ImageIO.read(getClass().getResource("/resources/ScoreCard.jpg"));
				rules = ImageIO.read(getClass().getResource("/resources/YahtzeeRules.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void startGame(){
		gameStarted = true;
	}

	public void paint(Graphics g) {
		if(gameStarted)g.drawImage(scoreCardImg, 0,40, null);
		else g.drawImage(rules, 0,40, null);
	}

}
