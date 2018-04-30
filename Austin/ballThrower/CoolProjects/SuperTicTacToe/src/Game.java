import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;


public class Game extends JFrame implements MouseListener, ActionListener{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;
	public static final int TOP_SPACE = 25;
	public static final int BOTTOM_SPACE = 50;
	
	
	private BufferedImage buffer;
	private Graphics bufferGraphics;
	private ArrayList<Player> allPlayers;
	private int whosTurn;
	
	private boolean gameOver;
	private boolean gameStarted;
	
	private Button startGame;
	private Button addPlayer;
	private TextField playerName;
	private JComboBox symbolOptions;
	private Label symbolOptionsLabel;
	private Label playerNameLabel;
	
	
	GameBoard gb;
	
	private Game(){
		setLayout(null);
		String [] options = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		symbolOptions = new JComboBox(options);
		add(symbolOptions);
		symbolOptions.setBounds(50, 110, 40, 20);
		symbolOptionsLabel = new Label("Select symbol for next player");
		add(symbolOptionsLabel);
		symbolOptionsLabel.setBounds(110, 110, 200, 20);

		playerNameLabel = new Label("Type Player Name");
		add(playerNameLabel);
		playerNameLabel.setBounds(170, 80, 200, 20);
		
		//symbolOptions.addActionListener(this);
		addPlayer = new Button("Add Player");
		add(addPlayer);
		addPlayer.addActionListener(this);
		addPlayer.setBounds(90, 140, 200, 20);
		
		startGame = new Button("Start Game");
		startGame.setBounds(50, 50, 200, 20);
		startGame.addActionListener(this);
		startGame.setEnabled(false);
		add(startGame);
		
		playerName = new TextField();
		playerName.setBounds(50, 80, 200, 20);
		add(playerName);
		
		gameOver = false;
		gameStarted = false;
		allPlayers = new ArrayList<Player>();
		whosTurn = 0;
		buffer = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		bufferGraphics = buffer.getGraphics();
		gb = new GameBoard();
		addMouseListener(this);
	}
	
	
	public static void main(String[] args) {
		Game g = new Game();
		g.setSize(WIDTH, HEIGHT+TOP_SPACE+BOTTOM_SPACE);
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g.setResizable(false);
		g.setVisible(true);
	}
	
	public void paint(Graphics g){
		if(gameStarted){
			bufferGraphics.setColor(Color.white);
			bufferGraphics.fillRect(0, 0, WIDTH, HEIGHT);
			gb.draw(bufferGraphics);
			if(gameOver){
				bufferGraphics.setColor(new Color(1f,0f,0f,0.25f));
				bufferGraphics.drawString("GameOver", 50,300);
			}
			g.drawImage(buffer, 0,TOP_SPACE,null);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, HEIGHT+TOP_SPACE, WIDTH, BOTTOM_SPACE);
			g.setColor(Color.black);
			if(gameOver){
				g.drawString(allPlayers.get(whosTurn).getName() + " is the winner.", 50,HEIGHT+ TOP_SPACE + 20);
			}else{
				g.drawString(allPlayers.get(whosTurn).getName() + " Select an empty square", 50,HEIGHT+ TOP_SPACE + 20);
			}
		}else{
			int x = 400;
			int y = 150;
			for(Player p : allPlayers){ 
				p.paint(g, x, y);
				y += 25;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(gameOver || !gameStarted)return;
		int col = e.getX()*GameBoard.NUM_COLS/WIDTH;
		int row = (e.getY()-TOP_SPACE)*GameBoard.NUM_ROWS/HEIGHT;
		if(gb.spotEmpty(row, col)){
			gb.addPiece(row, col, allPlayers.get(whosTurn).getSymbol());
			if(gb.checkForWinner(row, col)) gameOver = true;
			if(!gameOver)whosTurn++;
			if(whosTurn == allPlayers.size()) whosTurn = 0;
			
		}
		repaint();
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


	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if(ob == startGame){
			remove(startGame);
			remove(symbolOptions);
			remove(addPlayer);
			remove(playerName);
			remove(playerNameLabel);
			remove(symbolOptionsLabel);
			gameStarted = true;
		}else{
			char symbol = ((String)symbolOptions.getSelectedItem()).charAt(0);
			String name = playerName.getText();
			if(name.equals(""))return;
			allPlayers.add(new Player(symbol,name));
			symbolOptions.removeItemAt(symbolOptions.getSelectedIndex());
			symbolOptions.grabFocus();
			playerName.setText("");
	        playerName.requestFocus();	        
	        if(allPlayers.size()==2) startGame.setEnabled(true);
		}
		repaint();
	}

}
