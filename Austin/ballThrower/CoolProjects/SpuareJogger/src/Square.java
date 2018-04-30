import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Square {
	private double w,h,x,y;
	float r,g,b;
	public static double vX=0,vY=2;
	private Color myColor;
	private double myVY;
	
	public Square(){
		myVY = Math.random()*3+2;
		myColor = randomColor();
		h = w = 20;
		y=-h;
		x = Math.random()*(MyMain.WIDTH);
	}
	private Color randomColor(){
		float r = (float)Math.random();
		float g = (float)Math.random();
		float b = (float)Math.random();
		return new Color(r,g,b);
	}
	
	
	public boolean didHitJogger(){
		if(x+w < MyMain.x-MyMain.w/2) return false; // square left of jogger 
		if(x > MyMain.x+MyMain.w/2) return false; //square right of jogger
		if(y+h < MyMain.y - MyMain.h) return false; //square above jogger
		if(y > MyMain.y) return false; //square below jogger
		
		return true;
	}
	public boolean isOffScreen(){
		return y >MyMain.HEIGHT;
	}
	
	public void update(){
		x += vX;
		y += vY*myVY;
	}
	public void paint (Graphics g){
		g.setColor(myColor);
		g.fillRect((int)x, (int)y, (int)w, (int)h);
	}
}
