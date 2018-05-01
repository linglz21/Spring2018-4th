import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;


public class GameRunner extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1800;
	public static final int HEIGHT = 1000;
	private BufferedImage offscreen;
	private Graphics buffer;
	private Frog myFrog;
	private Log myLog;
	public static boolean gameOver;
	
	private ArrayList<Log> myLogs;
	
	
	public GameRunner(){
		gameOver = false;
		this.setSize(WIDTH,HEIGHT);
		offscreen = new BufferedImage(this.WIDTH, this.HEIGHT, BufferedImage.TYPE_INT_RGB);
		buffer = offscreen.getGraphics();
		myFrog = new Frog();
		offscreen = new BufferedImage(this.WIDTH, this.HEIGHT, BufferedImage.TYPE_INT_RGB);
		buffer = offscreen.getGraphics();
		myLogs = new ArrayList<Log>();
		for(int row = 0 ; row<12 ; row++){
			double speed = Math.random()*2+1;
			for(int col=0 ; col<7 ; col++){
				if(row%2==0){
					myLogs.add(new Log(col*280,row*70,speed));
				}else{
					myLogs.add(new Log(col*280,row*70,-speed));
				}
			}
		}
		this.addKeyListener(myFrog);
	}
		
		
	public static void main(String[] args) {
		GameRunner myGame = new GameRunner();
		myGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myGame.setResizable(false);
		new Thread(myGame).start();
		myGame.setVisible(true);
			
	}
	
	
	private boolean frogOffScreen(){
		if(myFrog.frogX<0)return true;
		if(myFrog.frogX+myFrog.frogWidth>WIDTH)return true;
		if(myFrog.frogY<0)return true;
		if(myFrog.frogY+myFrog.frogHeight>HEIGHT)return true;
		return false;
		
	}
	public void paint(Graphics g){
		buffer.setColor(Color.blue);
		buffer.fillRect(0, 0, WIDTH, HEIGHT);
		for(Log l : myLogs)l.paint(buffer);
		myFrog.paint(buffer);
		buffer.setColor(Color.black);
		Font f = buffer.getFont();
		Font f2 = f.deriveFont(240f);
		buffer.setFont(f2);
        if(gameOver)buffer.drawString("GameOver", 300, 300);
		
		g.drawImage(offscreen, 0,0,null);
		
	}	
	
	@Override
	public void run() {
		while(!gameOver){
		
			myFrog.update();
			for(Log l : myLogs){
				l.update();
				l.myFrog = null;
				myFrog.isTouchingLog(l);
			}
			if(myFrog.isDying()){
				gameOver = true;
			}
			if(myFrog.isOffScreen()){
				gameOver = true;
			}
			repaint();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}


