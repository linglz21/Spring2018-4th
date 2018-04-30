
public class Card {
	private int value; // will use 1 for ace, 11 for jack, 12 for queen, 13 for king
	private String suit; // will use "diamond", "spade", "heart", "club"  would be better to use enum
	
	
	public Card(String suit, int value){
		this.value = value;
		this.suit = suit;
	}
	
	public String toString(){
		String retVal = "";
		switch(value){ 
		case 1: retVal += "Ace"; break;
		case 11: retVal += "Jack"; break;
		case 12: retVal += "Queen"; break;
		case 13: retVal += "King"; break;
		default: retVal += value; break;
		}
		return retVal + " of " + suit;
	}

	public int getValue(){
		return value;
	}
	
	// not needed for blackjack
	public String getSuit(){
		return suit;
	}
}
