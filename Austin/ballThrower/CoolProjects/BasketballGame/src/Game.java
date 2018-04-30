import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;


public class Game extends JFrame implements ActionListener, MouseListener, MouseMotionListener, Runnable, KeyListener, ItemListener{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 563;
	
	public static Game g;
	private BackgroundImage myBackground;
	private static boolean isGameStarted;
	private boolean gameOver;
	private Button startButton;
	private int shotsLeft;
	private MouseEvent released;
	private boolean mousePressed;
	private Basketball basketball;
	private Hoop hoop;
	private BufferedImage offScreen;
	private final int ballXInit, ballYInit;
	private static int hoopX, hoopY;
	private int score;
	private Checkbox[] mode;
	private boolean isSmiley;
	
	private Game(){
		gameOver = false;
		isGameStarted = false;
		shotsLeft = 20;
		offScreen = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_BGR);
		ballXInit=15;
		ballYInit=HEIGHT-100;
		hoopX = WIDTH-85;
		hoopY=50;
		score = 0;
		isSmiley = false;
		
		mode = new Checkbox[4];
		mode[0] = new Checkbox("Easy" ,false);
		mode[0].setBounds(750, 120, 100, 20);
		mode[0].addItemListener(this);
		add(mode[0]);
		mode[1] = new Checkbox("Medium" ,false);
		mode[1].setBounds(750, 145, 100, 20);
		mode[1].addItemListener(this);
		add(mode[1]);
		mode[2] = new Checkbox("Hard" ,false);
		mode[2].setBounds(750, 170, 100, 20);
		mode[2].addItemListener(this);
		add(mode[2]);
		mode[3] = new Checkbox("Smiley" ,false);
		mode[3].setBounds(750, 195, 100, 20);
		mode[3].addItemListener(this);
		add(mode[3]);
		
		this.setLayout(null);
		startButton = new Button("Start Game");
		startButton.addActionListener(this);
		startButton.setEnabled(false);
		
		this.add(startButton);
		startButton.setBounds(750, 300, 100, 20);
		myBackground = new BackgroundImage();
		
		addKeyListener(this);
		
		mousePressed = false;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	
	public static void main(String []args){
		g = new Game();
		g.setSize(WIDTH, HEIGHT);
		g.setResizable(false);
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Thread(g).start();
		g.setVisible(true);
	}

	
	
	public void paint(Graphics g){
		if(g==null)return;
		Graphics g2 = offScreen.getGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		g2.setColor(Color.white);
		
		myBackground.paint(g2);		
		if (this.gameOver) {
			g2.drawString("Game Over", WIDTH/2, HEIGHT/2);
			g2.drawString("Final Score: " + score, WIDTH/2, HEIGHT/2+20);
			removeMouseListener(this);
			
		}
		if (isGameStarted){	
			basketball.paint(g2);
			hoop.paint(g2);
			g2.setColor(Color.white);
			g2.drawString("You have " + shotsLeft + " shots left.", 60, 100);
			
			g2.setColor(Color.white);
			g2.drawString("You have " + shotsLeft + " shots left.", 60, 100);
			g2.drawString("Your score is " + score + ".", 60, 130);
			basketball.paint(g2);
			hoop.paint(g2);
			addKeyListener(this);
			
			if(mousePressed){
				g2.setColor(Color.white);
				g2.drawLine((int)basketball.getX()+30, (int)basketball.getY()+30, released.getX(), released.getY());
			}
						
		}
		g.drawImage(offScreen,0,0,this);
		
		
	}
	
	public void gameStart(){
		hoop = new Hoop(hoopX, hoopY);
		basketball = new Basketball(ballXInit, ballYInit, 0,0,isSmiley);
		repaint();
	}

	public void shotTaken(){
		shotsLeft -=1;
		if (shotsLeft == 0)gameOver=true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		myBackground.startGame();
		isGameStarted=true;
		g.gameStart();
		this.remove(startButton);
		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		released = e;
		mousePressed = true;
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
		double x = e.getX();
		double y = e.getY();
		double vX = x - basketball.getX()-30;
		double vY = y - basketball.getY()-30;
		basketball.push(vX*.1, vY*.1);
		repaint();
		shotTaken();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		released = e;
		repaint();		
	}

	@Override
	public void run() {
		while(basketball==null){
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while(true){
			basketball.update();
			if (basketball.doHit(hoop, hoopX, hoopY)){
				score +=1;
				basketball.setLocation(ballXInit, ballYInit);
				repaint();
			}
			repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		for(int i=0 ; i<4 ; i++){
			if(mode[i] == e.getSource()){
				if(i==3){
					isSmiley=true;
					hoopY = 350;
				}
				else hoopY = 100+(2-i)*150;
			}
			remove(mode[i]);
			startButton.setEnabled(true);
		}
		repaint();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			basketball.setLocation(ballXInit, ballYInit);
			basketball.vX = 0;
			basketball.vY = 0;
			shotsLeft = 20;
			score = 0;
			gameOver = false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
}
