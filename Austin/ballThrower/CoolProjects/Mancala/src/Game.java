import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import test.videoRecorder.*;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Game extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 1369;
	public static int HEIGHT = 383;
	
	private MyVideoRecorder rec;
	private BufferedImage recImage;
	private Graphics recGraphics;

	private int whosTurn;
	private Board theBoard;
	private BoardHelper theBoardHelper;
	private Player[] thePlayers;
	private boolean gameOver;

	private String message;
	private String errorMessage;
	private Font f;

	public Game() {
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/resources/FFF_Tusj.ttf").openStream())
					.deriveFont(30.0f);
		} catch (Exception e) {
		}
		message = "Player A go";
		errorMessage = "";
		whosTurn = 0;
		theBoard = new Board();
		theBoardHelper = new BoardHelper(theBoard);
		thePlayers = new Player[2];
		thePlayers[0] = new ComputerPlayerThree(0);
		thePlayers[1] = new ComputerPlayerTwo(1);
		this.addMouseListener(thePlayers[0]);
		this.addMouseListener(thePlayers[1]);
		this.addMouseListener(theBoard);
		gameOver = false;
		boolean done = false;
		JOptionPane.showMessageDialog(null, "You must select a place to save your video");
		while(!done){
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("AVI File","avi");
		    fc.setFileFilter(filter);
		    fc.setSelectedFile(new File("test.avi"));
		    int rVal = fc.showOpenDialog(this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				done = true;
			}
			if (rVal == JFileChooser.CANCEL_OPTION) {
				JOptionPane.showMessageDialog(null, "You must select a place to save your video");
			}
			File f = fc.getSelectedFile();
			System.out.println(f);
			rec = new MyVideoRecorder(fc.getSelectedFile(), 30, 1f, WIDTH,HEIGHT);
			recImage = rec.getImage();
			recGraphics = rec.getGraphics();
		}
	}
	

	public static void main(String[] args) {
		Game theGame = new Game();
		theGame.setSize(WIDTH, HEIGHT+35);
		theGame.setResizable(false);
		theGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Thread(theGame).start();
		theGame.setVisible(true);
	}

	public void paint(Graphics g) {
		rec.acquire();
		Graphics2D g2 = (Graphics2D)g;
		g2.setTransform(AffineTransform.getTranslateInstance(0, 30));
		recGraphics.setColor(Color.white);
		recGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		theBoard.paint(recGraphics);
		if(theBoard.hasBeenClicked()){
			Graphics2D rg2 = (Graphics2D)recGraphics;
			rg2.setRenderingHint(
			        RenderingHints.KEY_TEXT_ANTIALIASING,
			        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			recGraphics.setFont(f);
			recGraphics.drawString(message, 1000, 200);
			recGraphics.drawString(errorMessage, 200,200);
			recGraphics.drawString("Side B", 630,30);
			recGraphics.drawString("Side A", 630,375);
			recGraphics.drawString("Store B", 40,30);
			recGraphics.drawString("Store A", 1220,30);
		}
		g2.drawImage(recImage, 0, 0, WIDTH, HEIGHT, null);
		if(!gameOver)errorMessage = "";
		rec.release();
		rec.finishFrame();
	}

	public void run() {
		while (!theBoard.hasBeenClicked()){
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
			}
		}
		int countAttemps = 0;
		while (!gameOver) {
			doAnimation();
			Pit p =  theBoard.getPit(thePlayers[whosTurn].selectMove(theBoardHelper), whosTurn);
			if(p==null){
				errorMessage = "You must pick a pit on your own side";
				
			}else if (p.stoneCount() > 0) {
				Pit lastPit = theBoard.redistribute(p);
				doAnimation();
				if(lastPit != null){
					for(int i=0 ; i<20 ; i++){
						errorMessage = "Stones were captured";
						this.paint(this.getGraphics());
					}
					theBoard.capture(lastPit);
					doAnimation();
				}
				whosTurn = (whosTurn + 1) % 2;
				countAttemps = 0;
				if(whosTurn==0){
					message = "Player A go";
				}else{
					message = "Player B go";
				}
			} else {
				errorMessage += "The spot you picked was empty. You have " + (300-countAttemps) + " tries left." ;
				countAttemps++;
			}
			doAnimation();
			int winner = theBoard.checkForWinner(whosTurn); 

			if (winner != -1 || countAttemps==300){
				// add code to determine winner if attempts is too big
				gameOver = true;
				message = "";
				if(winner==0){
					errorMessage = "Game Over Player A wins";
				}else if(winner==1){
					errorMessage = "Game Over Player B wins";
				}else{
					errorMessage = "Game Over it's a tie";
				}
				doAnimation();
			}
		}
		doAnimation();
		rec.finishVideo();
	}
	
	private void doAnimation(){
		this.paint(this.getGraphics());
		while(!theBoard.isStopped()){
			this.paint(this.getGraphics());
		}
		
	}
}
