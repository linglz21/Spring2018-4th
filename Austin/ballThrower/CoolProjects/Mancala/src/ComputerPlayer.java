
public class ComputerPlayer extends Player {

	
	public ComputerPlayer(int i) {
		super(i);
	}
	
	public int selectMove(BoardHelper b){
		return (int)(Math.random()*6);
	}
	
	
}
