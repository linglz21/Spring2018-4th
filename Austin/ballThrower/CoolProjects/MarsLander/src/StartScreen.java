import java.awt.Color;
import java.awt.Graphics;


public class StartScreen {

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, TheGame.WIDTH, TheGame.HEIGHT);
		g.setColor(Color.green);
		g.fillOval(50, 50, 100, 100);
		
	}

}
