import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

//Player not controlled by user with username
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	public Color color;
	public String username;
	public ArrayList<Cell> cells;
	Player(Color color, int mass, String username) {
		this.color = color;
		this.username = username;
		cells = new ArrayList<Cell>();
		cells.add(new Cell(color, mass));
	}
	public void update() {
		for (Cell cell : cells) {
			cell.update();
		}
	}
	
	public int getMass() {
		int ret = 0;
		for (Cell c : cells) {
			ret += c.mass;
		}
		return ret;
	}
	public UserPlayer getUserPlayer() {
		return (UserPlayer)this;
	}
	public Point getCenter() {
		int totalX = 0;
		int totalY = 0;
		int meanX;
		int meanY;
		int size = cells.size();
		for (Cell cell : cells) {
			totalX += cell.mapPosX;
			totalY += cell.mapPosY;
		}
		meanX = totalX / size;
		meanY = totalY / size;
		//System.out.println("[Player] Center X: "+meanX);
		return new Point(meanX, meanY);
	}
	
	public void draw(Graphics g) {
		for (Cell cell: cells) {
			cell.draw(g);
		}
	}
}
