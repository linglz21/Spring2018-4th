import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;


public class Store {
	private LockableList<Stone> myStones;
	private int x,y;
	private int myID;
	private static int nextID = 0;
	private static Font f = null;
	
	public Store(int x, int y){
		if(f==null){
			try {
				f = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/resources/NC.ttf").openStream())
						.deriveFont(40.0f);
			} catch (Exception e) {
			}
		}
		myID = nextID++;
		myStones = new LockableList<Stone>();
		this.x = x;
		this.y = y;
	}
	
	public void paint(Graphics g){
		myStones.acquire();
		for(Stone s : myStones){
			s.paint(g, x, y);
		}
		myStones.release();
		g.setColor(Color.black);
		g.setColor(Color.white);
		g.drawString("" + myStones.size(), x-29,y-29);
		g.setColor(Color.black);
		g.drawString("" + myStones.size(), x-30,y-30);
	}
	
	public void addStones(ArrayList<Stone> s){
		for(Stone st : s)st.spreadY();
		myStones.acquire();
		myStones.addAll(s);
		myStones.release();
	}
	
	public int getStoneCount(){
		return myStones.size();
	}

	@Override
	public String toString() {
		return "Store " + myID + myStones ;
	}

	public boolean isStopped() {
		boolean stopped = true;
		myStones.acquire();
		for(Stone s : myStones) if(!s.isStopped())stopped = false;
		myStones.release();
		//System.out.println("Pit " + stopped);
		return stopped;
	}

}
