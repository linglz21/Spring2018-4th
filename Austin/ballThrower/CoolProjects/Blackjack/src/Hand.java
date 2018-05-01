import java.util.ArrayList;


public class Hand {
	ArrayList<Card> allCards;
	
	public Hand(){
		allCards = new ArrayList<Card>();
	}
	
	public void takeCard(Card c){
		allCards.add(c);
	}
	
	public String toString(){
		return allCards.toString();
	}
	
	public int getValue(){
		int aceCount=0;
		int value = 0;
		for(Card c : allCards){
			if(c.getValue()== 1)aceCount++;
			if(c.getValue()>10) value += 10;
			else value += c.getValue();
		}
		while(value<12 && aceCount>0){
			value += 10;
			aceCount --;
		}
		return value;
	}

}
