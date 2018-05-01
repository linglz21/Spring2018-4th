/**
 * TestTank.java  01/04/12
 *
 * @author - Wayne Ryan
 * @author - Period W
 * @author - Revision 1
 *
 * @author - I received help from ...
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;


public class TestTank extends JFrame implements MouseListener, MouseMotionListener, Runnable
{
	private static final long serialVersionUID = 1L;
	public FishTank myTank;

	private BufferedImage offscreen;
	private Graphics bufferGraphics;
	

/*
	private BufferedImage background;
	private BufferedImage rendered;
	private double heightMap [][][];
	private int flip;
*/
	private Water myWater;

	public TestTank()
	{
		offscreen = new BufferedImage(FishTank.WIDTH,FishTank.HEIGHT, BufferedImage.TYPE_INT_RGB);
		bufferGraphics = offscreen.getGraphics();
		Graphics2D g2 = (Graphics2D)bufferGraphics;
	    RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
	    
		addMouseListener(this);
		addMouseMotionListener(this);
		myWater = new Water(FishTank.WIDTH,FishTank.HEIGHT);
		myTank = new FishTank(myWater);

	}
	
	public static void main(String[] args) {
		TestTank tt = new TestTank();
		tt.setSize(FishTank.WIDTH, FishTank.HEIGHT);
		tt.setResizable(false);
		tt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Thread(tt).start();
		tt.setVisible(true);
		
	}
	
	public void paint(Graphics g)
	{

		if(myWater != null) myWater.draw(bufferGraphics, this);
		if(myTank != null) myTank.draw(bufferGraphics);
		g.drawImage(offscreen, 0, 0, this);
	}

	public void run()
	{
		while(true){
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			myTank.update();
			repaint();
		}

	}

	private int i = 0;
	public void mouseDragged(MouseEvent e)
	{
		if (i++ % 2 == 0)
		{
			int x = e.getX();
			int y = e.getY();
			Location loc = new Location(x, y);
			myTank.addObject(new Food(loc, new Location(Math.random() - 0.5, Math.random() - 0.5)));
		}
	}
	public void mouseMoved(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}

}