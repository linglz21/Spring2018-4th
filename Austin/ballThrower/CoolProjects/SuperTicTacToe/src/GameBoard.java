import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class GameBoard {
	public static final int NUM_ROWS = 10;
	public static final int NUM_COLS = 10;
	
	Character[][] allPieces;
	
	public GameBoard(){
		allPieces = new Character[NUM_COLS][NUM_ROWS];
	}
	
	public void addPiece(int row, int col, char c){
		allPieces[col][row] = c;
		System.out.println("row=" + row + " col=" + col);
	}
	
	public boolean spotEmpty(int row, int col){
		return allPieces[col][row] == null;
	}
	
	public boolean checkForWinner(int row, int col){
		return checkHorizontal(row, col) ||
			   checkVertical(row, col) ||
			   checkLeftDiagonal(row,col) ||
			   checkRightDiagonal(row,col);
	}
	
	private boolean checkHorizontal(int row, int col){
		int count = 1;
		Character symbol = allPieces[col][row];
		for(int c = col+1 ; c<NUM_COLS && allPieces[c][row]==symbol ; c++) { 
			count++;
		}
		for(int c = col-1 ; c>=0 && allPieces[c][row]==symbol ; c--) count ++;
		return count>=5;
	}
	
	private boolean checkVertical(int row, int col){
		int count = 1;
		Character symbol = allPieces[col][row];
		for(int r = row+1 ; r<NUM_ROWS && allPieces[col][r]==symbol ; r++) count ++;
		for(int r = row-1 ; r>=0 && allPieces[col][r]==symbol ; r--) count ++;
		return count>=5;
	}
	
	private boolean checkLeftDiagonal(int row, int col){
		int count = 1;
		Character symbol = allPieces[col][row];
		for(int r = row+1 , c=col+1 ; c<NUM_COLS && r<NUM_ROWS && allPieces[c][r]==symbol ; r++ , c++) count ++;
		for(int r = row-1 , c=col-1; c>=0 && r>=0 && allPieces[c][r]==symbol ; r-- , c--) count ++;
		return count>=5;
	}
	
	private boolean checkRightDiagonal(int row, int col){
		int count = 1;
		Character symbol = allPieces[col][row];
		for(int r = row+1 , c=col-1 ; c>=0 && r<NUM_ROWS && allPieces[c][r]==symbol ; r++ , c--) count ++;
		for(int r = row-1 , c=col+1; c<NUM_COLS && r>=0 && allPieces[c][r]==symbol ; r-- , c++) count ++;
		return count>=5;
	}
	
	
	public void draw(Graphics g){
		Font f = g.getFont();
		f = f.deriveFont(50f);
		g.setFont(f);
		g.setColor(Color.black);
		double x=0;
		double dX = (double)(Game.WIDTH-5)/NUM_COLS;
		for(int i=0 ; i<=NUM_COLS ; i++){
			g.fillRect((int)x, 0, 5, Game.HEIGHT);
			x += dX;
		}
		double y=0;
		double dY = (double)(Game.HEIGHT-5)/NUM_ROWS;
		for(int i=0 ; i<=NUM_ROWS ; i++){
			g.fillRect(0, (int)y, Game.WIDTH, 5);
			y += dY;
		}
		int colSpacing = Game.WIDTH/NUM_COLS;
		int rowSpacing = Game.HEIGHT/NUM_ROWS;
		for(int row =0 ; row < NUM_ROWS ; row++){
			for(int col=0 ; col < NUM_COLS ; col++){
				if(allPieces[col][row] != null){
					g.drawString(""+allPieces[col][row],col*colSpacing+15,(row+1)*rowSpacing-15);
				}
			}
		}
	}

}
