import java.awt.Color;
import java.awt.Graphics;

public class Deck {
	double x, y;
	public static final int WIDTH = 70;
	public static final int HEIGHT = 90;
	
	private Card[] myCards;
	int nextCard;
	
	public Deck() {
		nextCard = 0;
		myCards = new Card[52];
		for(int i=0 ; i<52 ; i++){
			myCards[i] = new Card();
		}
		shuffle();
	}
	
	public void shuffle(){
		for(int i=51 ; i>0 ; i--){
			int index = (int)(Math.random()*i);
			Card temp = myCards[index];
			myCards[index] = myCards[i];
			myCards[i] = temp;
		}
	}
	
	public Card deal(){
		if(nextCard==52){
			shuffle();
			nextCard = 0;
		}
		return myCards[nextCard++];
		
	}

}
