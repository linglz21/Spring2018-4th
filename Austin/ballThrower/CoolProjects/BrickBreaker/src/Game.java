import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;


public class Game extends JFrame implements Runnable{

	private static final long serialVersionUID = 1L;
	
	Puck myPuck;
	Paddle myPaddle;
	ArrayList<Brick> myBricks;
	
	BufferedImage offscreen;
	
	public Game(){
		myPuck = new Puck();
		myPaddle = new Paddle();
		myBricks = new ArrayList<Brick>();
		for(int row = 0; row<4; row++) {
			for(int col=0; col<(800/75); col++) {
				myBricks.add(new Brick(col*(75+2), row*(20+2)));
			}
		}
		this.addKeyListener(myPaddle);
		new Thread(this).start();
		offscreen = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		
	}
	
	public static void main(String[] args) {
		Game g = new Game();
		g.setSize(800, 600);
		g.setResizable(false);
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g.setVisible(true);
		
		
	}
	
	public void paint(Graphics g) {
		Graphics bg = offscreen.getGraphics();
		bg.setColor(Color.white);
		bg.fillRect(0, 0, 800, 600);
		myPuck.draw(bg);
		myPaddle.draw(bg);
		for(Brick b : myBricks)b.draw(bg);
		g.drawImage(offscreen, 0, 0, null);
	}

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//myPuck.update();
			myPuck.hitPaddle(myPaddle);
			for (int i=0; i < myBricks.size(); i++) {
				Brick b = myBricks.get(i);
				if (myPuck.hitBrick(b)) {
					myBricks.remove(b);
				}
			}
			myPuck.update();
			this.repaint();
		}
		
	}
}
