import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

//Player with usercells instead of cells
public class UserPlayer extends Player{
	private static final long serialVersionUID = 1L;
	UserPlayer(Color color, int mass, String username) {
		super(color, mass, username);
		init();
	}
	UserPlayer(Player player){
		super(player.color, player.getMass(), player.username);
		this.cells = player.cells;
		init();
	}
	
	private void init() {
		for (int i = 0; i < cells.size(); i++) {
			setUserCell(i, new UserCell(cells.get(i)));
		}
	}
	private void setUserCell(int i, UserCell cell) {
		cells.set(i, cell);
	}
	public void update() {
		for (Cell cell : cells) {
			UserCell uCell = (UserCell)cell;
			uCell.update();
		}
	}

	public void addMass(int i) {
		int addPerCell = i/cells.size();
		System.out.println("[UserPlayer] Mass to add per cell: "+addPerCell);
		for (Cell c : cells) {
			System.out.println("[UserPlayer] Adding mass");
			c.mass += addPerCell;
		}
	}
	public void split() {
		int largestSize = 0;
		UserCell largestCell = null;
		for (Cell cell : cells) {
			if (cell.mass > largestSize) { 
				largestSize = (int)cell.mass;
				largestCell = (UserCell)cell;
			}
		}
		cells.remove(cells.indexOf(largestCell));
		
		cells.add(new UserCell(largestCell.color, (int)largestCell.mass/2));
		cells.add(new UserCell(largestCell.color, (int)largestCell.mass/2));	
		//Need to rejoin cells after period of time
	}
	
	public void draw(Graphics g) {
		//for every cell
		//if this cell is colliding with any other cell, move to outside
		//move in direction of other cell's radius
		//System.out.println("[UserPlayer] Drawing cell(s)...");
		for (Cell cellToTest : cells) {
			//System.out.println("[UserPlayer] Cell to test: #"+cells.indexOf(cellToTest));
			for (Cell cell : cells) {	
				if (cellToTest.isColliding(cell) && cellToTest != cell) {
					System.out.println("[UserPlayer] Cell to test is colliding with this cell");
					double rise = cell.mapPosY - cellToTest.mapPosY;
					double run = cell.mapPosX - cellToTest.mapPosX;
					//double distanceToEdge = Math.sqrt(Math.pow((cell.mapPosX-cellToTest.mapPosX), 2)+Math.pow((cell.mapPosY-cellToTest.mapPosY), 2));
					while (cellToTest.isColliding(cell)) {
						cellToTest.mapPosY += rise;
						cellToTest.mapPosX += run;
					}
				}
			}
			//Now draw cells
			super.draw(g);

			//System.out.println("[UserPlayer] Drawing username '"+username+"'");
			g.drawString(username, this.getCenter().x, this.getCenter().y);
		}
	}

	
	
}
