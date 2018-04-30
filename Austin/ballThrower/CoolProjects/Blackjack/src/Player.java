import java.util.Scanner;


public class Player{
	Hand myHand;
	
	Scanner myScanner;
	
	public Player(){
		myHand = new Hand();
		myScanner = new Scanner(System.in);
	}
	
	
	public boolean doYouWantToHit(){
		System.out.println("Hit or stay?");
		String input = myScanner.nextLine();
		while(!inputOK(input)){
			System.out.println("Please type 'hit' or type 'stay'!!!");
			input = myScanner.nextLine();
		}
		
		return input.equalsIgnoreCase("hit");
			
	}
	
	public void takeCard(Card c){
		myHand.takeCard(c);
	}
	
	public String toString(){
		return myHand.toString();
	}
	
	private boolean inputOK(String str){
		if(str.equalsIgnoreCase("hit"))return true;
		if(str.equalsIgnoreCase("stay"))return true;
		return false;
	}

}
