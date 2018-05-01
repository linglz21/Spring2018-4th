import java.awt.Color;
import java.awt.Graphics;

public class Food {
	int x, y;

	public Food() {
		x = (int) (Math.random() * (MainClass.WIDTH - 15));
		y = (int) (Math.random() * (MainClass.HEIGHT - 45) + 30);
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(x, y, 10, 10);
	}

}
