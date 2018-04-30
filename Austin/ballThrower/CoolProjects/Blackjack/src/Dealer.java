
public class Dealer {
	
	Deck myDeck;
	Hand myHand;
	Card myHiddenCard;
	
	public Dealer(){
		myDeck = new Deck();
		myHand = new Hand();
		myHiddenCard = myDeck.deal();
		myHand.takeCard(myDeck.deal());
	}
	
	public String toString(){
		return " ??? + " + myHand.toString();
	}
	
	public void dealToSelf(){
		myHand.takeCard(myDeck.deal());
	}
	
	public Card dealCard(){
		return myDeck.deal();
	}
	
	public boolean doYouWantToHit(){
		int value = myHand.getValue();
		value += myHiddenCard.getValue();
		if(myHiddenCard.getValue()==1 && value<=11)value += 10;
		if(value < 17){
			return true;
		}
		return false;
	}
	

}
