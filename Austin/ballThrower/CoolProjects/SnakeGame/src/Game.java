import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Rules.Direction;
import Rules.Sound;

public class Game extends JFrame implements ActionListener, KeyListener,
		Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1080;
	public static final int HEIGHT = 700;
	public static final int GRID_WIDTH= 1015;
	public static final int GRID_HEIGHT= 630;
	public static final int BlockSize = 35;
	public static Game g;
	private boolean switchScreen = false;
	private boolean done = false;
	private LinkedList<Point> snake;
	private Point fruit;
	private int direction = Direction.NO_DIRECTION;
	private Thread runThread;
	private int score = 0;

	BufferedImage myImage;
	BufferedImage myImage2;
	BufferedImage myImage3;

	BufferedImage buffer;
	Graphics bufferGraphics;

	private Button startButton;

	public static void main(String[] args) {
		Game g = new Game();
		g.setSize(WIDTH, HEIGHT);
		g.setResizable(false);
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g.setFocusable(true);
		g.setVisible(true);
	}

	public Game() {
		this.setLayout(null);
		buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		bufferGraphics = buffer.getGraphics();
		this.addKeyListener(this);

		try {
			myImage = ImageIO.read(getClass().getResource("/Rules/New Snake Rules.png"));
			myImage2 = ImageIO.read(getClass().getResource("/Rules/Cartoon Snake.png"));
			myImage3 = ImageIO.read(getClass().getResource("/Rules/Cartoon Apple"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		startButton = new Button("Start Game");
		startButton.setBounds(650, 505, 420, 160);
		this.add(startButton);
		startButton.addActionListener(this);
		startButton.setEnabled(true);
		init2();
	}

	public void start(){
		init();
		new Thread(this).start();
	}
	
	public void init() {
		Sound.sound1.play();
	}
	public void init1(){
		Sound.sound2.play();
	}
	public void init2(){
		Sound.sound3.play();
	}

	public void paint(Graphics g) {
		//draws white background, snake, fruit, and grid
		bufferGraphics.setColor(Color.white);
		bufferGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		bufferGraphics.drawImage(myImage, 0, 25, null);
		bufferGraphics.drawImage(myImage2, 605, 25, null);
		if (switchScreen == true) {
			bufferGraphics.setColor(Color.white);
			bufferGraphics.fillRect(0, 0, WIDTH, HEIGHT);
			bufferGraphics.setColor(Color.black);
			drawSnake(bufferGraphics);
			drawFruit(bufferGraphics);
			drawGrid(bufferGraphics);
			drawScore(bufferGraphics);
		}
		
		g.drawImage(buffer, 10, 0, null);
	}

	public void generateDefaultSnake(){
		//position of snake when it resets
		score = 0;
		snake.clear();
		snake.add(new Point (1,4));
		snake.add(new Point (1,3));
		snake.add(new Point (1,2));
		direction= Direction.NO_DIRECTION;
	}
	
	private void drawScore(Graphics g) {
		Font myFont = g.getFont().deriveFont(30f);
		g.setFont(myFont);
		g.drawString("Score: " + score, 10, 690);
	}
	
	private void drawGrid(Graphics g) {
		// rectangle
		g.drawRect(0, 35, GRID_WIDTH, GRID_HEIGHT);
		// vertical lines
		for (int x = BlockSize; x <= WIDTH-50; x += BlockSize) {
			g.drawLine(x, 35, x,HEIGHT-35);
		}
		//horizontal lines
		for (int y = BlockSize; y <= HEIGHT-40; y += BlockSize) {
			g.drawLine(0, y, GRID_WIDTH, y);
		}
	}

	public void move() throws IOException {
		//draws snake over and over again
		String gameOverMsg = "GAME OVER! Your score was " + score + ". Do you wish to continue?";
		
		if(direction == Direction.NO_DIRECTION){
			return;
		}
		
		Point head = snake.peekFirst();
		Point newPoint = head;
		switch (direction) {
		case Direction.NORTH:
			newPoint = new Point(head.x, head.y - 1);
			break;
		case Direction.SOUTH:
			newPoint = new Point(head.x, head.y + 1);
			break;
		case Direction.WEST:
			newPoint = new Point(head.x - 1, head.y);
			break;
		case Direction.EAST:
			newPoint = new Point(head.x + 1, head.y);
			break;
		case Direction.NOWHERE:
			newPoint = new Point(head.x, head.y);
			break;
		}
		snake.remove(snake.peekLast());

		if (newPoint.equals(fruit)) {
			// snake hits fruit and 10 points are added to the score
			score += 10;
			Point addPoint = (Point) newPoint.clone();
			
			switch (direction) {
			case Direction.NORTH:
				newPoint = new Point(head.x, head.y - 1);
				break;
			case Direction.SOUTH:
				newPoint = new Point(head.x, head.y + 1);
				break;
			case Direction.WEST:
				newPoint = new Point(head.x - 1, head.y);
				break;
			case Direction.EAST:
				newPoint = new Point(head.x + 1, head.y);
				break;
			case Direction.NOWHERE:
				newPoint = new Point(head.x, head.y);
				break;
			}
			snake.push(addPoint);
			placeFruit();
			init();
			
		} else if (newPoint.x < 0 || newPoint.x > 28) {
			// out of bounds (x)
			init1();
			generateDefaultSnake();
			int gameOver = JOptionPane.showConfirmDialog(startButton, gameOverMsg);
			if(gameOver == JOptionPane.YES_OPTION){
				generateDefaultSnake();
			}
			else if(gameOver == JOptionPane.NO_OPTION){
				startButton.setVisible(true);
				switchScreen = false;
				done = true;
				repaint();
				init2();
			}
			else if(gameOver == JOptionPane.CANCEL_OPTION || gameOver == JOptionPane.CLOSED_OPTION){
				generateDefaultSnake();
			}
			return;
		} else if (newPoint.y < 1 || newPoint.y > 18) {
			// out of bounds (y)
			init1();
			generateDefaultSnake();
			int gameOver = JOptionPane.showConfirmDialog(startButton, gameOverMsg);
			if(gameOver == JOptionPane.YES_OPTION){
				generateDefaultSnake();
			}
			else if(gameOver == JOptionPane.NO_OPTION){
				startButton.setVisible(true);
				switchScreen = false;
				done = true;
				repaint();
				init2();
			}
			else if(gameOver == JOptionPane.CANCEL_OPTION || gameOver == JOptionPane.CLOSED_OPTION){
				generateDefaultSnake();
			}
			return;
		} else if (snake.contains(newPoint)) {
			// ran into itself
			init1();
			generateDefaultSnake();
			int gameOver = JOptionPane.showConfirmDialog(startButton, gameOverMsg);
			if(gameOver == JOptionPane.YES_OPTION){
				generateDefaultSnake();
			}
			else if(gameOver == JOptionPane.NO_OPTION){
				startButton.setVisible(true);
				switchScreen = false;
				done = true;
				repaint();
				init2();
			}
			else if(gameOver == JOptionPane.CANCEL_OPTION || gameOver == JOptionPane.CLOSED_OPTION){
				generateDefaultSnake();
			}
			return;
		}
		snake.push(newPoint);
	}
	
	public void drawSnake(Graphics g) {
		//draws snake
		g.setColor(Color.green);
		for (Point p : snake) {
			g.fillRect(p.x * BlockSize, p.y * BlockSize, BlockSize, BlockSize);
		}
		g.setColor(Color.black);
	}

	public void drawFruit(Graphics g) {
		//draws fruit
		g.setColor(Color.red);
		g.drawImage(myImage3, fruit.x * BlockSize, fruit.y * BlockSize, BlockSize, BlockSize, startButton);
		g.setColor(Color.black);
	}
	
	public void placeFruit(){
		//places fruit in random spot on grid
		Random rand = new Random();
		int randomX = rand.nextInt(28);
		int randomY = rand.nextInt(18);
		Point randomPoint = new Point(randomX, 1+randomY);
		while(snake.contains(randomPoint)){
			randomX = rand.nextInt(28);
			randomY = rand.nextInt(18);
			randomPoint = new Point(randomX, 1+randomY);
		}
		fruit = randomPoint;
	}
	
	public void actionPerformed(ActionEvent e) {
		startButton.setVisible(false);
		switchScreen = true;
		this.setPreferredSize(new Dimension(GRID_WIDTH, GRID_HEIGHT));
		snake = new LinkedList<Point>();
		generateDefaultSnake();
		placeFruit();
		done = false;
		runThread = new Thread(this);
		runThread.start();
		if(runThread == null){
			runThread = new Thread(this);
			runThread.start();
		}
		repaint();
		JOptionPane.showMessageDialog(null, "Press down, left, or right to start.", "Let the games begin!", JOptionPane.ERROR_MESSAGE); 
	}

	@Override
	public void run() {
		while (!done) {
			try {
				move();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			repaint();
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			if(direction != Direction.SOUTH)
				//prevents going the opposite direction the way the snake is facing
				direction = Direction.NORTH;
			break;
		case KeyEvent.VK_DOWN:
			if(direction != Direction.NORTH)
				//prevents going the opposite direction the way the snake is facing
				direction = Direction.SOUTH;
			break;
		case KeyEvent.VK_RIGHT:
			if(direction != Direction.WEST)
				//prevents going the opposite direction the way the snake is facing
				direction = Direction.EAST;
			break;
		case KeyEvent.VK_LEFT:
			if(direction != Direction.EAST)
				//prevents going the opposite direction the way the snake is facing
				direction = Direction.WEST;
			break;
		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			//starts game at menu again
			startButton.setVisible(true);
			switchScreen = false;
			done = true;
			repaint();
			init2();
		}
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			//resets the game
			direction = Direction.NOWHERE;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {

	}
}