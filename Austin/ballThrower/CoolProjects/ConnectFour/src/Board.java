import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class Board implements MouseListener {

	private final int COL_COUNT=7;
	//private final int ROW_COUNT=8;
	private final int ROW_COUNT=6;
	private final int SOME_SPACE=100;
	private final int COL_WIDTH = Game.WIDTH / COL_COUNT;
	private final int ROW_HEIGHT = (Game.HEIGHT-SOME_SPACE) / ROW_COUNT;
	
	private final int turnCountMax = 42;
	
	ArrayList<Integer> rowList = new ArrayList<Integer>();
	ArrayList<Integer> colList = new ArrayList<Integer>();
	
	private final int player = 1;
	private final int com = 2;
	
	private int x1 = -1;
	private int x2 = -1;
	private int y1 = -1;
	private int y2 = -1;
	
	public int turnCount;
	private boolean gameOverChips;
	private boolean gameOver4;
	private boolean sittingOnTop;
	public int whosTurn;
	public static int winner;
	
	private String message;
	private String errormessage;
	private String connect4msg;
	private String winnermessage;
	
	private Chip[][] allChips;
	private int[][] moves;

	private final Color redChip=Color.red;
	private final Color blackChip=Color.black;
	//private Color chipColor;
	
	public Board(){
		message = "You're turn! Choose a slot.";
		errormessage = "";
		connect4msg = "Connect Four! Click to choose a slot.";
		
		moves = new int[ROW_COUNT][COL_COUNT];
		for(int i=0; i<ROW_COUNT ; i++){
			for( int j=0; j<COL_COUNT ; j++){
				moves[i][j] = 0;
			}
		}
		winner = 0;
		//red is 1
		//black is 2
		allChips = new Chip[ROW_COUNT][COL_COUNT];
		turnCount = 0;
		gameOverChips = false;
		gameOver4 = false;
		//chipColor = Color.white;
	}
	
	public void paint(Graphics g){
		g.setColor(new Color(0xB5C4F7));
		g.fillRect(0, 0, Game.WIDTH, (Game.HEIGHT-SOME_SPACE));
		g.setColor(Color.black);
		for(int x=0 ; x<COL_COUNT ; x++){
			g.drawLine(x*COL_WIDTH, 0, x*COL_WIDTH, (Game.HEIGHT-SOME_SPACE));
		}
		for(int y = 0; y < ROW_COUNT ; y++){
			g.drawLine(0, y*ROW_HEIGHT, Game.WIDTH, y*ROW_HEIGHT);
		}
		
		for(int row = 0 ; row<ROW_COUNT ; row++){
			for(int col=0 ; col<COL_COUNT ; col++){
				if(allChips[row][col] != null){
					allChips[row][col].draw(g);
				}
			}
		}
		Font fontA = g.getFont().deriveFont(20f);
		Font fontC = g.getFont().deriveFont(50f);
		g.setFont(fontA);
		
		g.setColor(Color.black);
		g.drawString(connect4msg, 2, Game.HEIGHT - SOME_SPACE + 25);
		g.setColor(Color.green);
		g.drawString(message, Game.WIDTH-350 , Game.HEIGHT - SOME_SPACE + 25);
		g.setColor(Color.red);
		g.setFont(fontC);
		g.drawString(errormessage, 2, Game.HEIGHT - SOME_SPACE + 90);
		
		
		if(gameOverChips){
			g.setColor(Color.red);
			Font fontB = g.getFont().deriveFont(100f);
			g.setFont(fontB);
			String gameOver = "Game Over";
			g.drawString(gameOver, 200, Game.HEIGHT-10);
			errormessage = "";
			message = "";
			connect4msg = "";
			if(winner == 1){
				winnermessage = "You Win!";
				g.setColor(Color.cyan);
				Font fontC1 = g.getFont().deriveFont(180f);
				g.setFont(fontC1);
				g.drawString(winnermessage, 50, Game.HEIGHT/2);
				g.setColor(Color.white);
				g.drawLine(x1, y1, x2, y2);
				g.drawLine(x1-1, y1-1, x2-1, y2-1);
				g.drawLine(x1+1, y1+1, x2+1, y2+1);
				g.drawLine(x1-2, y1-2, x2-2, y2-2);
				g.drawLine(x1+2, y1+2, x2+2, y2+2);
				//System.out.println(x1 + ", " + y1 + ", " + x2 + ", " + y2);
			}
			if(winner == 2){
				g.setColor(Color.white);
				g.drawLine(x1, y1, x2, y2);
				g.drawLine(x1-1, y1-1, x2-1, y2-1);
				g.drawLine(x1+1, y1+1, x2+1, y2+1);
				g.drawLine(x1-2, y1-2, x2-2, y2-2);
				g.drawLine(x1+2, y1+2, x2+2, y2+2);
				
				winnermessage = "You Lose!";
				g.setColor(Color.cyan);
				Font fontC1 = g.getFont().deriveFont(180f);
				g.setFont(fontC1);
				g.drawString(winnermessage, 20, Game.HEIGHT/2);
				
				//System.out.println(x1 + ", " + y1 + ", " + x2 + ", " + y2);
			}
			if(winner ==0){
				winnermessage = "It's a Tie!";
				g.setColor(Color.cyan);
				Font fontC1 = g.getFont().deriveFont(150f);
				g.setFont(fontC1);
				g.drawString(winnermessage, 20, Game.HEIGHT/2);
			}
			
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		errormessage = "";
		sittingOnTop = false;
		int mouseX = e.getX()-Game.MARGIN;
		int mouseY = e.getY()-(Game.MARGIN+Game.TOP_SPACE);
		if(mouseY > Game.HEIGHT-SOME_SPACE){
			mouseX = 0;
			mouseY = 0;
		}
		int col = mouseX/COL_WIDTH;
		int row = mouseY/ROW_HEIGHT;
		
		whosTurn = turnCount%2;
		int testRow = row+1;
		if(testRow >= ROW_COUNT){
			sittingOnTop = true;
		}
		if(testRow < ROW_COUNT){
			if(allChips[testRow][col] != null){
				sittingOnTop = true;
			}	
		}
		
		if(row<0 || row>=ROW_COUNT || col<0 || col>=COL_COUNT || (allChips[row][col] != null) || (sittingOnTop == false)){
			//System.out.println("NO");
			//show thread telling pick different spot
			errormessage = "Invalid Slot Choice! Please try again.";
			if(mouseX == 0 && mouseY == 0){
				errormessage = "Click on the Board, Man";
			}
		}
		
		if((allChips[row][col] == null) && (sittingOnTop == true)){
			allChips[row][col] = new Chip(col*COL_WIDTH,row*ROW_HEIGHT,redChip);
			moves[row][col]= player;
			turnCount++;
			checkWinPlayer();
			
			gameOverChips = isGameOver();
			computerTurn();
		}
		
		gameOverChips = isGameOver();
		Game.g.repaint();
		if(winner != 0)Game.g.removeMouseListener(this);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	private void computerTurn(){
		rowList.clear();
		colList.clear();
		int row; 
		int col; 
		int count = 0;
		for(col = 0; col < COL_COUNT ; col++ ){
			for(row = 5 ; row >= 0 ; row--){
				if((allChips[row][col] == null) && (count == 0)){
					if(row < 5){
						if(allChips[row+1][col] != null){
							rowList.add(row);
							colList.add(col);
						}
					}else{
						rowList.add(row);
						colList.add(col);
					}
				}
			}
		}
		
		
		int a = rowList.size();
		int b = colList.size();
		int min;
		
		if (a<b){
			min = a;
		} else{
			min = b;
		}
		
		int choice = (int) (Math.random()*min); 
		row = rowList.get(choice);
		col = colList.get(choice);
		
		int size = rowList.size();
		
		int z = (int) (Math.random()*3);
		
		if(z == 0){
			for(int i = 0; i < size ; i++ ){
				if(colList.get(i) != 0){
					if(moves[rowList.get(i)][colList.get(i)-1] == 2){
						row = rowList.get(i);
						col = colList.get(i);
					}
				}
				
				if(colList.get(i) != 0){
					if(moves[rowList.get(i)][colList.get(i)-1] == 1){
						row = rowList.get(i);
						col = colList.get(i);
					}
				}
			}
		}
		
		if(z == 1){
			for(int i = 0; i < size ; i++ ){
				if(colList.get(i) != 6){
					if(moves[rowList.get(i)][colList.get(i)+1] == 2){
						row = rowList.get(i);
						col = colList.get(i);
					}
				}
				
				if(colList.get(i) != 6){
					if(moves[rowList.get(i)][colList.get(i)+1] == 1){
						row = rowList.get(i);
						col = colList.get(i);
					}
				}
			}
		}
		
		if(z==2){

			for(int i = 0; i < size ; i++ ){	
				if(rowList.get(i) != 5){
					if(moves[rowList.get(i)+1][colList.get(i)] == 2){
						row = rowList.get(i);
						col = colList.get(i);
					}
				}
				
				if(rowList.get(i) != 5){
					if(moves[rowList.get(i)+1][colList.get(i)] == 1){
						row = rowList.get(i);
						col = colList.get(i);
					}
				}
			}	
		}
		
		moves[row][col] = com; 
		allChips[row][col] = new Chip(col*COL_WIDTH,row*ROW_HEIGHT,blackChip);
		turnCount++;
		
		if(winner == 0){
			checkWinCom();
		}
	}
	
	private boolean isGameOver(){
		if((turnCount >= turnCountMax) || (gameOver4 == true)){
			
			return true;
		}else{
			return false;
		}
	}
	
	private void checkWinCom(){
		int x =0;
		int y = 0;
		for(int i=0; i<ROW_COUNT ; i++){
			int countCom = 0;
			for(int j=0; j<COL_COUNT ; j++){
				
				if(moves[i][j] == com){
					if(countCom == 0){
					x = j*COL_WIDTH+(COL_WIDTH/2);
					y = i*ROW_HEIGHT+(ROW_HEIGHT/2);
					}
					countCom++;
					if(countCom >= 4){
						x1 = x;
						y1 = y;
						x2 = j*COL_WIDTH+(COL_WIDTH/2);
						y2 = i*ROW_HEIGHT+(ROW_HEIGHT/2);
						gameOver4 = true;
						winner = com;
					}
				}else{
					countCom = 0;
				}
			}
		}
		for(int j=0; j<COL_COUNT ; j++){
			int countCom = 0;
			for(int i=0; i<ROW_COUNT ; i++){
				if(moves[i][j] == com){
					if(countCom == 0){
						x = j*COL_WIDTH+(COL_WIDTH/2);
						y = i*ROW_HEIGHT+(ROW_HEIGHT/2);
						}
					countCom++;
					if(countCom >= 4){
						x1 = x;
						y1 = y;
						x2 = j*COL_WIDTH+(COL_WIDTH/2);
						y2 = i*ROW_HEIGHT+(ROW_HEIGHT/2);
						gameOver4 = true;
						winner = com;
					}
					
				}else{
					countCom = 0;
				}
			}
		}
		for(int r = 5 ; r>=3 ; r--){
			for(int c = 0 ; c<=3 ; c++){
				if(moves[r][c]== com){
					if(moves[r-1][c+1]== com){
						if(moves[r-2][c+2]== com){
							if(moves[r-3][c+3]== com){
								x1 = c*COL_WIDTH+(COL_WIDTH/2);
								y1 = r*ROW_HEIGHT+(ROW_HEIGHT/2);
								x2 = (c+3)*COL_WIDTH+(COL_WIDTH/2);
								y2 = (r-3)*ROW_HEIGHT+(ROW_HEIGHT/2);
								gameOver4 = true;
								winner = com;
							}
						}
					}
				}
			}
		}
		for(int r = 5 ; r>=3 ; r--){
			for(int c = 3 ; c<=6 ; c++){
				if(moves[r][c]== com){
					if(moves[r-1][c-1]== com){
						if(moves[r-2][c-2]== com){
							if(moves[r-3][c-3]== com){
								x1 = c*COL_WIDTH+(COL_WIDTH/2);
								y1 = r*ROW_HEIGHT+(ROW_HEIGHT/2);
								x2 = (c-3)*COL_WIDTH+(COL_WIDTH/2);
								y2 = (r-3)*ROW_HEIGHT+(ROW_HEIGHT/2);
								gameOver4 = true;
								winner = com;
							}
						}
					}
				}
			}
		}
	}
	private void checkWinPlayer(){
		int x =0;
		int y = 0;
		for(int i=0; i<ROW_COUNT ; i++){
			int countPlayer = 0;
			for(int j=0; j<COL_COUNT ; j++){
				if(moves[i][j] == player){
					if(countPlayer == 0){
						x = j*COL_WIDTH+(COL_WIDTH/2);
						y = i*ROW_HEIGHT+(ROW_HEIGHT/2);
						}
					countPlayer++;
					if(countPlayer >= 4){
						x1 = x;
						y1 = y;
						x2 = j*COL_WIDTH+(COL_WIDTH/2);
						y2 = i*ROW_HEIGHT+(ROW_HEIGHT/2);
						gameOver4 = true;
						winner = player;
					}
				}else{
					countPlayer = 0;
				}
			}
		}
		for(int j=0; j<COL_COUNT ; j++){
			int countPlayer = 0;
			for(int i=0; i<ROW_COUNT ; i++){
				if(moves[i][j] == player){
					if(countPlayer == 0){
						x = j*COL_WIDTH+(COL_WIDTH/2);
						y = i*ROW_HEIGHT+(ROW_HEIGHT/2);
						}
					countPlayer++;
					if(countPlayer >= 4){
						x1 = x;
						y1 = y;
						x2 = j*COL_WIDTH+(COL_WIDTH/2);
						y2 = i*ROW_HEIGHT+(ROW_HEIGHT/2);
						gameOver4 = true;
						winner = player;
					}
				}else{
					countPlayer = 0;
				}
			}
		}
		
		for(int r = 5 ; r>=3 ; r--){
			for(int c = 0 ; c<=3 ; c++){
				if(moves[r][c]== player){
					if(moves[r-1][c+1]== player){
						if(moves[r-2][c+2]== player){
							if(moves[r-3][c+3]== player){
								x1 = c*COL_WIDTH+(COL_WIDTH/2);
								y1 = r*ROW_HEIGHT+(ROW_HEIGHT/2);
								x2 = (c+3)*COL_WIDTH+(COL_WIDTH/2);
								y2 = (r-3)*ROW_HEIGHT+(ROW_HEIGHT/2);
								gameOver4 = true;
								winner = player;
							}
						}
					}
				}
			}
		}
		for(int r = 5 ; r>=3 ; r--){
			for(int c = 3 ; c<=6 ; c++){
				if(moves[r][c]== player){
					if(moves[r-1][c-1]== player){
						if(moves[r-2][c-2]== player){
							if(moves[r-3][c-3]== player){
								x1 = c*COL_WIDTH+(COL_WIDTH/2);
								y1 = r*ROW_HEIGHT+(ROW_HEIGHT/2);
								x2 = (c-3)*COL_WIDTH+(COL_WIDTH/2);
								y2 = (r-3)*ROW_HEIGHT+(ROW_HEIGHT/2);
								gameOver4 = true;
								winner = player;
							}
						}
					}
				}
			}
		}		
	}

}
