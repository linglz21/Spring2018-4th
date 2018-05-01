import java.util.ArrayList;


public class Deck {
	
	ArrayList<Card> allCards;
	
	public Deck(){
		refill();
		shuffle();
	}
	
	private void refill(){
		allCards = new ArrayList<Card>();
		for(int i=1 ; i<=13 ; i++){
			allCards.add(new Card("Heart",i));
			allCards.add(new Card("Club",i));
			allCards.add(new Card("Diamond",i));
			allCards.add(new Card("Spade",i));
		}		
	}
	
	public void shuffle(){
		// swap each card into some random location
		for(int i=0 ; i<52 ; i++){
			Card temp = allCards.get(i);
			int random = (int)(Math.random()*52);
			allCards.set(i, allCards.get(random));
			allCards.set(random, temp);
		}
	}
	
	public Card deal(){
		if(allCards.size()==0){
			refill();
			shuffle();
		}
		return allCards.remove(0);
	}

}
