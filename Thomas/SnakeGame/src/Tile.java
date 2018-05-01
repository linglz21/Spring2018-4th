import java.awt.Color;
import java.awt.Graphics;

public class Tile {
	int x, y;

	public Tile(Tile other, int dX, int dY) {
		x = other.x + dX;
		y = other.y + dY;
	}

	public Tile() {
		x = (int) (Math.random() * MainClass.WIDTH);
		y = (int) (Math.random() * MainClass.HEIGHT);

	}

	public void draw(Graphics g) {
		g.setColor(new Color(0x08FF18));
		g.fillOval(x, y, 12, 12);
	}

}

// tile color (white)
// tile position (random)
