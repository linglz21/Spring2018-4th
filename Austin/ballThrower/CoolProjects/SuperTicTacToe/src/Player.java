import java.awt.Graphics;


public class Player {
	private char mySymbol;
	private String myName;
	
	public Player(char c, String name){
		mySymbol = c;
		myName = name;
	}
	
	public char getSymbol(){ return mySymbol;}
	public String getName(){return myName;}
	
	public void paint (Graphics g, int x, int y){
		g.drawString(myName + " " + mySymbol, x, y);
	}

}
