import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JFrame;

public class Game extends JFrame implements ActionListener,  ItemListener {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 900;
	public static Game g;

	private Player[] allPlayers;
	private Dice[] myDice;
	private BackgroundImage myBackground;
	private Button rollButton;
	private int rollCount;
	private int whosTurn;
	private boolean gameOver;
	private Checkbox[] selectPlayers;
	private int numberOfPlayers;

		
	public Game() {
		numberOfPlayers = 0;
		gameOver = false;
		allPlayers = new Player[6];
		for (int i = 0; i < 6; i++) {
			allPlayers[i] = new Player(i);
			this.addMouseListener(allPlayers[i]);
		}
		rollCount = 0;
		whosTurn = 0;
		
		
		this.setLayout(null);
		rollButton = new Button("Roll");
		rollButton.addActionListener(this);
		rollButton.setEnabled(false);
		
		this.add(rollButton);
		rollButton.setBounds(750, 5 * Dice.SIZE + 40, 100, 20);
		myBackground = new BackgroundImage();
		myDice = new Dice[5];
		for (int i = 0; i < 5; i++) {
			myDice[i] = new Dice(750, i * Dice.SIZE + 40);
			addMouseListener(myDice[i]);
		}
		selectPlayers = new Checkbox[7];
		for(int i=1 ; i<7 ; i++){
			selectPlayers[i] = new Checkbox(""+i ,false);
			selectPlayers[i].setBounds(750, rollButton.getY()+30+25*i, 100, 20);
			selectPlayers[i].addItemListener(this);
			add(selectPlayers[i]);
		}
	}

	public static void main(String[] args) {
		g = new Game();
		g.setSize(WIDTH, HEIGHT);
		g.setResizable(false);
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g.setVisible(true);
	}

	public void dicePlaced() {
		for (int i = 0; i < 5; i++) {
			myDice[i].reset();
		}
		this.rollButton.setEnabled(true);
		int currentPlayer = this.whosTurn;
		this.whosTurn++;
		if (this.whosTurn == numberOfPlayers)
			this.whosTurn = 0;
		while (whosTurn != currentPlayer
				&& this.allPlayers[whosTurn].scoreCardIsFull()) {
			this.whosTurn++;
			if (this.whosTurn == numberOfPlayers)
				this.whosTurn = 0;
		}
		if (allPlayers[whosTurn].scoreCardIsFull()) {
			gameOver = true;
			this.rollButton.setEnabled(false);
		}
		rollCount = 1;
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for (Dice d : myDice)
			d.paint(g);
		myBackground.paint(g);
		g.setColor(Color.black);
		if (rollCount == 0)g.drawString("SelectNumberOfPlayers", 735, 560);
		else if(rollCount<3){
			g.drawString("Player " + (this.whosTurn+1), 740, 560);
			g.drawString("Click the dice to re-roll", 740, 600);
		    g.drawString("Then press 'Roll' button", 740, 620);
		}
		if (rollCount == 1)g.drawString("You've rolled once", 740, 580);
		if (rollCount == 2)g.drawString("You've rolled twice", 740, 580);
		if (rollCount == 3){
			g.drawString("You've rolled thrice", 740, 580);
			g.drawString("Click scorecard location", 740, 560);
		}
		for (int i = 0; i < numberOfPlayers; i++) {
			allPlayers[i].paint(g);
		}
		g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		if(numberOfPlayers>0){
			g.fillRect(whosTurn * 75 + 269, 108, 75, 318);
			g.fillRect(whosTurn * 75 + 269, 470, 75, 360); // 830
		}
		if (this.gameOver) {
			Font f = g.getFont().deriveFont(130f);
			g.setFont(f);
			g.drawString("Game Over", 60, 500);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		for (int i = 0; i < 5; i++) {
			myDice[i].roll();
		}
		rollCount++;
		if (rollCount == 3) {
			this.rollButton.setEnabled(false);
		}
		allPlayers[whosTurn].placeDice(myDice);
		repaint();

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		for(int i=1 ; i<7 ; i++){
			if(selectPlayers[i] == e.getSource()){
				this.numberOfPlayers = i;
				System.out.println(i);
			}
			remove(selectPlayers[i]);
		}
		rollCount = 1;
		this.rollButton.setEnabled(true);
		this.myBackground.startGame();
		for (int i = 0; i < 5; i++) {
			myDice[i].reset();
		}
		repaint();
	}

}
