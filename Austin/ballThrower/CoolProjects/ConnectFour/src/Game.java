import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Game extends JFrame implements Runnable, ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 600;
	public static final int TOP_SPACE = 35;
	public static final int MARGIN = 10;
	
	private boolean gameOver;
	private boolean gameStarted;
	private Button startGame;
	
	public static Game g;
	public Board b;
	
	Image rules;
	
	private BufferedImage buffer;
	private Graphics bufferGraphics;
	
	public static void main(String[] args) {
		g = new Game();
		g.setSize(WIDTH+2*MARGIN, HEIGHT+TOP_SPACE+2*MARGIN);
		g.setResizable(false);
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Thread(g).start();
		g.setVisible(true);
	}
	
	public Game(){
		this.setBackground(Color.black);
		gameOver = false;
		gameStarted = false;
		buffer = new BufferedImage(WIDTH+1,HEIGHT+1, BufferedImage.TYPE_INT_RGB);
		bufferGraphics = buffer.getGraphics();
		this.setLayout(null);		
		startGame = new Button("Start Game");
		startGame.addActionListener(this);
		startGame.setVisible(true);
		this.add(startGame);
		startGame.setBounds(165, 400, WIDTH-300, HEIGHT/4);
		Font f = startGame.getFont().deriveFont(50f);
		startGame.setFont(f);
		
		try{
			rules = ImageIO.read(getClass().getResource("/resources/Rules.png")); 	
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void paint(Graphics g){
		bufferGraphics.setColor(Color.white);
		bufferGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		bufferGraphics.setColor(Color.white);
		if(!gameStarted)bufferGraphics.drawImage(rules, 150, TOP_SPACE+MARGIN, null);
		else{
			b.paint(bufferGraphics);
		}
		
		if(gameOver){
			Font myFont = bufferGraphics.getFont().deriveFont(100f);
			bufferGraphics.setFont(myFont);
			String gameOver = "Game Over";
			bufferGraphics.drawString(gameOver, 100, HEIGHT-150);
			//this.removeMouseListener(b);
			
		}
		g.drawImage(buffer, MARGIN, TOP_SPACE+MARGIN, null);
	}
	
	@Override
	public void run() {
		
		
		while(!gameOver){
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		startGame.setVisible(false);
		b = new Board();
		gameStarted = true;
		this.addMouseListener(b);
		this.requestFocus(true);
		addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Key was pressed");
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			System.out.println("Space was pressed");
			gameOver = false;
			gameStarted = false;
			startGame.setVisible(true);	
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
		
	
}
