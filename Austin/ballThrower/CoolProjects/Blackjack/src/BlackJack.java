import java.util.Scanner;


public class BlackJack {
	 public static void main(String[] args){
		 Dealer d = new Dealer();
		 Player p = new Player();
		
		 System.out.println("Dealer hand = " + d);
		 p.takeCard(d.dealCard());
		 p.takeCard(d.dealCard());
		 System.out.println("Player hand = " + p);
	     boolean done = false;
	     while(!done){
	    	done = true;
	    	 if(d.doYouWantToHit()){
	    		 done = false;
	    		 d.dealToSelf();
	    	 }
	    	 if(p.doYouWantToHit()){
	    		 done = false;
	    		 p.takeCard(d.dealCard());
	    	 }
	    	 if(d.myHand.getValue() > 21){
	    		 done = true;
				 System.out.println("You Win!");	 
			 }
			if(p.myHand.getValue() > 21){
				done = true;
				System.out.println("You Lose!");	
				
				
			}
			 System.out.println("Dealer hand = " + d);
			 System.out.println("Player hand = " + p);
			 
	        }
	     if(d.myHand.getValue() <= 21 && p.myHand.getValue() <= 21){
	    	 if(p.myHand.getValue() > d.myHand.getValue()){
	    		 System.out.println("You Win!");
	    	 }else{
	    		 System.out.println("You Lose!");
		 }
	     }
	     }

		
	 }
	     
	     
	     
	     // add betting?
	 

