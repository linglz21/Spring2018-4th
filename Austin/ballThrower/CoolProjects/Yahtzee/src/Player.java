import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Player implements MouseListener{
	
	int [] upperSection;
	int threeOfKind;
	int fourOfKind;
	int fullHouse;
	int smStraight;
	int lgStraight;
	int yahtzee;
	int chance;
	
	int myIndex;
	
	Dice[] allDice;
	
	public Player(int j){
		myIndex = j;
		allDice = null;
		upperSection = new int[7];
		for(int i=0 ; i<7 ; i++)upperSection[i] = -1;
		threeOfKind = -1;
		fourOfKind = -1;
		fullHouse = -1;
		smStraight = -1;
		lgStraight = -1;
		yahtzee = -1;
		chance = -1;
	}
	
	public boolean scoreCardIsFull(){
		for(int i=1 ; i<7 ; i++){
			if(this.upperSection[i]==-1)return false;
		}
		if(threeOfKind == -1)return false;		
		if(fourOfKind  == -1)return false;		
		if(fullHouse   == -1)return false;		
		if(smStraight  == -1)return false;		
		if(lgStraight  == -1)return false;		
		if(yahtzee     == -1)return false;		
		if(chance      == -1)return false;
		return true;
	}
	
	public void paint(Graphics g){
		Font f = g.getFont().deriveFont(30f);
		g.setFont(f);
		g.setColor(Color.black);
		for(int i=1 ; i<7 ; i++){
			if(upperSection[i] != -1){
				//System.out.println("Player "+ myIndex + " score" + upperSection[i] + " slot" + i);
				g.drawString("" + upperSection[i], myIndex*75+273, i*36+102);
			}
		}
		int upperTotal = getUpperTotal();
		g.drawString("" + upperTotal, myIndex*75+273, 355);
		if(upperTotal>=63){
			Font f2 = g.getFont().deriveFont(20f);
			g.setFont(f2);
			g.drawString("" + 35, myIndex*75+273, 384);
			g.setFont(f);
			upperTotal += 35;
			
		}
		g.drawString("" + upperTotal, myIndex*75+273, 420);
		
		int lowerTotal = getLowerTotal();
		g.drawString("" + lowerTotal, myIndex*75+273, 755);
		g.drawString("" + upperTotal, myIndex*75+273, 791);
		g.drawString("" + (upperTotal+lowerTotal), myIndex*75+273, 827);
		
		if(threeOfKind != -1)g.drawString("" + threeOfKind, myIndex*75+273, 466+36);
		if(fourOfKind  != -1)g.drawString("" + fourOfKind,  myIndex*75+273, 466+36*2);
		if(fullHouse   != -1)g.drawString("" + fullHouse,   myIndex*75+273, 466+36*3);
		if(smStraight  != -1)g.drawString("" + smStraight,  myIndex*75+273, 466+36*4);
		if(lgStraight  != -1)g.drawString("" + lgStraight,  myIndex*75+273, 466+36*5);
		if(yahtzee     != -1)g.drawString("" + yahtzee,     myIndex*75+273, 466+36*6);
		if(chance      != -1)g.drawString("" + chance,      myIndex*75+273, 466+36*7);
	}
	
	public void placeDice(Dice[] d){
		allDice = d;
	}
	
	private int getUpperTotal(){
		int subTotal = 0;
		for(int i=1 ; i<7 ; i++){
			if(upperSection[i] != -1)subTotal += upperSection[i];
		}
		return subTotal;
	}
	
	
	private int getLowerTotal(){
		int subTotal = 0;
		if(threeOfKind != -1)subTotal+=threeOfKind;
		if(fourOfKind  != -1)subTotal+=fourOfKind ;
		if(fullHouse   != -1)subTotal+=fullHouse  ;
		if(smStraight  != -1)subTotal+=smStraight ;
		if(lgStraight  != -1)subTotal+=lgStraight ;
		if(yahtzee     != -1)subTotal+=yahtzee    ;
		if(chance      != -1)subTotal+=chance     ;
		return subTotal;
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(allDice==null)return;
		int lowerLimit = 268 + 75*this.myIndex;
		int upperLimit = lowerLimit + 75;
		if(e.getX()<lowerLimit || e.getX() > upperLimit)return;
		
		if(e.getY() > 106 && e.getY()<326){
			int index = (e.getY()-106) / 36 + 1;
			placeInUpperSection(index);
		}
		
		if(e.getY() > 470 && e.getY()<725){
			int index = (e.getY()-470) / 36;
			switch (index){
			case 0 : placeInThreeOfKind(); break;
			case 1 : placeInFourOfKind(); break;
			case 2 : placeInFullHouse(); break;
			case 3 : placeInSmallStraight(); break;
			case 4 : placeInLargeStraight(); break;
			case 5 : placeInYahtzee(); break;
			case 6 : placeInChance(); break;
			}
		}
	}
	
	private int totalAllDice(){
		int total = 0;
		for(Dice d : allDice)total += d.getValue();
		return total;
	}
	
	private int[] getDieCounts(){
		int[] diceCount = new int[7];
		for(Dice d : allDice){
			diceCount[d.getValue()]++;
		}
		return diceCount;
	}
	
	private void endTurn(){
		allDice = null;
		Game.g.dicePlaced();
		Game.g.repaint();				
	}
	
	
	private void placeInThreeOfKind(){
		if(this.threeOfKind !=-1) return;
		int[] diceCount = getDieCounts();
		boolean gotThreeOfKind = false;
		for(int i=1 ; i<7 ; i++)if(diceCount[i]>=3)gotThreeOfKind = true;		
		if(gotThreeOfKind)threeOfKind = totalAllDice();
		else threeOfKind = 0;
		endTurn();
	}
	private void placeInFourOfKind(){
		if(this.fourOfKind !=-1) return;
		int[] diceCount = getDieCounts();
		boolean gotFourOfKind = false;
		for(int i=1 ; i<7 ; i++)if(diceCount[i]>=4)gotFourOfKind = true;
		if(gotFourOfKind)fourOfKind = totalAllDice();
		else fourOfKind = 0;
		endTurn();
	}
	
	private void placeInFullHouse(){
		if(this.fullHouse !=-1) return;
		int[] diceCount = getDieCounts();
		boolean gotTwo = false;
		boolean gotThree = false;
		for(int i=1 ; i<7 ; i++){
			if(diceCount[i]==2)gotTwo = true;
			if(diceCount[i]==3)gotThree = true;
			if(diceCount[i]==6)gotTwo = gotThree = true;
		}
		if(gotTwo && gotThree)fullHouse = 25;
		else fullHouse = 0;
		endTurn();
	}
	
	public void placeInSmallStraight(){
		if(this.smStraight !=-1) return;
		int[] diceCount = getDieCounts();
		int count = 0;
		boolean gotSmallStraight = false;
		for(int i=1 ; i<7 ; i++){
			if(diceCount[i]!=0)count++;
			else count = 0;
			if(count== 4) gotSmallStraight = true;
		}
		if(gotSmallStraight)this.smStraight = 30;
		else this.smStraight = 0;
		endTurn();
	}
	
	public void placeInLargeStraight(){
		if(this.lgStraight !=-1) return;
		int[] diceCount = getDieCounts();
		int count = 0;
		boolean gotLargeStraight = false;
		for(int i=1 ; i<7 ; i++){
			if(diceCount[i]!=0)count++;
			else count = 0;
			if(count== 5) gotLargeStraight = true;
		}
		if(gotLargeStraight)this.lgStraight = 40;
		else this.lgStraight = 0;
		endTurn();
	}
	
	public void placeInYahtzee(){
		if(this.yahtzee ==-1) this.yahtzee=0;
		int[] diceCount = getDieCounts();
		boolean gotYahtzee = false;
		for(int i=1 ; i<7 ; i++)if(diceCount[i]==5)gotYahtzee = true;
		if(gotYahtzee && yahtzee==0)this.yahtzee = 50;
		else if (gotYahtzee) this.yahtzee += 100;
		if(yahtzee==0 || gotYahtzee) endTurn();
	}
	
	public void placeInChance(){
		if(this.chance !=-1) return;
		this.chance = this.totalAllDice();
		endTurn();
	}
	
	public void placeInUpperSection(int index){
		if(upperSection[index] != -1) return;
		int score=0;
		for(Dice d : allDice){
			if(d.getValue() == index){
				score += d.getValue();
			}
		}
		upperSection[index] = score;
		endTurn();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	

}
