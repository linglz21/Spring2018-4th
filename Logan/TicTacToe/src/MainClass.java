import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class MainClass extends JFrame implements Runnable, MouseListener{
	public static final int WIDTH = 600;
	public static final int SHIFT = 35;
	public static final int HEIGHT = WIDTH + SHIFT;

	BufferedImage offscreen;
	Graphics bg;
	char[][] grid;
	boolean xTurn;
	public boolean gameOver;
	public boolean consecutive;
	public int moveCount;
	private WinLine myWinLine;
	
	public MainClass() {
		xTurn = true;
		grid = new char[3][3];
		offscreen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		bg = offscreen.getGraphics();
		Font f = bg.getFont().deriveFont(175f);
		bg.setFont(f);
		this.addMouseListener(this);
	}



	public static void main(String[] args) {
		MainClass mc = new MainClass();
		mc.setSize(WIDTH, HEIGHT);
		mc.setResizable(false);
		mc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mc.setVisible(true);
		
	}

	public void paint(Graphics g) {
		bg.setColor(Color.white);
		bg.fillRect(0, 0, WIDTH, HEIGHT);
		bg.setColor(Color.black);
		bg.fillRect(200, 30, 10, HEIGHT);
		bg.fillRect(400, 30, 10, HEIGHT);
		bg.fillRect(0, 230, WIDTH, 10);
		bg.fillRect(0, 430,  WIDTH, 10);
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				if (grid[r][c] == 'X'|| grid[r][c] == 'O') {
					System.out.println("drawing something");
					bg.drawString(""+grid[r][c], c*200+50, r*200+200);
				}
			}
		}
		if(myWinLine!=null)myWinLine.draw(bg);
		if(gameOver){		
			Font f = bg.getFont().deriveFont(75f);
			bg.setFont(f);
			bg.setColor(Color.red);
			bg.drawString("Game Over",100, 350);
		}else if(moveCount==9){
			Font f = bg.getFont().deriveFont(75f);
			bg.setFont(f);
			bg.setColor(Color.red);
			bg.drawString("CAT",250, 375);
		}
		g.drawImage(offscreen, 0, 0, null);
		
		//create game and start it 
		// first player will be "X"
		

			

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		if(gameOver)return;
		// print the x and y location where they click
		int c = e.getX()/200;
		int r = e.getY()/200;
		String str = "MousePressed,";
		
		
		System.out.println("("+r+","+c+")");
		if(grid[r][c]!='X' && grid[r][c]!='O'){
			if(xTurn)grid[r][c] = 'X';
			else grid[r][c] = 'O';
			xTurn = !xTurn;
			moveCount++;
			if(won(r,c))gameOver = true;
		
		}
		// convert x and y into row and col
		// add an X or O to the row and col of the grid
		repaint();
	}

	
	private boolean won(int r, int c) {
		char symbol = grid[r][c];
		boolean didWin = true;
		for(int i=0 ; i<3 ; i++ )if(grid[r][i]!=symbol)didWin = false;
		if(didWin){
			myWinLine = new WinLine(r,-1,-1);
			return true;
		}
		didWin = true;
		for(int i=0 ; i<3 ; i++ )if(grid[i][c]!=symbol)didWin = false;
		if(didWin){
			myWinLine = new WinLine(-1,c,-1);
			return true;
		}
		
		// add code to check the diagonals
		if(grid[0][0]==symbol && grid[1][1]==symbol &&grid[2][2]==symbol ){
			myWinLine = new WinLine(-1,-1,1);
			return true;
		}
		if(grid[0][2]==symbol && grid[1][1]==symbol &&grid[2][0]==symbol ){
			myWinLine = new WinLine(-1,-1,2);
			return true;
		}
		return false;
		
		
 


 }

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
