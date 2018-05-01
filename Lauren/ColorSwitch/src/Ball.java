import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Ball implements KeyListener{
	private double x, y;
	private double vX,vY;
	public static final int SIZE = 20;
	private boolean leftPressed,rightPressed,downPressed;
	private Color myColor;
	
	private Color[] choices;
	Obstacle safe;
	
	public Ball(){
		myColor = new Color(0x32F0F0);
		leftPressed = rightPressed = false;
		x = 300;
		y = 700;
		vX = vY = 0.0;
		choices = new Color[6];
		choices[0]=new Color(0xF20F5A);// bright pink
		choices[1]=new Color(0x7CFF70);// green
		choices[2]=new Color(0xF28A8A);// pink,coral
		choices[3]=new Color(0xA859F7);// purple
		choices[4]=new Color(0x32F0F0);// blue
		choices[5]=new Color(0xFFFF3D);// yellow

	}
	public void bounce(){ vY = -5; }
	public void update(){
		x += vX;
		y += vY;
		vY -= 0.2;
		vX *= 0.95;
		vY *= 0.95;
		if(downPressed)vY +=.4;
		if(leftPressed)vX -= 1;
		if(rightPressed)vX +=1;
		if(x<0 && vX<0) vX *= -1;
		if(x+SIZE>MainClass.WIDTH && vX>0) vX *= -1;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT) leftPressed = true;
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) rightPressed = true;
		if(e.getKeyCode()== KeyEvent.VK_DOWN) downPressed = true;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==37) leftPressed = false;
		if(e.getKeyCode()==39) rightPressed = false;
		if(e.getKeyCode()== KeyEvent.VK_DOWN) downPressed = false;
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void shift(double dY){
		y+=dY;
	}

	public double getX() {return x;}
	public double getY() {return y;}
	
	
	public void draw(Graphics bg) {
		bg.setColor(myColor);
		bg.fillOval((int)x, (int)y, SIZE ,SIZE);
	}
	
	public boolean hit(Obstacle ob){
		if(ob==safe)return false;
		if(x>ob.getX()+ob.WIDTH) return false;
		if(x+SIZE < ob.getX())return false;
		if(y>ob.getY()+ob.HEIGHT) return false;
		if(y+SIZE < ob.getY())return false;
		safe = ob;
		return true;
	}
	public boolean sameColor(Obstacle ob) {
		return myColor.equals(ob.getColor());
	}
	public void changeColor() {
		int choice = (int)(Math.random()*6);
		myColor = choices[choice];
	}
}
