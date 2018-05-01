
public class ComputerPlayerTwo extends Player {

	private int myStones[];
	private int otherStones[];
	
	public ComputerPlayerTwo(int i) {
		super(i);
		myStones = new int[6];
		otherStones = new int[6];
	}
	
	public int selectMove(BoardHelper b){
		update(b);
		// let t[p] be the number of stones I can take if I select pit p
		int[] t = new int[6];
		for(int i=0 ; i<6 ; i++){
			t[i] = computeTake(i);
		}
		
		// Try to capture the maximum number of stones
		int capture = 0;
		int index = -1;
		for(int i=0 ; i<6 ; i++){
			if( t[i]>capture){
				index = i;
				capture = t[i];
			}
		}
		if(index!=-1){
			System.out.println(index + " count=" + myStones[index]);
			return index;
		}
		// If I can't take any try to move just one stone
		for(int i=0 ; i<5 ; i++){
			int count = b.countStonesAt(i, this.myRow);
			if(count==1){
				System.out.println(" move one" + i);
				return i;
			}
		}
		// If I can't just move one try to just move two
		for(int i=0 ; i<5 ; i++){
			int count = b.countStonesAt(i, this.myRow);
			if(count==2){
				System.out.println(" move two" + i);
				return i;
			}
		}
		// If all else fails choose a pit at random
		System.out.println(" random");
		return (int)(Math.random()*6);
	}
	
	
	
	private int computeTake(int i) {
		int stopIndex = i;
		boolean onMySide = true;
		int stoneCount = myStones[i];
		if(stoneCount==0)return 0;
		boolean lapWasMade = false;
		stopIndex++;
		while(stoneCount>0){
			if(stopIndex==i && onMySide){
				stopIndex++; //skip selected pit
				lapWasMade = true;
			}
			stoneCount--; // drop a stone
			stopIndex++; // move to next pit
			if(stopIndex>5){ // if at end of row
				stopIndex -= 6; // go to beginning
				onMySide = !onMySide; // of other row
			}
		}
		stopIndex--;
		if(stopIndex==-1){
			onMySide = !onMySide;
			stopIndex = 5;
					
		}
		if(onMySide)return 0;
		int take = 0;
		while(stopIndex>=0 && (otherStones[stopIndex] == 1 || (!lapWasMade && otherStones[stopIndex] == 2) || ( lapWasMade && otherStones[stopIndex]==0) )){
			if(!lapWasMade && otherStones[stopIndex] == 1 )take+=2;
			if(!lapWasMade &&otherStones[stopIndex] == 2 )take+=3;
			if(lapWasMade && otherStones[stopIndex] == 0 )take+=2;
			if(lapWasMade &&otherStones[stopIndex] == 1 )take+=3;
			stopIndex--;
		}
		return take;
	}

	private void update(BoardHelper b){
		for(int i=0 ; i<6 ; i++){
			myStones[i] = b.countStonesAt(i, myRow);
			otherStones[i] = b.countStonesAt(i, otherRow);
		}
	}
	
	
}
