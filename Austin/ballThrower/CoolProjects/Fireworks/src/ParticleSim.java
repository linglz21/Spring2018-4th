import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;

public class ParticleSim extends JFrame implements Runnable,MouseListener{
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;
	private ArrayList<Particle> allParticles;
	private ArrayList<Projectile> allProjectiles;
	private boolean done;
	private Semaphore lock;

	private Graphics offscreen;
	private BufferedImage theBuffer;
	
	private int pressX,pressY;

	public ParticleSim(){
		lock = new Semaphore(1);
		done = false;
		allParticles = new ArrayList<Particle>();
		allProjectiles = new ArrayList<Projectile>();
		addMouseListener(this);
		theBuffer = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		offscreen = theBuffer.getGraphics();
		Graphics2D g2 = (Graphics2D)offscreen;
	    RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
	}
	
	public static void main(String[] args) {
		ParticleSim p = new ParticleSim();
		p.setSize(WIDTH,HEIGHT);
		p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Thread(p).start();
		p.setVisible(true);
	}
	
	private void getLock(){
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	private void releaseLock(){
		lock.release();
	}
	
	public void paint(Graphics g){
		if(g==null)return;
		offscreen.setColor(new Color(0.0f,0.0f,0.0f,0.05f));
		Dimension dim = this.getSize();
		offscreen.fillRect(0, 0, dim.width, dim.height);
		getLock();
		for(int i=0 ; i<allParticles.size(); i++){
			Particle p = allParticles.get(i);
			p.paint(offscreen);
		}
		for(int i=0 ; i<allProjectiles.size() ; i++){
			Projectile p = allProjectiles.get(i);
			p.paint(offscreen);
		}
		releaseLock();
		g.drawImage(theBuffer, 0, 0, this);
		
	}

	@Override
	public void run() {
		while(!done){
			getLock();
			for(int i=0 ; i<allParticles.size(); i++){
				Particle p = allParticles.get(i);
				p.update();
				if(p.isDead()){
					allParticles.remove(p);
				}
			}
			for(int i=0 ; i<allProjectiles.size() ; i++){
				Projectile p = allProjectiles.get(i);
				p.update();
				if(p.isDead()){
					allProjectiles.remove(p);
					double pX = p.getX();
					double pY = p.getY();
					for(Particle pa : allParticles){
						double dX = pa.getX() - pX;
						double dY = pa.getY() - pY;
						double distance = Math.sqrt(dX*dX + dY*dY);
						distance/= 30;
						dX /= distance*distance*distance;
						dY /= distance*distance*distance;
						pa.push(dX, dY);
					}
					for(int j=0 ; j<200 ; j++){
						allParticles.add(new Particle(p.getX(),p.getY()));
					}
				}
			}
			releaseLock();
			paint(this.getGraphics());
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {
		pressX = arg0.getX();
		pressY = arg0.getY();		
	}
	public void mouseReleased(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		int vX = x-pressX;
		int vY = y-pressY;
		getLock();
		allProjectiles.add(new Projectile(x,y,vX/5,vY/5));
		releaseLock();
		
	}

}
