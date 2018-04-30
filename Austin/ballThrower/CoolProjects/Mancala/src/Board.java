import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Board implements MouseListener{
	
	private Pit[] rowA;
	private Pit[] rowB;
	private Store storeA;
	private Store storeB;
	
	private BufferedImage background;
	private BufferedImage rules;
	private boolean gameStarted;
	
	public Board(){
		gameStarted = false;
		try {
			background = ImageIO.read(getClass().getResource("/resources/board.png"));
			rules = ImageIO.read(getClass().getResource("/resources/Rules.png"));
		} catch (IOException e) {
		}
		rowA = new Pit[6];
		rowB = new Pit[6];
		int shift = 275;
		int spacing = (Game.WIDTH-2*shift)/5;
		
		rowA[5] = new Pit(5*spacing+shift,Game.HEIGHT*3/4, 'a');
		rowB[5] = new Pit(0+shift,Game.HEIGHT/4,'b');
		for(int i=4 ; i>=0 ; i--){
			rowA[i] = new Pit(i*spacing+shift,Game.HEIGHT*3/4,'a');
			rowA[i].next = rowA[i+1];
			rowB[i] = new Pit((5-i)*spacing+shift,Game.HEIGHT/4,'b');
			rowB[i].next = rowB[i+1];
		}
		rowA[5].next = rowB[0];
		rowB[5].next = rowA[0];
		
		Pit temp = rowA[0];
		while(temp.next.prev == null){
			temp.next.prev = temp;
			temp = temp.next;
		}
		storeA = new Store(Game.WIDTH-100,Game.HEIGHT/2);
		storeB = new Store(100,Game.HEIGHT/2);
	}
		
	public boolean hasBeenClicked(){
		return gameStarted;
	}
	
	public boolean isStopped(){
		boolean stopped = true;
		for(int i=0 ; i<6 ; i++){
			if(!rowA[i].isStopped())stopped = false;
			if(!rowB[i].isStopped())stopped = false;			
		}
		if(!storeA.isStopped())stopped = false;
		if(!storeB.isStopped())stopped = false;
		//System.out.println(stopped);
		return stopped;
	}

	public void paint(Graphics g) {
		if(!gameStarted){
			g.drawImage(rules, 0, 0, null);
			return;
		}
		g.drawImage(background, 0, 0, null);
		for(int i=0 ; i<6 ; i++){
			rowA[i].paint(g);
			rowB[i].paint(g);
		}
		storeA.paint(g);
		storeB.paint(g);
	}
	
	public Pit redistribute(Pit p){
		ArrayList<Stone> temp = p.removeStones();
		Pit current = p;
		// distribute stones
		while(temp.size()>0){
			current = current.next;
			if(current == p) current = current.next;
			current.addStone(temp.remove(0));
		}
		if(current.getRow() != p.getRow() && (current.stoneCount()==2 || current.stoneCount()==3)){
			return current;
		}else{
			return null;
		}
		// if the last stone ends up in the opponent's row and that pit finishes with 2 or 3 seeds, those seeds are captured.
	}
	
	public void capture(Pit p){
		Pit current = p;
		Store playersStore = null;
		if(p.getRow() == 'a'){
			playersStore = storeB;
		}else{
			playersStore = storeA;
		}
		while(current.getRow() == p.getRow() && (current.stoneCount()==2 || current.stoneCount()==3)){
			playersStore.addStones( current.removeStones());
			current = current.prev;
		}
	}
	
	public Pit getPit(int index, int player){
		Pit[] theRow = null;
		if(player==0) theRow = rowA; else theRow = rowB;
		if(index<0 || index>=theRow.length || player>1 || player<0)return null;
		return theRow[index];
	}
	
	public Pit[] getRow(int player){
		Pit[] theRow = null;
		if(player==0) theRow = rowA; else theRow = rowB;
		return theRow;
	}

	// returns the pit in the appropriate row closest to where the player clicked
	public Pit getPit(int x, int y,int player) {
		Pit[] theRow = null;
		if(player==0) theRow = rowA; else theRow = rowB;
		
		Pit temp = theRow[0];
		double minDist = theRow[0].getDistance(x,y);
		for(int i=1 ; i<6 ; i++){
			double dist = theRow[i].getDistance(x, y);
			if(dist<minDist){
				temp = theRow[i];
				minDist = dist;
			}
		}
		if(minDist>75)return null;
		return temp;
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	public void mousePressed(MouseEvent e) {
		gameStarted = true;	
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub		
	}

	// return -1 if no winner    0 if playerA    1 if playerB
	public int checkForWinner(int whosTurn) {
		Pit[] temp = null;
		if(whosTurn==0 )temp = rowA;
		else temp = rowB;
		boolean empty = true;
		for(int i=0 ; i<6 ; i++){
			if(temp[i].stoneCount()>0)empty = false;
		}
		if(empty || storeA.getStoneCount()>24 || storeB.getStoneCount()>24){
			int aCount = storeA.getStoneCount();
			int bCount = storeB.getStoneCount();
			for(int i=0 ; i<6 ; i++){
				aCount += rowA[i].stoneCount();
				bCount += rowB[i].stoneCount();
			}
			if(aCount>bCount)return 0;
			else if(aCount<bCount)return 1;
			else return 2;
		}
		return -1;
	}

}
