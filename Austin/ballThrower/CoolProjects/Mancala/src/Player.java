import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Player implements MouseListener{
	
	private MouseEvent mouseClick;
	private boolean myTurn;
	protected int myRow;
	protected int otherRow;
	
	public Player(int i){
		myRow = i;
		if(i==0)otherRow = 1;
		else otherRow = 0;
		mouseClick = null;
		myTurn = false;
	}
	
	
	public int selectMove(BoardHelper b){
		mouseClick = null;
		myTurn = true;
		while(mouseClick == null){
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b.selectPitAt(mouseClick.getX(),mouseClick.getY(),myRow);
	}

	public void mouseReleased(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		if(myTurn)mouseClick = e;
		myTurn = false;
	}

}
