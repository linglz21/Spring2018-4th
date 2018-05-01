import java.awt.Color;
import java.awt.Graphics;


public class Term {
	private String myWord;
	private String myDefinition; 
	private int x=0 , y=100;
	
	public Term(String word, String definition){
		myWord = word;
		myDefinition = definition;
	}
	
	public Term(Term other){
		myWord = other.myWord;
		myDefinition = other.myDefinition;
	}
	
	public void display(Graphics g){
		g.setColor(Color.white);
		g.drawString(myDefinition, x, y);
	}

	public boolean check(String text){
		return text.equalsIgnoreCase(myWord);
	}
	
	public void update() {
		y += 1;
		
	}

	public boolean hitBottom(int height) {
		return y>=height;
	}

}
