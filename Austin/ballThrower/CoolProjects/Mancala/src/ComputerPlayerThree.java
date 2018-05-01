
public class ComputerPlayerThree extends Player {

	private int myStones[];
	private int otherStones[];
	private int totalStones;
	
	private int myStonesPrime[];
	private int otherStonesPrime[];
	
	public ComputerPlayerThree(int i) {
		super(i);
		myStones = new int[6];
		otherStones = new int[6];
	}
	
	public int selectMove(BoardHelper b){
		update(b);
		System.out.println("myStones");
		for(int i=0 ; i<6 ; i++){
			System.out.print(" " + myStones[i]);
		}
		System.out.println("OtherStones");
		for(int i=0 ; i<6 ; i++){
			System.out.print(" " + otherStones[i]);
		}
		System.out.println();
		// let t[p] be the number of stones I can take if I select pit p
		int[] t = new int[6];
		int[] l = new int[6];
		// let l[p] be the number of stones I might loose if I select pit p
		for(int i=0 ; i<6 ; i++){
			t[i] = computeTake(i);
			distribute(i);
			int maxLoose = 0;
			for(int j=0 ; j<6 ; j++){
				int tempLoose = computeLoose(j);
				if(tempLoose > maxLoose) maxLoose = tempLoose;
			}
			l[i] = maxLoose;
		}
		System.out.println("Take");
		for(int i=0 ; i<6 ; i++){
			System.out.print(" " + t[i]);
		}
		System.out.println("Loose");
		for(int i=0 ; i<6 ; i++){
			System.out.print(" " + l[i]);
		}
		System.out.println();
		
		//super.selectMove(b);
		// Try to capture the maximum number of stones
		int capture = t[0]-l[0];
		int index = -1;
		for(int i=0 ; i<6 ; i++){
			if(myStones[i]>0 && t[i]-l[i]>capture){
				index = i;
				capture = t[i]-l[i];
			}
		}
		
		if(index!=-1 && totalStones>24){
			System.out.println(index + " count=" + myStones[index]);
			return index;
		}
		// If I can't take and can't prevent loosing any try to move just one stone
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
	
	private void distribute(int i){
		myStonesPrime = new int[6];
		otherStonesPrime = new int[6];
		for(int j=0 ; j<6 ; j++){
			myStonesPrime[j] = myStones[j];
			otherStonesPrime[j] = otherStones[j];
		}
		int count = myStonesPrime[i];
		myStonesPrime[i] = 0;
		int index = i;
		boolean onMyside = true;
		while(count!=0){
			if(++index==6){ index=0; onMyside = !onMyside;}
			if(index==i){
				if(++index==6){index=0; onMyside = !onMyside;}
			}
			if(onMyside)myStonesPrime[index]++;
			else otherStonesPrime[index]++;
			count--;
		}
		
		if(onMyside)return;
		while(index>=0 && (otherStonesPrime[index] == 2 || otherStonesPrime[index] == 3 )){
			otherStonesPrime[index] = 0;
			index--;
		}
	}
	
	private int computeLoose(int i){
		int stopIndex = i;
		boolean onMySide = false;
		int stoneCount = otherStonesPrime[i];
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
		if(!onMySide)return 0;
		int take = 0;
		while(stopIndex>=0 && (myStonesPrime[stopIndex] == 1 || (!lapWasMade && myStonesPrime[stopIndex] == 2) || ( lapWasMade && myStonesPrime[stopIndex]==0) )){
			if(!lapWasMade && myStonesPrime[stopIndex] == 1 )take+=2;
			if(!lapWasMade &&myStonesPrime[stopIndex] == 2 )take+=3;
			if(lapWasMade && myStonesPrime[stopIndex] == 0 )take+=2;
			if(lapWasMade && myStonesPrime[stopIndex] == 1 )take+=3;
			stopIndex--;
		}
		return take;
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
		totalStones = 0;
		for(int i=0 ; i<6 ; i++){
			myStones[i] = b.countStonesAt(i, myRow);
			totalStones += myStones[i];
			otherStones[i] = b.countStonesAt(i, otherRow);
			totalStones += otherStones[i];
		}
	}
	
	
}
