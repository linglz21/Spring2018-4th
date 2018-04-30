import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map extends Sprite {
	int[][] myMap = new int[GameRunner.HEIGHT][GameRunner.WIDTH];
	static final int SAND = 1;
	static final int ROAD = 2;
	static final int EARTH = 3;
	private BufferedImage bufferedImage;

	/**
	 * 
	 */
	Map() {
		for (int row = GameRunner.HEIGHT - 300; row < GameRunner.HEIGHT; row++) {
			for (int col = 0; col < GameRunner.WIDTH; col++) {
				myMap[row][col] = (int) (Math.random() * 3) + 1;
			}
		}
	}

	/**
	 * @param f file of the map
	 */
	Map(String s) {
		try {
			bufferedImage = ImageIO.read(getClass().getResource("/resources/" + s));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int row = 0; row < GameRunner.HEIGHT; row++) {
			for (int col = 0; col < GameRunner.WIDTH; col++) {
				int test = bufferedImage.getRGB(col, row);
				int alpha = (test >> 24) & 0xFF;
				int red = (test >> 16) & 0xFF;
				int green = (test >> 8) & 0xFF;
				int blue = (test) & 0xFF;
				////////////Start Requirement:2 Logic Example: 4
				if (red > 200 && green > 200 && blue < 50)
					myMap[row][col] = SAND;
				if (red < 100 && green < 100 && blue < 100)
					myMap[row][col] = ROAD;
				if (red < 100 && green < 100 && blue < 30)
					myMap[row][col] = EARTH;
				////////////Start Requirement:2 Logic Example: 4
			}
		}
		int rY = (int) (Math.random() * GameRunner.HEIGHT);
		int rX = (int) (Math.random() * GameRunner.WIDTH);

	}

	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return if there is something at the x,y location
	 */
	public boolean isOverlapping(int x, int y) {
		if (myMap[y][x] == 0)
			return false;
		return true;
	}

	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return what the material is at x,y location
	 */
	public int getMaterial(double x, double y) {
		if (x >= 0 && x <= 799 && y >= 0 && y <= 799) {
			return myMap[(int) y][(int) x];
		}
		return -1;
	}

	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param r radius of explosion
	 */
	public void takeDamage(int x, int y, int r) {
		for (int row = y - r; row < GameRunner.HEIGHT && row > -1
				&& row < y + r; row++) {
			for (int col = x - r; col < GameRunner.WIDTH && col > -1
					&& col < x + r; col++) {
				if (Math.sqrt((row - y) * (row - y) + (col - x) * (col - x)) < r) {
					// System.out.println(row + " " + col);
					myMap[row][col] = 0;
				}
			}
		}
		spread();
	}

	/**
	 * 
	 */
////////////Start Requirement:4 while/for loops
	public void spread() {
		for (int row = 0; row < GameRunner.HEIGHT; row++) {
			for (int col = 0; col < GameRunner.WIDTH; col++) {
				////////////Start Requirement:2 Logic Example: 2
				if (col >= 0 && col <= 799 && row >= 0 && row + 1 <= 799) {
				////////////End Requirement:2 Logic Example: 2

					spread(col, row);
					int i=row-1;
					while(i >0 && spread(col, i--));
				}
			}
		}
	}
////////////End Requirement:4 while/for loops


	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return boolean if done
	 */
	public boolean spread(int x, int y) {
		////////////Start Requirement:2 Logic Example: 3
		if (getMaterial(x, y) != 0 && getMaterial(x, y + 1) == 0) {
		////////////End Requirement:2 Logic Example: 3

			myMap[y + 1][x] = myMap[y][x];
			myMap[y][x] = 0;
			spread(x, y + 1);
			return true;
		}
		return false;
	}

	@Override
	void paint(Graphics g) {
		for (int row = 0; row < myMap.length; row++) {
			for (int col = 0; col < myMap[0].length; col++) {
				g.setColor(Color.yellow);
				if (myMap[row][col] == SAND) {
					g.setColor(Color.yellow);
					g.drawRect(col, row, 1, 1);
				} else if (myMap[row][col] == ROAD) {
					g.setColor(Color.GRAY);
					g.drawRect(col, row, 1, 1);
				} else if (myMap[row][col] == EARTH) {
					g.setColor(Color.ORANGE);
					g.drawRect(col, row, 1, 1);
				}
			}
		}
		// g.setColor(Color.YELLOW);
		// g.fillRect(0, 600, 1600, 900);
	}

	@Override
	void update() {
	}

}
