import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Background {
	
	private BufferedImage Background = null;
	
	
	public static boolean Started;
	
	public Background(){
		if(Background == null){
			try {
				Background = ImageIO.read(getClass().getResource("/resources/background.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public void Paint (Graphics g){
		g.drawImage(Background, 0, 0, null);
		
		
	}
	
	
	
	
	
}
