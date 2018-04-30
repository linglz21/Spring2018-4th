import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Ship implements KeyListener {
	
	private BufferedImage leftImage;
	private BufferedImage rightImage;
	private double x;
	private double vX; 
	private double angle;
	private boolean leftPressed, rightPressed;
	private boolean facingLeft;
	
	public Ship(){
		try {
			facingLeft = true;
			leftPressed = rightPressed = false;
			vX = 0;
			leftImage = ImageIO.read(getClass().getClassLoader().getResource("resources/ship.png"));
			rightImage = ImageIO.read(getClass().getClassLoader().getResource("resources/ship2.png"));
			x = TheGame.WIDTH/2 - leftImage.getWidth()/2;
			angle = Math.PI/4;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double getX(){
		return x + leftImage.getWidth()/2;
	}
	
	public double getVX(){
		return vX;
	}
	
	public boolean isFacingLeft(){
		return facingLeft;
	}
	
	public double getAngle(){
		return angle;
	}
	

	public void draw(Graphics bg, Water w) {
		Graphics2D g2 = (Graphics2D)bg;
		AffineTransform oldAT = g2.getTransform();
		AffineTransform at = new AffineTransform();
		int dy = (int)w.getHeight((int)x+leftImage.getWidth()/2)-leftImage.getHeight();
		at.translate(x+leftImage.getWidth()/2, dy+leftImage.getHeight());
		at.rotate(-angle);
		at.translate(-(x+leftImage.getWidth()/2), -(dy+leftImage.getHeight()));
		g2.setTransform(at);
		if(facingLeft)bg.drawImage(leftImage, (int)x, dy+5, null);
		else bg.drawImage(rightImage, (int)x, dy+10, null);
		g2.setTransform(oldAT);
	}

	public void update(Water w) {
		double left = w.getHeight((int)x);
		double right = w.getHeight((int)x+leftImage.getWidth());
		angle = -3*Math.atan2(right-left,leftImage.getWidth());
		if(facingLeft)angle -= 0.05;
		else angle += 0.05;
		x += vX;
		vX -= angle/4;
		vX *= 0.99;
		if(leftPressed){ 
			vX-= 0.25; 
			facingLeft = true; 
		}
		if(rightPressed){ 
			vX += 0.25; 
			facingLeft = false; 
		}
		if(x< -leftImage.getWidth()) x = TheGame.WIDTH;
		if(x> TheGame.WIDTH)x = -leftImage.getWidth();
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)leftPressed = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)rightPressed = true;
	}


	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)leftPressed = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)rightPressed = false;
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
