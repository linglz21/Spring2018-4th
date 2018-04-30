import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Snake extends JFrame implements KeyListener {

	List<Tile> myTiles;
	int vX, vY;
	static final int SPEED = 10;

	public Snake() {
		myTiles = new LinkedList<Tile>();
		myTiles.add(new Tile());
	}

	public void update() {
		myTiles.add(0, new Tile(myTiles.get(0), vX * SPEED, vY * SPEED));
		myTiles.remove(myTiles.size() - 1);
	}

	public void draw(Graphics g) {
		for (Tile t : myTiles)
			t.draw(g);
	}

	public int length() {
		return myTiles.size();
	}

	public boolean eat(Food f) {
		int dX = myTiles.get(0).x - f.x;
		int dY = myTiles.get(0).y - f.y;
		double dist = Math.sqrt(dX * dX + dY * dY);
		if (dist > 30)
			return false;
		myTiles.add(0, new Tile(myTiles.get(0), vX * SPEED, vY * SPEED));
		return true;
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			vY = -1;
			vX = 0;
			
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			vY = 1;
			vX = 0;
			
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			vY = 0;
			vX = -1;
			
		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			vY = 0;
			vX = 1;
			;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			vY = -1;
			vX = 0;
			
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			vY = 1;
			vX = 0;
			
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			vY = 0;
			vX = -1;
			
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			vY = 0;
			vX = 1;
			
		}
		if (e.getKeyCode() == KeyEvent.VK_R) {
			MainClass.restart();
			// restart game with key "R"
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	public boolean isOffScreen() {
		int x = myTiles.get(0).x;
		int y = myTiles.get(0).y;
		return x < 0 || y < 30 || x > MainClass.WIDTH || y > MainClass.HEIGHT;
	}

	public boolean hitSelf() {
		int x = myTiles.get(0).x;
		int y = myTiles.get(0).y;
		for (int i = 2; i < myTiles.size(); i++) {
			Tile temp = myTiles.get(i);
			int dX = x - temp.x;
			int dY = y - temp.y;
			if (Math.sqrt(dX * dX + dY * dY) < 12)
				return true;
		}
		return false;
	}

}

// make first tile of snake different color
