import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class MainClass extends JFrame implements Runnable, KeyListener {
	public static final int WIDTH = 720;
	public static final int HEIGHT = 800;

	private double x, y;

	BufferedImage offscreen, background;
	Graphics bg;
	//ArrayList<Deck> Cards;
	boolean gameStart;
	ArrayList<Card> playerHand;
	ArrayList<Card> dealerHand;
	Deck myDeck;
	boolean gameOver;
	
	
	

	public MainClass() {
		myDeck = new Deck();
		playerHand = new ArrayList<Card>();
		playerHand.add(myDeck.deal());
		playerHand.add(myDeck.deal());
//		for(Card c : playerHand)c.flip();
		dealerHand = new ArrayList<Card>();
		dealerHand.add(myDeck.deal());
		for(Card c : dealerHand)c.flip();
		dealerHand.add(myDeck.deal());
		offscreen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		bg = offscreen.getGraphics();
		try {
			background = ImageIO.read(MainClass.class
					.getResourceAsStream("/resources/PokerBlackjack'.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.addKeyListener(this);
		
	}

		public static void Bust(){
			
		}
	
		

				
				
	

	public static void main(String[] args) {
		MainClass mc = new MainClass();
		mc.setSize(WIDTH, HEIGHT);
		mc.setResizable(false);
		mc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mc.setVisible(true);
	}

	public void paint(Graphics g) {
		bg.drawImage(background, 0, 0, null);
		bg.setColor(Color.WHITE);
		bg.drawString("Player 2", (int) (WIDTH/4.5), HEIGHT/5);
		bg.drawString("Player 1", (int) (WIDTH-555), HEIGHT-230);
		
	
		for(int i=0 ; i<playerHand.size() ; i++){
			playerHand.get(i).draw(bg, 128+60*i, HEIGHT-197);
		}
		for(int i=0 ; i<dealerHand.size() ; i++){
			dealerHand.get(i).draw(bg, 128+60*i, 197);
		}
		g.drawImage(offscreen, 0, 0, null);
		}
		

	
	
	@Override
	public void keyPressed(KeyEvent e) {
	
			}
		


		
	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("Pressed");
		if (e.getKeyCode() == KeyEvent.VK_H){
			playerHand.add(myDeck.deal());
			repaint();
		}
			if (e.getKeyCode() == KeyEvent.VK_ENTER){
			for(Card c : dealerHand)if(c.isDown())c.flip();
			repaint();
			}
	}

	
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	
	public void run() {
		// TODO Auto-generated method stub
		
	}
}


