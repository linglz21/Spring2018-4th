import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;

public class PaintProject extends JFrame implements MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	public static PaintProject p;
	private int X1, Y1;
	private Color brushColor;
	private Graphics stroke;
	private boolean dragging;
	
	PaintProject(){
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public static void main(String[] args){
		p = new PaintProject();
		p.setSize(WIDTH, HEIGHT);
		p.setResizable(false);
		p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p.setVisible(true);
	}
	
	public void paint(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0,0,WIDTH,HEIGHT);
		g.setColor(Color.BLACK);
		g.fillRect(695, 27, 100, 50);
		g.drawRect(5, 27, 685, 767);
		g.setColor(Color.RED);
		g.fillRect(695, 82, 100, 50);
		g.setColor(Color.GREEN);
		g.fillRect(695, 137, 100, 50);
		g.setColor(Color.BLUE);
		g.fillRect(695, 192, 100, 50);
		g.setColor(Color.GRAY);
		g.fillRect(695, 745, 100, 50);
		g.setColor(Color.BLACK);
	    
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		int x = arg0.getX();
        int y = arg0.getY();

        if(x >= 695 && x <= 795 && y >= 27 && y <= 77){
        	brushColor = Color.BLACK;
        }else if(x >= 695 && x <= 795 && y >= 82 && y <= 132){
        	brushColor = Color.RED;
        }else if(x >= 695 && x <= 795 && y >= 137 && y <= 187){
        	brushColor = Color.GREEN;
        }else if(x >= 695 && x <= 795 && y >= 192 && y <= 242){
        	brushColor = Color.BLUE;
        }else if(x >= 695 && x <= 795 && y >= 745 && y <= 795){
        	repaint();
        }else return;
        
	}
	
	public void stroke(){
		stroke = getGraphics();
		if(brushColor == Color.BLACK){
			stroke.setColor(Color.BLACK);
		}
		if(brushColor == Color.RED){
			stroke.setColor(Color.RED);
		}
		if(brushColor == Color.GREEN){
			stroke.setColor(Color.GREEN);
		}
		if(brushColor == Color.BLUE){
			stroke.setColor(Color.BLUE);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		int x = arg0.getX();
        int y = arg0.getY();
		if (x > 5 && x < 685 && y > 27 && y < 767) {
        	X1 = x;
        	Y1 = y;
        	dragging = true;
        	stroke();
        }
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		 if (dragging == false)
			return;
		 	int x = arg0.getX();
	        int y = arg0.getY();
	        stroke.drawLine(X1, Y1, x, y);
	        X1 = x;
	        Y1 = y;
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (dragging == true){
		dragging = false;
		}else return;
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
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}