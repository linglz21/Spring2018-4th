import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Deck {

	ArrayList<Card> myCards;
	
	public Deck(){
		myCards = new ArrayList<Card>();
		fillDeck();
	}
	
	private void fillDeck(){
//		char[] suits = {'H','D','S','C'};
//		int[] values = {2,3,4,5,6,7,8,9,10,11,12,13,14};
//		for(char suit : suits)for(int value : values)myCards.add(new Card(suit,value));
		try {
			BufferedReader in = new BufferedReader(getClass().getClassLoader().getResource("resources/cards.txt").getFile());
			int suit;
			char value;
			String fileName;
			BufferedImage img =ImageIO.read(getClass().getClassLoader().getResource("resources/" + fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Card dealCard() {
		if(myCards.size()==0)fillDeck();
		return myCards.remove(0);
	}

}
