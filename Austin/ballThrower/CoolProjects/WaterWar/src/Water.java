import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Water implements MouseListener{
	
	private double[][] waterLevel;
	private int flip; 
	private double baseLevel;
	
	public Water(){
		flip = 0;
		baseLevel = TheGame.HEIGHT * 3 / 4;
		waterLevel = new double[2][TheGame.WIDTH];
		for(int i=0 ; i<waterLevel[0].length ; i++){
			waterLevel[0][i] = 0;
			waterLevel[1][i] = 0;
		}
	}
	
	public double getHeight(int x){
		if(x<0 || x>=waterLevel[0].length)return baseLevel;
		return waterLevel[0][x]+baseLevel;
	}

	public void draw(Graphics bg) {
		bg.setColor(Color.blue);
		for(int i=0 ; i<waterLevel[0].length ; i++){
			bg.fillRect(i, (int)(waterLevel[0][i]+baseLevel), 1, TheGame.HEIGHT);
		}
	}
	
	private final double p = 1/(5*Math.sqrt(2*Math.PI));
	
	private double g(double x){
		return p*Math.pow(Math.E, -0.5*((x/9)*(x/9)));
	}
	
	public void splash(int x){
		
		for(int i=-20 ; i<20 ; i++){
			int index = i+x;
			if(index>0 && index<waterLevel[0].length-1){
				waterLevel[flip][index] += 60*g(i);
				waterLevel[(flip+1)%2][index] += 25*g(i);
			}
		}
	}

	public void update() {
		int current = flip;
		int previous = flip = (flip+1)%2;
		
		for(int i=0 ; i<waterLevel[0].length ; i++){
			int left = i-1;
			if(left<0) left = TheGame.WIDTH-1;
			int right = i+1;
			if(right >= TheGame.WIDTH) right = 0;
			waterLevel[previous][i] =  0.998*(waterLevel[current][left] + waterLevel[current][right]-waterLevel[previous][i]);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		splash(e.getX());
	}


}
