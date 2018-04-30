import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;


public class Square {
	
	private ArrayList<Integer> posibilities;
	public static final int WIDTH=90;
	public static final int HEIGHT=90;
	private static Font myFont;
	private Color myColor;
	
	public Square(char c){
		if(myFont == null){
			try {
				myFont = Font.createFont(Font.TRUETYPE_FONT,new File("Pacifico.ttf")).deriveFont(60f);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		posibilities = new ArrayList<Integer>();
		if(c == ' '){
			myColor = Color.black;
			for(int i=1 ; i<10 ; i++){
				posibilities.add(i);
			}
		}else{
			myColor = Color.red;
			posibilities.add((c-'0'));
		}
	}

	public Square(Square square) {
		myColor = square.myColor;
		posibilities = new ArrayList<Integer>();
		for(Integer i : square.posibilities) posibilities.add(i);
	}

	public void paint(Graphics g, int r, int c) {
		g.setColor(myColor);
		int y = r * HEIGHT;
		int x = c * WIDTH;
		String s = "";
		if(isSolved()){
			g.setFont(myFont);
		}else{
			g.setFont(myFont.deriveFont(15f));
		}
		for(int i=0 ; i<posibilities.size() ; i++){
			if(i%3==0){
				g.drawString(s,Board.H_OFFSET+x+WIDTH/3,Board.V_OFFSET+y+HEIGHT/2);
				s = "";
				y += 15;
			}			
			s += posibilities.get(i);
		}
		g.drawString(s,Board.H_OFFSET+x+WIDTH/3,Board.V_OFFSET+y+HEIGHT/2);
	}
	
	public boolean isSolved(){
		return posibilities.size() == 1;
	}
	
	public boolean isValid(){
		return posibilities.size() != 0;
	}
	
	public boolean remove(Integer i){
		return posibilities.remove(i);
	}

	public Integer getSolution() {
		if(isSolved())return posibilities.get(0);
		return null;
	}

	public void randomGuess() {
		if(!this.isValid())return;
		Integer solution = this.posibilities.get((int)(Math.random()*this.posibilities.size()));
		this.posibilities = new ArrayList<Integer>();
		this.posibilities.add(solution);
	}
	
	
	

}
