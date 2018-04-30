import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Log {
	public double logX, logY, logV; 
	public static final int logWidth = 120;
	public static final int logHeight = 60;

	private BufferedImage theLog;
	Frog myFrog;
	
	public Log(double x, double y, double v){
		myFrog = null;
		logX = x;
		logY = y;
		logV = v*3;
		logY+=70;
		try {
			theLog = ImageIO.read(getClass().getResource("/resources/log.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public void paint(Graphics g){
		g.drawImage(theLog,(int) logX,(int) logY, logWidth, logHeight, null);
	}
	
	public void update(){
		logX += logV;
		if(myFrog != null) myFrog.frogX += logV;
		if(logX < -200)logX = GameRunner.WIDTH;
		if(logX > GameRunner.WIDTH)logX = -200;
		//if(myFrog != this.myLogs);
		//if(myFrog != isTouchingLog);
			
	}
	
	
	}


