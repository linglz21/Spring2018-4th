import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;


public class Pit {
	private LockableList<Stone> myStones;
	public Pit next;
	public Pit prev;
	private int x,y;
	private int myID;
	private static int nextID = 0;
	private static Font f = null;	
	private char myRow;
	
	public Pit(int x, int y, char row){
		if(f==null){
			try {
				f = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/resources/NC.ttf").openStream())
						.deriveFont(40.0f);
			} catch (Exception e) {
			}
		}
		myRow = row;
		myID = nextID++;
		myStones = new LockableList<Stone>();
		myStones.acquire();
		for(int i=0 ; i<4 ; i++)myStones.add(new Stone(x,y));
		myStones.release();
		this.x = x;
		this.y = y;
	}
	
	public boolean isStopped(){
		boolean stopped = true;
		myStones.acquire();
		for(Stone s : myStones) if(!s.isStopped())stopped = false;
		myStones.release();
		//System.out.println("Pit " + stopped);
		return stopped;
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setFont(f);
		myStones.acquire();
		for(Stone s : myStones){
			s.paint(g, x, y);
		}
		myStones.release();
		g.setColor(Color.black);
		g.setColor(Color.white);
		int stoppedStones = 0;
		myStones.acquire();
		for(Stone s : myStones)if(s.isStopped())stoppedStones++;
		myStones.release();
		
		g.drawString("" + stoppedStones, x-29,y-29);
		g.setColor(Color.black);
		g.drawString("" + stoppedStones, x-30,y-30);
	}
	
	public ArrayList<Stone> removeStones(){
		myStones.acquire();
		LockableList<Stone> temp = myStones;
		myStones = new LockableList<Stone>();
		temp.release();
		return temp;
	}
	
	public void addStone(Stone s){
		myStones.acquire();
		myStones.add(s);
		myStones.release();
	}
	
	public int stoneCount(){
		return myStones.size();
	}

	@Override
	public String toString() {
		return "Pit "+ myID  + myStones ;
	}

	public double getDistance(int x2, int y2) {
		int dX = x-x2;
		int dY = y-y2;
		return Math.sqrt(dX*dX + dY*dY);
	}

	public char getRow() {
		return myRow;
	}

}
