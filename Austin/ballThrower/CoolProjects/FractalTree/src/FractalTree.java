
import java.awt.*;

import javax.swing.*;
import java.util.Timer;
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;

public class FractalTree extends JFrame implements MouseListener, Runnable 
{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 700;
    public BufferedImage offscreen;
    public Graphics bufferGraphics;
    private TreeBranch trunk;
    private Point base;
    public ArrayList<Leaf> myLeaves;

    public FractalTree()
    {
        myLeaves = new ArrayList<Leaf>();
        offscreen = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
        bufferGraphics = offscreen.getGraphics();
		Graphics2D g2 = (Graphics2D)bufferGraphics;
	    RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);        
        ((Graphics2D)bufferGraphics).setBackground(Color.blue);
        addMouseListener(this);
        base = new Point(WIDTH/2,HEIGHT-50);
        trunk = new TreeBranch(base,Math.PI);
        trunk.parent = this;

    }
    
    public static void main(String[] args) {
		FractalTree ft = new FractalTree();
		ft.setSize(WIDTH, HEIGHT);
		ft.setResizable(false);
		ft.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Thread(ft).start();
		ft.setVisible(true);
		
		
	}
    
    public void run(){
    	while(true){
    		try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        trunk.update(base);
	        paint(getGraphics());
	        for(Leaf l : myLeaves)l.update(0.5,0.5);
    	}
    }

    public void paint(Graphics g)
    {
    	if(g==null)return;
        bufferGraphics.clearRect(0,0,WIDTH,HEIGHT);
        bufferGraphics.setColor(Color.black);
        trunk.paint(bufferGraphics);
        for(Leaf l : myLeaves)l.paint(bufferGraphics);
        g.drawImage(offscreen,0,0,this);

    }
    
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}


}
