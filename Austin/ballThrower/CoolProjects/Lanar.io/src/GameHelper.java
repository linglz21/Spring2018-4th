

public class GameHelper implements Runnable {
	private Game game;
	private double start;
	private int frames;
	public static int fps; //Doesn't really work
	GameHelper(Game game) {
		this.game = game;
		new Thread(this).start();
	}
	@Override
	public void run() {
		while (true) {
			start = System.currentTimeMillis();
			frames = 0;
			while (System.currentTimeMillis()-start < 1000) {
				game.repaint();
				frames++;
			}
			fps = frames;
		}
	}
	
}
