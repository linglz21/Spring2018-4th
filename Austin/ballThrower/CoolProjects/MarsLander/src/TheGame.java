import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;


public class TheGame extends Applet implements Runnable, MouseListener{

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;
	public static double gravity;
	
	private SpaceShip theShip;
	private LandingPad thePad;
	private LockableList<Spark> allSparks;
	private StartScreen ss;
	private EndScreen es;
	private Object currentScreen;
	private boolean done;
	
	private BufferedImage offscreen;
	
	public void init(){
		done = true;
		currentScreen = ss = new StartScreen();
		es = new EndScreen();
		this.setSize(WIDTH,HEIGHT);
		gravity = 0.05;
		thePad = new LandingPad();
		allSparks = new LockableList<Spark>();
		offscreen = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		addMouseListener(this);
		new Thread(this).start();
		this.requestFocus();
	}
	
	public void paint(Graphics g){
		Graphics g2 = offscreen.getGraphics();
		if(g2 == null)return;
		if(currentScreen == ss){
			ss.paint(g);
			return;
		}
		
		g2.setColor(Color.black);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		if(currentScreen == es)es.paint(g2);
		theShip.paint(g2);
		thePad.paint(g2);
		allSparks.aquire();
		for(Spark s : allSparks)s.paint(g2);
		allSparks.release();
		g.drawImage(offscreen,0,0,this);
	}

	@Override
	public void run() {
		while(true){
			// update everything
			allSparks.aquire();
			if(!done)allSparks.addAll(theShip.update());
			for(int i=0 ; i<allSparks.size() ; i++){
				Spark s = allSparks.get(i);
				if(s.update())allSparks.remove(s);
			}
			allSparks.release();
			if(!done){
				if(thePad.hitTest(theShip.getX(), theShip.getY())){
					if(theShip.getSpeed()>1.5){
						allSparks.aquire();
						allSparks.addAll(theShip.explode());
						allSparks.release();
					}
					currentScreen = es;
					done = true;
					theShip.pause();
				}
				if(theShip.isOffScreen()){
					allSparks.aquire();
					allSparks.addAll(theShip.explode());
					allSparks.release();
					currentScreen = es;
					done = true;
				}
			}
			// repaint everything
			this.paint(getGraphics());
			// sleep for a short while
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		if(currentScreen == ss){
			done = false;
			theShip = new SpaceShip();
			addKeyListener(theShip);
			// do initialization
			currentScreen = null;
		}
		else if(currentScreen == es){
			currentScreen = ss;
		}
	}


}
