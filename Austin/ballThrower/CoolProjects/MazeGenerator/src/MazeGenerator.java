import java.awt.Button;
import java.awt.Label;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JFrame;


public class MazeGenerator extends JFrame implements ActionListener, AdjustmentListener{
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 550;
	private static final int HEIGHT = 600;
	private Maze myMaze;
	private Button makeMaze;
	private Scrollbar rows;
	private Scrollbar cols;
	private Label mazeSizeLabel;
	
	public MazeGenerator(){
		myMaze = new Maze();
		myMaze.setBounds(30, 50,WIDTH-50, HEIGHT-100);
		add(myMaze);
		
		makeMaze = new Button("Generate Maze");
		makeMaze.addActionListener(this);
		makeMaze.setBounds(20,10,110,20);
		add(makeMaze);
		
		rows = new Scrollbar(Scrollbar.VERTICAL,20,10,5,150);
		rows.setBounds(20,50,10,500);
		rows.addAdjustmentListener(this);
		add(rows);
		
		cols = new Scrollbar(Scrollbar.HORIZONTAL,20,10,5,150);
		cols.setBounds(30,40,500,10);
		cols.addAdjustmentListener(this);
		add(cols);
		
		mazeSizeLabel = new Label(rows.getValue() + "rows by " + cols.getValue() + " columns");
		mazeSizeLabel.setBounds(150, 10, 400, 20);
		add(mazeSizeLabel);
		
		this.setLayout(null);
	}
	
	public static void main(String[] args) {
		MazeGenerator mg = new MazeGenerator();
		mg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mg.setSize(WIDTH,HEIGHT);
		mg.setResizable(false);
		mg.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		myMaze.makeMaze(rows.getValue(),cols.getValue());
		repaint();
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent arg0) {
		mazeSizeLabel.setText(rows.getValue() + "rows by " + cols.getValue() + " columns");
		repaint();
	}
}
