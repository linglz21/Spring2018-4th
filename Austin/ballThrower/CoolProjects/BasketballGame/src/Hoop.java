import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Hoop {
	protected int x, y;
	protected Color myColor;
	private static BufferedImage hoopImage = null;
	
	public Hoop(int x, int y){
		this.x = x;
		this.y = y;
		try {
			hoopImage = ImageIO.read(getClass().getResource("/resources/hoopImage2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	
	public void paint(Graphics g){
		g.drawImage(hoopImage, x,y, 85, 65, null);	
	}
	
}
