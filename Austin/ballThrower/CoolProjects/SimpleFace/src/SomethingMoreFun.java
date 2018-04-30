import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;


public class SomethingMoreFun extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		SomethingMoreFun mySomething = new SomethingMoreFun();
		mySomething.setSize(800, 600);
		mySomething.setResizable(false);
		mySomething.setVisible(true); 
	}
	
	public void paint(Graphics g){
		g.setColor(Color.blue);
		g.fillOval(200, 125, 350, 350);
		g.setColor(Color.white);
		g.fillOval(240, 170, 100, 100);
		g.fillOval(400, 170, 100, 100);
		g.setColor(Color.black);
		g.fillOval(245, 185, 25, 25);
		g.fillOval(410, 225, 25, 25);
		g.setColor(Color.black);
		g.fillArc(260, 400, 200, 75, 0, 100);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(6));
		g.drawLine(390, 475, 390, 550);
		g.drawLine(390, 550, 425, 590);
		g.drawLine(390, 550, 355, 590);
		g.drawLine(390, 485, 375, 575);
	}

}
