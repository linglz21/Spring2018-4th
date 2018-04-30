import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Man implements KeyListener{
	private double x, y;
	private double vX,vY;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	private boolean leftPressed,rightPressed;
	public Man(){
		leftPressed = rightPressed = false;
		x = TheGame.WIDTH/2;
		y = -50;
		vX = vY = 0.0;
	}
	public void bounce(){ vY = -5; }
	public void paint(Graphics g){
		g.setColor(Color.white);
		g.fillRect((int)x,(int)y, WIDTH,HEIGHT);
	}
	public void update(){
		x += vX;
		y += vY;
		vY += 0.3;
		vX *= 0.95;
		
		if(leftPressed)vX -= 1;
		if(rightPressed)vX +=1;
		if(x<0 && vX<0) vX *= -1;
		if(x+WIDTH>TheGame.WIDTH && vX>0) vX *= -1;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==37) leftPressed = true;
		if(e.getKeyCode()==39) rightPressed = true;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==37) leftPressed = false;
		if(e.getKeyCode()==39) rightPressed = false;
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public double getX() {return x;}
	public double getY() {return y;}
}
