import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class TheGame extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH=400;
	public static final int HEIGHT=800;
	private int tbp; // The number of clock ticks between platform creation
	private int currentTick;
	private BufferedImage theBuffer;
	private Graphics offScreen;
	private ArrayList<Platform> allPlatforms;
	private Man theMan;
		
	public TheGame(){
		theMan = new Man();
		addKeyListener(theMan);
	    Platform p = new Platform();
	    p.setX(WIDTH/2);
		allPlatforms = new ArrayList<Platform>();
		allPlatforms.add(p);
		tbp = 20;
		currentTick = 0;
		theBuffer = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_BGR);
		offScreen = theBuffer.getGraphics();
		new Thread(this).start();
	}
	
	public static void main(String[] args) {
		TheGame dj = new TheGame();
		dj.setSize(WIDTH,HEIGHT);
		dj.setResizable(false);
		dj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dj.setVisible(true);
	}
	
	public void paint(Graphics g){
		if(offScreen==null)return;
		offScreen.setColor(Color.black);
		offScreen.fillRect(0, 0, WIDTH, HEIGHT);
		for(Platform p : allPlatforms)p.paint(offScreen);
		theMan.paint(offScreen);
		g.drawImage(theBuffer, 0, 0, this);
	}

	@Override
	public void run() {
		while(true){
			theMan.update();
			currentTick++;
			if(currentTick==20){
				allPlatforms.add(new Platform());
				currentTick = 0;
			}
			double manX = theMan.getX();
			double manY = theMan.getY();
			for(int i=0 ; i<allPlatforms.size() ; i++){
				Platform p = allPlatforms.get(i);
				p.update();
				if(p.hitTest(manX, manY)) theMan.bounce();
				if(p.isDead())allPlatforms.remove(i);
			}
			
			repaint();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

}
