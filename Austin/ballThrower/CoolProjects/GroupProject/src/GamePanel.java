import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable, ActionListener{

	private static final long serialVersionUID = 1L;
	private int score;
	private int stopOffset;
	
	Term fallingTerm;
	private ArrayList<Term> unusedTerms; 
	private ArrayList<Term> stackedTerms; 
	private ArrayList<Term> usedTerms;
	private boolean gameOver, gameStarted;
	
	public GamePanel(){
		score = 0;
		gameOver = false;
		stopOffset = 5;
		unusedTerms = new ArrayList<Term>();
		stackedTerms = new ArrayList<Term>();
		usedTerms = new ArrayList<Term>();
		
		try {
				BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("Resources/input.txt")));
				String st = br.readLine();
				String wholeFile = "";
				while(st != null){
					wholeFile += st;
					
					st = br.readLine();
				}
				//System.out.println(wholeFile);
				while(wholeFile.indexOf("<Term>") == 0){
					int stop = wholeFile.indexOf("</Term>");
					String oneTerm = wholeFile.substring(6,stop);
					wholeFile = wholeFile.substring(stop +7);
					//System.out.println(oneTerm);
					stop = oneTerm.indexOf("<Definition>");
					String word = oneTerm.substring(6,stop-7);
					String definition = oneTerm.substring(stop+12,oneTerm.indexOf("</Definition>"));
					unusedTerms.add(new Term(word.trim(),definition));
					
				}
		}catch (Exception e) {
			e.printStackTrace();
		}
		pickRandomTerm();
		new Thread(this).start();
	}
	
	public void pickRandomTerm(){
		if(unusedTerms.size()==0){
			unusedTerms.addAll(usedTerms);
			usedTerms.clear();
		}
		if(unusedTerms.size()==0)fallingTerm = null;
		int index = (int)(Math.random()*unusedTerms.size());
		fallingTerm = unusedTerms.remove(index);
		usedTerms.add(fallingTerm);
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if(fallingTerm!=null)fallingTerm.display(g);
		for(Term t : stackedTerms)t.display(g);
	}

	@Override
	public void run() {
		while (true){
			if(fallingTerm!=null)fallingTerm.update();
			if(fallingTerm.hitBottom(this.getHeight()-stopOffset)){
				stackedTerms.add(fallingTerm);
				pickRandomTerm();
				stopOffset += 20;
			}
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TextField tf = (TextField)e.getSource();
		if(fallingTerm.check(tf.getText().trim())){
			pickRandomTerm();
			score++;
			GameRunner.score.setText("Score: " + score);
		}
		tf.setText("");
		
	}
}
