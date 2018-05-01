import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;


public class Board {
	
	Square[][] mySquares;
	public static final int H_OFFSET = 50;
	public static final int V_OFFSET = 50;
	
	public Board(Board b){
		mySquares = new Square[9][9];
		for(int r=0 ; r<9 ; r++){
			for(int c=0; c<9 ; c++){
				mySquares[r][c] = new Square(b.mySquares[r][c]);
			}
		}
		
	}
	
	public Board(String fileName){
		if(fileName == null)return;
		mySquares = new Square[9][9];
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			for(int r=0 ; r<9 ; r++){
				char[] line = in.readLine().toCharArray();
				for(int i=0, c=0 ; c<9 ; c++ , i++){
					if(line[i] == '|')i++;
					mySquares[r][c] = new Square(line[i]);
				}
				if((r+1)%3 == 0)in.readLine();
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void randomGuess(){
		int r = (int)(Math.random()*9);
		int c = (int)(Math.random()*9);
		mySquares[r][c].randomGuess();
	}
	
	public boolean reduceGroup(int r,int c){
		boolean solvedAsquare = false;
		r *=3;
		c *=3;
		for(int x=0 ; x<3 ; x++){
			for(int y=0 ; y<3 ; y++){
				if(mySquares[r+y][c+x].isSolved()){
					for(int x2=0 ; x2<3 ; x2++){
						for(int y2=0 ; y2<3 ; y2++){
							if(x!=x2 || y!=y2){
								boolean temp = mySquares[r+y2][c+x2].isSolved();
								mySquares[r+y2][c+x2].remove(mySquares[r+y][c+x].getSolution());
								if(!temp && mySquares[r+y2][c+x2].isSolved()) solvedAsquare = true;
							}
						}
					}
				}
			}
		}
		return solvedAsquare;
	}
	
	public void reduce(){
		boolean doneReducing = false;
		while (!doneReducing){
			doneReducing = true;
			for(int i=0 ; i<9 ; i++)if(reduceCol(i)) doneReducing = false;
			for(int i=0 ; i<9 ; i++)if(reduceRow(i)) doneReducing = false;
			for(int r=0 ; r<3 ; r++)for(int c=0 ; c<3 ; c++) if(reduceGroup(r, c))doneReducing =false;
		}
	}
	
	public boolean isSolved(){
		for(int r=0 ; r<9 ; r++){
			for(int c=0 ; c<9 ; c++){
				if(!this.mySquares[r][c].isSolved())return false;
			}
		}
		return true;
	}
	
	
	public boolean reduceCol(int c){
		boolean solvedAsquare = false;
		for(int r=0 ; r<9 ; r++){
			if(mySquares[r][c].isSolved()){
				for(int r2=0 ; r2<9 ; r2++){
					if(r2 != r){
						boolean temp = mySquares[r2][c].isSolved();
						mySquares[r2][c].remove(mySquares[r][c].getSolution());
						if(!temp && mySquares[r2][c].isSolved()) solvedAsquare = true;
					}
				}
			}
		}
		return solvedAsquare;
	}
	
	public boolean reduceRow(int r){
		boolean solvedAsquare = false;
		for(int c=0 ; c<9 ; c++){
			if(mySquares[r][c].isSolved()){
				for(int c2=0 ; c2<9 ; c2++){
					if(c2 != c){
						boolean temp = mySquares[r][c2].isSolved();
						mySquares[r][c2].remove(mySquares[r][c].getSolution());
						if(!temp && mySquares[r][c2].isSolved()) solvedAsquare = true;
					}
				}
			}
		}
		return solvedAsquare;
	}
	
	public boolean isValid(){
		if(mySquares==null)return false;
		for(int r=0 ; r<9 ; r++){
			for(int c=0 ; c<9 ; c++){
				if(!mySquares[r][c].isValid())return false;
			}
		}
		return true;
	}

	public void paint(Graphics g) {
		for(int i=0 ; i<10 ; i++){
			int thickness = i%3==0 ? 6 : 3;
			g.fillRect(Square.WIDTH*i+H_OFFSET, V_OFFSET, thickness, 9*Square.HEIGHT+6);
			g.fillRect(H_OFFSET,Square.HEIGHT*i+V_OFFSET,  9*Square.WIDTH+6,thickness);
		}
		for(int r=0 ; r<9 ; r++){
			for(int c=0 ; c<9 ; c++){
				mySquares[r][c].paint(g,r,c);
			}
		}
		
	}

}
