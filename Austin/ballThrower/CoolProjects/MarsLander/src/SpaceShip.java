import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class SpaceShip implements KeyListener{
	private double x,y,vX,vY;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	private boolean leftPressed, rightPressed, gameStarted;
	private double thrusterForce;
	private boolean isDead;
	private double fuelLeft;
	
	public SpaceShip(){
		isDead = gameStarted = false;
		thrusterForce = 0.05;
		x = TheGame.WIDTH/8;
		y = TheGame.HEIGHT-40;
		vX = vY = 0.0;
		leftPressed = rightPressed = false;
		fuelLeft = 50;
	}
	
	public void pause(){
		gameStarted = false;
	}
	
	public ArrayList<Spark> update(){
		ArrayList<Spark> temp = new ArrayList<Spark>();
		if(!gameStarted)return temp;
		x += vX;
		y += vY;
		vY += TheGame.gravity;
		if(fuelLeft<=0)return temp;
		if(leftPressed){
			fuelLeft -= 0.2;
			vX -= thrusterForce;
			vY -= thrusterForce;
			for(int i=0 ; i<20 ; i++)temp.add(new Spark(x+this.WIDTH,y+this.HEIGHT,3+Math.random(),3+Math.random()));
		}
		if(rightPressed){
			fuelLeft -= 0.2;
			vX += thrusterForce;
			vY -= thrusterForce;
			for(int i=0 ; i<20 ; i++)temp.add(new Spark(x-2,y+this.HEIGHT,-3-Math.random(),3+Math.random()));
		}
		return temp;
	}
	
	public boolean isOffScreen(){
		return y>TheGame.HEIGHT;
	}
	
	public void paint(Graphics g){
		if(isDead)return;
		g.setColor(Color.white);
		g.fillRect((int)x,(int)y,WIDTH,HEIGHT);
		g.setColor(Color.red);
		g.drawRect(0, TheGame.HEIGHT-50, 10, 50);
		g.fillRect(0,(int)( TheGame.HEIGHT-fuelLeft), 10, (int)fuelLeft);
	}
	
	public double getSpeed(){
		return Math.sqrt(vX*vX+vY*vY);
	}
	
	public ArrayList<Spark> explode(){
		ArrayList <Spark> temp = new ArrayList<Spark>();
		if(isDead)return temp;
		isDead = true;
		for(int i=0 ; i<1000 ; i++){
			double dX,dY,dist;
			do{
				dX = Math.random()*10-5;
				dY = Math.random()*10-5;
				dist = Math.sqrt(dX*dX+dY*dY);
			}while(dist>5);
			temp.add(new Spark(x+this.WIDTH/2,y+this.HEIGHT/2,dX,dY));
		}
		return temp;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		gameStarted = true;
		if(e.getKeyCode()==37)leftPressed = true;
		if(e.getKeyCode()==39)rightPressed = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==37)leftPressed = false;
		if(e.getKeyCode()==39)rightPressed = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
