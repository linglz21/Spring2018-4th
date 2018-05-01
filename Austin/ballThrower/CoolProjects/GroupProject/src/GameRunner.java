import java.awt.Label;
import java.awt.TextField;

import javax.swing.JFrame;


public class GameRunner extends JFrame {

	
	

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private GamePanel gp;
	private TextField tf;
	public static Label score;
	
	public GameRunner (){
		this.setLayout(null);
		gp = new GamePanel();
		gp.setBounds(30, 150, 700, 400);
		add(gp);
		
		tf = new TextField();
		tf.setBounds(30, 60, 400, 30);
		add(tf);
		tf.addActionListener(gp);
		
		score = new Label("Score: 0");
		score.setBounds(450, 60, 100, 30);
		add(score);
	}
	
	public static void main(String[] args) {
		GameRunner gr = new GameRunner();
		gr.setSize(WIDTH, HEIGHT);
		gr.setResizable(false);
		gr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gr.setVisible(true);
	}
}
