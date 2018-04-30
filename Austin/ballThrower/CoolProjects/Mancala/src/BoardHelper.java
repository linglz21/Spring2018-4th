
public class BoardHelper {
	private Board myBoard;
	
	public BoardHelper (Board b){
		myBoard = b;
	}


	/**
	 * @param x screen coordinate
	 * @param y screen coordinate
	 * @param player the id of a player, should be 0 or 1
	 * @return index of pit closest to (x,y) if pit is empty or in other players row returns -1
	 */
	public int selectPitAt(int x, int y,int player) {
		Pit[] theRow = myBoard.getRow(player);
		if(theRow==null) return -1;
		int temp = 0;
		double minDist = theRow[temp].getDistance(x,y);
		for(int i=1 ; i<6 ; i++){
			double dist = theRow[i].getDistance(x, y);
			if(dist<minDist){
				temp = i;
				minDist = dist;
			}
		}
		if(minDist>75)return -1;
		return temp;
	}
	
	
	/**
	 * @param i the index of a pit 
	 * @param player the id of a player, should be 0 or 1
	 * @return the number of stones in the pit at index i of that players row.
	 */
	public int countStonesAt(int i, int player){
		Pit[] theRow = myBoard.getRow(player);
		if(theRow==null) return -1;
		if(theRow.length <= i) return -1;
		return theRow[i].stoneCount();
	}
	
}
