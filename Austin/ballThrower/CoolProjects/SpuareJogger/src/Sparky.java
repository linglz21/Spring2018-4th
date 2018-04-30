import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Sparky {
	private double x,y,dx,dy,age;
	private int color;
	private int width, height;
	
	public Sparky(double x, double y, double dx, double dy){
		this.x = x;
		this.y = y;
		this.dx = dx+ Math.random()-0.5;
		this.dy = dy+ Math.random()/-0.5;
		age = Math.random()-0.5;
		color = (int)(Math.random()*3);
		width = (int)(Math.random()*5);
		height = (int)(Math.random()*5);
		
	}
	
	public void update(){
		x += dx;
		y += dy;
		age += 0.1;
		
	}
	
	public double getAge(){return age;}
	
	public void draw (Graphics g){
		if(color == 1){g.setColor(Color.red);}
		if(color == 2){g.setColor(Color.orange);}
		if(color == 3){g.setColor(Color.yellow);}
		g.fillRect((int)x, (int)y, width, height);
	}

	public static ArrayList<Sparky> explode(double x,double y){
		ArrayList<Sparky> temp = new ArrayList<Sparky>();
		for(int i=0 ; i<1000 ; i++){
			double dX = Math.random()*10-5;
			double dY = Math.random()*10-5;
			double dist = Math.sqrt(dX*dX+dY*dY);
			if(dist<5)temp.add(new Sparky(x,y,dX,dY));
		}
		return temp;
	}
}
