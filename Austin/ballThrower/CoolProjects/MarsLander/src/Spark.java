import java.awt.Color;
import java.awt.Graphics;


public class Spark {
	
	double x,y,vX,vY,size;
	float[] startColor = {1.0f,0.0f,0.0f};
	float[] endColor = {1.0f,1.0f,0.0f};
	
	public Spark(double x, double y, double vX, double vY){
		this.x = x;
		this.y = y;		
		this.vX = vX;
		this.vY = vY;
		size = Math.random()/2+0.5;
	}
	
	public Color blendColor(float amount){
		float r = amount*startColor[0] + (1-amount)*endColor[0];
		float g = amount*startColor[1] + (1-amount)*endColor[1];
		float b = amount*startColor[2] + (1-amount)*endColor[2];
		return new Color(r,g,b);
	}
	
	public boolean update(){    
		x += vX;
		y += vY;
		vY += .05;
		size -= 0.05;
		return size<=0;
	}
	
	
	public void paint(Graphics g){
		if(size<0)return;
		g.setColor(blendColor((float)size));
		g.fillOval((int)x, (int)y, 4, 4);
	}

}
