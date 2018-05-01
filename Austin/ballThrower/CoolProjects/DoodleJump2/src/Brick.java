import java.awt.Color;
import java.awt.Graphics;


public class Brick {

	public int x; //coordinate of the brick
	public int y;
	public static final int WIDTH = 100, HEIGHT = 4; //80, 4 
	protected Color myColor;
	
	private boolean evade;// IF BRICK IS BELOW THE GROUND LEVEL 
	
	private int randomX(){
		int x = (int)(Math.random()*(Game.WIDTH-WIDTH));
		return x;
		
	}

	public Brick(int y){	
	x = randomX();
	this.y = y;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.fillRect(x, y, WIDTH, HEIGHT);
		
	}
	
		
	
	public void Jump(){
		int xD = Dude.getX(); 
		int yD = Dude.getY();
		int vY = Dude.getvY();
		
		
		if(xD + Dude.WIDTH >  x && x+ WIDTH > xD){
			if (yD + Dude.HEIGHT >y &&  yD + Dude.HEIGHT < y+15 && vY > 0){
				Dude.isFalling = false;
				Dude.Jump(this);
				Dude.platform = y;
				System.out.println("JUMP");
				if(y>Game.HEIGHT - Game.GROUND-50){
					evade = true;
				}
				return;
				
				//INCREASING PRECISION
				//System.out.println("the height is " + (yD + Dude.HEIGHT));
				//System.out.println(y);
			}
		}
	}
	
	
	
	public void Falling(){
		
		int xD = Dude.getX(); 
		int yD = Dude.getY();
		int vY = Dude.getvY();
		if(yD <Game.HEIGHT - Game.GROUND-50 || evade){
			if((xD + Dude.WIDTH <  x || x+ WIDTH < xD) && (vY == 10 ) ){
				Dude.isFalling = true;
				System.out.println("Falling");
				evade = false;
				return;
				
			}	
		}return;
	}
	
		
	public void Roll(){
		if(Dude.RollDown){
			y += Dude.GoDown;
			if(y> Game.HEIGHT){
				y = y% Game.HEIGHT;
				x = randomX();
			}
		}
	}


		
		
}
	
	

