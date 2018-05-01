
import java.awt.*;
import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.*;
import java.util.*;

public class MovingFaces extends JFrame implements MouseListener, MouseMotionListener, KeyListener
{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Face> myFaces;
	
    private BufferedImage offscreen;
    private Graphics bufferGraphics;
    public Dimension dim;
    
    public static final int HEIGHT = 700;
    public static final int WIDTH = 1000;
    
    private int x,y;
    Face selected;
    
    public static void main(String[] args) {
    	MovingFaces t = new MovingFaces();
    	t.setResizable(false);
    	t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	t.setVisible(true);
	}
    

    public MovingFaces()
    {
    	setSize(WIDTH,HEIGHT);
        dim = getSize();
        offscreen = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB); 
        bufferGraphics = offscreen.getGraphics();
        myFaces = new ArrayList<Face>();
        for(int i=0 ; i<200 ; i++){
            int x = (int)(Math.random()*(dim.width-80))+10;
            int y = (int)(Math.random()*(dim.height-80))+10;
            Face temp = new Face(x,y);
            
            boolean overlaps = false;
            for( Face f : myFaces){
                if( f.overlaps(temp)) overlaps = true;
            }
            if(! overlaps)myFaces.add(temp);
        }
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
    }


    public void paint(Graphics g)
    {
        bufferGraphics.setColor(Color.black);
        bufferGraphics.fillRect(0,0,dim.width,dim.height);
        for(Face f : myFaces){
            f.draw(bufferGraphics);
        }
        g.drawImage(offscreen,0,0,null);
    }
    
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_SPACE ){
        	myFaces.add(new Face(x-30,y-30));
        }
        repaint();
    }
    
    public void keyReleased(KeyEvent e){
    }
    
    public void keyTyped(KeyEvent e){
    }
    
    public void mouseDragged(MouseEvent e){
        if(selected != null){
            int dX = e.getX() - x;
            int dY = e.getY() - y;
            selected.move(dX,dY);
        }
        mouseMoved(e);
    }
    
    public void mouseMoved(MouseEvent e){
        x = e.getX();
        y = e.getY();
        for(Face f : myFaces){
            f.lookAt(e.getX(), e.getY());
        }
        repaint();
    }
    
    public void mouseClicked(MouseEvent e){
    }

    public void mouseEntered(MouseEvent e){
    }
    public void mouseExited(MouseEvent e){
    }
    public void mousePressed(MouseEvent e){
        for(Face f : myFaces){
            if(f.checkMouth(e.getX(), e.getY())){
                selected = f;
                //x = e.getX();
               // y = e.getY();
            }
        }
        repaint();
    }
    public void mouseReleased(MouseEvent e){
        
        if(selected != null){
            selected.checkMouth(-500,-500);
            selected = null;
        }
        
        repaint();
    }
}
